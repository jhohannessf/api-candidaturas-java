package br.com.jhohannesfreitas.candidaturas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class LoginRequest {
    @NotBlank(message = "E-mail obrigatório")
    @Email(message = "E-mail inválido!")
    private String email;
    @NotBlank(message = "Senha obrigatória")
    private String senha;

}
