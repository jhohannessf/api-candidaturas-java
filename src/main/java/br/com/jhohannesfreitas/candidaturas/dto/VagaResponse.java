package br.com.jhohannesfreitas.candidaturas.dto;

import br.com.jhohannesfreitas.candidaturas.domain.enums.StatusCandidaturaEnum;
import br.com.jhohannesfreitas.candidaturas.domain.model.VagaEntity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record VagaResponse(
        String empresa,
        String cargo,
        String descricaoVaga,

        @Enumerated(EnumType.STRING)
        StatusCandidaturaEnum status
) {

        // Mapper de saída: Transforma uma entidade em um DTO
        public static VagaResponse fromEntity(VagaEntity vaga) {
                return new VagaResponse(
                        vaga.getEmpresa(),
                        vaga.getCargo(),
                        vaga.getDescricaoVaga(),
                        vaga.getStatus()
                );
        }

}
