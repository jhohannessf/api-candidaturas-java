package br.com.jhohannesfreitas.candidaturas.dto;

import br.com.jhohannesfreitas.candidaturas.domain.enums.StatusCandidaturaEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class CandidaturaRequestDTO {
    private Long id;

    @Enumerated(EnumType.STRING)
    private StatusCandidaturaEnum status;

    private LocalDate dataAplicacao;


}
