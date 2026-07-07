package br.com.jhohannesfreitas.candidaturas.mapper;

import br.com.jhohannesfreitas.candidaturas.domain.model.UsuarioEntity;
import br.com.jhohannesfreitas.candidaturas.dto.UsuarioRequest;
import br.com.jhohannesfreitas.candidaturas.dto.UsuarioResponse;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioEntity toEntity(UsuarioRequest dto) {
        UsuarioEntity usuario = new UsuarioEntity();

        usuario.setId(dto.id());
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(dto.senha());

        return usuario;
    }

    public UsuarioResponse toResponse(UsuarioEntity usuario) {
        return new UsuarioResponse(
                usuario.getNome(),
                usuario.getEmail()
        );
    }
}
