package br.com.jhohannesfreitas.candidaturas.service;

import br.com.jhohannesfreitas.candidaturas.dto.PerfilRequestDTO;
import br.com.jhohannesfreitas.candidaturas.dto.PerfilResponseDTO;
import br.com.jhohannesfreitas.candidaturas.domain.model.PerfilEntity;
import br.com.jhohannesfreitas.candidaturas.domain.model.UsuarioEntity;
import br.com.jhohannesfreitas.candidaturas.repository.IPerfilRepository;
import br.com.jhohannesfreitas.candidaturas.repository.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PerfilService {

    private final IPerfilRepository perfilRepository;
    private final IUsuarioRepository usuarioRepository;

    public PerfilResponseDTO cadastrarPerfil(PerfilRequestDTO dto) {

        // Buscar por usuário autenticado logado
        UsuarioEntity usuario =
                (UsuarioEntity) SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal();

        PerfilEntity perfil = new PerfilEntity(
                dto.resumoCurriculo(),
                dto.habilidades(),
                usuario);

        PerfilEntity perfilSalvo = perfilRepository.save(perfil);

        return new PerfilResponseDTO(
                perfilSalvo.getUsuario().getId(),
                perfilSalvo.getHabilidades(),
                perfilSalvo.getResumoCurriculo()
        );
    }

}
