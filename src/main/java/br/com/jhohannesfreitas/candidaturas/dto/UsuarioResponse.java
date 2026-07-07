package br.com.jhohannesfreitas.candidaturas.dto;

import br.com.jhohannesfreitas.candidaturas.model.UsuarioEntity;


public record UsuarioResponse (
        String nome,
        String email
) {

    public static UsuarioResponse fromEntity(UsuarioEntity usuario) {
        return new UsuarioResponse(
                usuario.getNome(),
                usuario.getEmail()
        );
    }

}
