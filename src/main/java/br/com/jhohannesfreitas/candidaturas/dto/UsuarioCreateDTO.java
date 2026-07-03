package br.com.jhohannesfreitas.candidaturas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class UsuarioCreateDTO {
    private Long id;
    @NotBlank(message = "nome obrigatório")
    private String nome;
    @NotBlank(message = "E-mail obrigatório")
    @Email(message = "E-mail inválido!")
    private String email;
    @NotBlank(message = "Senha obrigatória")
    private String senha;

    public UsuarioCreateDTO(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }
}
