package br.com.jhohannesfreitas.candidaturas.dto;

import br.com.jhohannesfreitas.candidaturas.model.UsuarioEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioRequest (
        Long id,
        @NotBlank(message = "nome obrigatório")
        String nome,
        @NotBlank(message = "E-mail obrigatório")
        @Email(message = "E-mail inválido!")
        String email,
        @NotBlank(message = "Senha obrigatória")
        String senha
) {

    // Mapper de entrada: Transforma uma DTO em uma Entidade
    public UsuarioEntity toEntity() {
        UsuarioEntity usuario = new UsuarioEntity();
        preencher(usuario);

        return usuario;
    }

    public void preencher(UsuarioEntity usuario) {
        usuario.setId(id);
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);
    }

}

