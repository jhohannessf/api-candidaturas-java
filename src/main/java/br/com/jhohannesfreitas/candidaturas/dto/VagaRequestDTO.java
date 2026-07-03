package br.com.jhohannesfreitas.candidaturas.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VagaRequestDTO {
    @NotBlank
    private String empresa;
    @NotBlank
    private String cargo;
    @NotBlank
    private String descricaoVaga;
}
