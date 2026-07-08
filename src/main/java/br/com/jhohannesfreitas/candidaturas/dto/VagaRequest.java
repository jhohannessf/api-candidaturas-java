package br.com.jhohannesfreitas.candidaturas.dto;

import br.com.jhohannesfreitas.candidaturas.domain.enums.StatusCandidaturaEnum;
import br.com.jhohannesfreitas.candidaturas.domain.model.VagaEntity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;

public record VagaRequest(
        @NotBlank
        String empresa,
        @NotBlank
        String cargo,
        @NotBlank
        String descricaoVaga,
        @Enumerated(EnumType.STRING)
        StatusCandidaturaEnum status
) {

    // Mapper de entrada: Transforma uma DTO em uma Entidade
    public VagaEntity toEntity() {
        VagaEntity vaga = new VagaEntity();
        preencher(vaga);
        return vaga;
    }

    private void preencher(VagaEntity vaga) {
        vaga.setEmpresa(empresa);
        vaga.setCargo(cargo);
        vaga.setDescricaoVaga(descricaoVaga);
        vaga.setStatus(status);
    }

}
