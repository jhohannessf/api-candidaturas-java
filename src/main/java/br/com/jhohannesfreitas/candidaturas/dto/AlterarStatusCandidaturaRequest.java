package br.com.jhohannesfreitas.candidaturas.dto;

import br.com.jhohannesfreitas.candidaturas.domain.enums.StatusCandidaturaEnum;

public record AlterarStatusCandidaturaRequest(
        Long candidaturaId,
        StatusCandidaturaEnum status
){
}
