package br.com.jhohannesfreitas.candidaturas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VagaResponseDTO {
    private String empresa;
    private String cargo;
    private String descricaoVaga;
}
