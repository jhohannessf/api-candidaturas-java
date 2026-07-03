package br.com.jhohannesfreitas.candidaturas.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InscricaoRequestDTO {
//    @Valid
//    private UsuarioResponseDTO usuario;
    private VagaRequestDTO vaga;
}
