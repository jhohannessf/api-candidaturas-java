package br.com.jhohannesfreitas.candidaturas.service;

import br.com.jhohannesfreitas.candidaturas.domain.model.UsuarioEntity;
import br.com.jhohannesfreitas.candidaturas.dto.UsuarioRequest;
import br.com.jhohannesfreitas.candidaturas.dto.UsuarioResponse;
import br.com.jhohannesfreitas.candidaturas.exception.RegraNegocioException;
import br.com.jhohannesfreitas.candidaturas.mapper.UsuarioMapper;
import br.com.jhohannesfreitas.candidaturas.repository.ICandidaturaRepository;
import br.com.jhohannesfreitas.candidaturas.repository.IUsuarioRepository;
import br.com.jhohannesfreitas.candidaturas.repository.IVagaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioMapper usuarioMapper;

    private final IUsuarioRepository usuarioRepository;

    private final AuthenticationService authenticationService;

    private final ICandidaturaRepository candidaturaRepository;

    private final IVagaRepository vagaRepository;

    private final VagaService vagaService;

    private final CandidaturaService candidaturaService;


    public UsuarioResponse alterarCadastro(Long id, UsuarioRequest usuarioRequest) {
        UsuarioEntity usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Usuário não encontrado"));

        UsuarioEntity usuarioLogado = authenticationService.getUsuarioAutenticado();

        if (!usuarioLogado.getId().equals(usuario.getId())) {
            throw new AccessDeniedException("Não é possível alterar dados de outro usuário.");
        }

        usuario.setNome(usuarioRequest.nome());
        usuario.setEmail(usuarioRequest.email());
        usuario.setSenha(usuarioRequest.senha());

        usuarioRepository.save(usuario);

        return usuarioMapper.toResponse(usuario);
    }

    // Sem uso por enquanto
    private UsuarioEntity buscarUsuarioPorEmail(String email) {

        return usuarioRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RegraNegocioException("Usuário de e-mail: " + email + " não encontrado"));
    }

    // Sem uso por enquanto
    private void validarEmailExistente(String email) {

        if (usuarioRepository.existsByEmail(email)) {
            throw new RegraNegocioException(
                    "E-mail já cadastrado");
        }
    }
}
