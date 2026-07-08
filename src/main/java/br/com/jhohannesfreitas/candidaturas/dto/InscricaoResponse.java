package br.com.jhohannesfreitas.candidaturas.dto;

import java.time.LocalDate;

public record InscricaoResponse(
        String nomeUsuario,
        String vagaEmpresa,
        String vagaCargo,
        String StatusCandidaturaEnum,
        LocalDate dataAplicacao
) {
}
