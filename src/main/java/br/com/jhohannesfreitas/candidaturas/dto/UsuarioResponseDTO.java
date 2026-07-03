package br.com.jhohannesfreitas.candidaturas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class UsuarioResponseDTO {
    private String nome;
    private String email;

}
