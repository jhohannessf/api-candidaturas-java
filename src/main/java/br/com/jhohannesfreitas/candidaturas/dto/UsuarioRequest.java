package br.com.jhohannesfreitas.candidaturas.dto;

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
) {}

