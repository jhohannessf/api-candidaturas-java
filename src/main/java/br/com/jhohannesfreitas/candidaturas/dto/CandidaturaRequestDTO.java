package br.com.jhohannesfreitas.candidaturas.dto;

import br.com.jhohannesfreitas.candidaturas.model.StatusCandidaturaEnum;
import br.com.jhohannesfreitas.candidaturas.model.VagaEntity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
