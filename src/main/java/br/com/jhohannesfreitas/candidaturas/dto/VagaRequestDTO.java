package br.com.jhohannesfreitas.candidaturas.dto;

import br.com.jhohannesfreitas.candidaturas.domain.enums.StatusCandidaturaEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Enumerated(EnumType.STRING)
    private StatusCandidaturaEnum status;

}
