package br.com.jhohannesfreitas.candidaturas.service;

import br.com.jhohannesfreitas.candidaturas.config.TokenProvider;
import br.com.jhohannesfreitas.candidaturas.dto.LoginRequest;
import br.com.jhohannesfreitas.candidaturas.dto.RegisterRequest;
import br.com.jhohannesfreitas.candidaturas.dto.TokenResponse;
import br.com.jhohannesfreitas.candidaturas.domain.enums.RoleTypeEnum;
import br.com.jhohannesfreitas.candidaturas.domain.model.RolesEntity;
import br.com.jhohannesfreitas.candidaturas.domain.model.UsuarioEntity;
import br.com.jhohannesfreitas.candidaturas.repository.IRolesRepository;
import br.com.jhohannesfreitas.candidaturas.repository.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.apache.coyote.BadRequestException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final IUsuarioRepository usuarioRepository;
    private final IRolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @Value("${jwt.expiration}")
    private long expirationTime;

    public void register(RegisterRequest dto) throws BadRequestException {
        UsuarioEntity usuarioEntity = usuarioRepository.findByEmail(dto.getEmail())
                .orElse(null);
        if (usuarioEntity != null) {
            throw new BadRequestException("Usuário já cadastradp com este e-mail");
        }

        RolesEntity role = rolesRepository.findByNome(RoleTypeEnum.ROLE_USUARIO.name())
                .orElseGet(() -> rolesRepository.save(RolesEntity.builder()
                        .nome(RoleTypeEnum.ROLE_USUARIO.name())
                        .build()));

        usuarioRepository.save(UsuarioEntity.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .roles(Set.of(role))
                .senha(passwordEncoder.encode(dto.getSenha()))
                .build());

    }

    public TokenResponse login(LoginRequest dto) throws BadRequestException {
        try {
            // Authentication provider -> userDetailsService -> passwordEncoder.matches() -> Usuário Autenticado
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha()));
            String token = tokenProvider.gerarToken(authentication);
            return new TokenResponse(token, expirationTime);
        } catch (BadCredentialsException e) {
            throw new BadRequestException("Credenciais inválidas");
        }
    }

    // Busca por usuário logado
    public UsuarioEntity getUsuarioAutenticado() {
        return (UsuarioEntity) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
}


