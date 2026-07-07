package br.com.jhohannesfreitas.candidaturas.dto;

import br.com.jhohannesfreitas.candidaturas.domain.enums.StatusCandidaturaEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VagaResponseDTO {
    private String empresa;
    private String cargo;
    private String descricaoVaga;

    @Enumerated(EnumType.STRING)
    private StatusCandidaturaEnum status;
}
