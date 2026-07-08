package br.com.jhohannesfreitas.candidaturas.dto;

import br.com.jhohannesfreitas.candidaturas.domain.enums.StatusCandidaturaEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;

public record CandidaturaResponse(
        Long id,

        @Enumerated(EnumType.STRING)
        StatusCandidaturaEnum status,

        LocalDate dataAplicacao
) {
}
