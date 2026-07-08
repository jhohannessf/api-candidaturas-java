package br.com.jhohannesfreitas.candidaturas.mapper;

import br.com.jhohannesfreitas.candidaturas.domain.enums.StatusCandidaturaEnum;
import br.com.jhohannesfreitas.candidaturas.domain.model.CandidaturaEntity;
import br.com.jhohannesfreitas.candidaturas.domain.model.UsuarioEntity;
import br.com.jhohannesfreitas.candidaturas.domain.model.VagaEntity;
import br.com.jhohannesfreitas.candidaturas.dto.CandidaturaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class CandidaturaMapper {

    public CandidaturaEntity toEntity(UsuarioEntity usuario, VagaEntity vaga) {
        return CandidaturaEntity.builder()
                .usuario(usuario)
                .vaga(vaga)
                .status(StatusCandidaturaEnum.APLICADO)
                .dataAplicacao(LocalDate.now())
                .build();
    }

    public CandidaturaResponse toResponse(CandidaturaEntity candidatura) {
        return new CandidaturaResponse(
                candidatura.getId(),
                candidatura.getStatus(),
                candidatura.getDataAplicacao()
        );
    }
}
