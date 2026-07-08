package br.com.jhohannesfreitas.candidaturas.repository;

import br.com.jhohannesfreitas.candidaturas.domain.enums.StatusCandidaturaEnum;
import br.com.jhohannesfreitas.candidaturas.domain.model.CandidaturaEntity;
import br.com.jhohannesfreitas.candidaturas.domain.model.UsuarioEntity;
import br.com.jhohannesfreitas.candidaturas.domain.model.VagaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface ICandidaturaRepository extends JpaRepository<CandidaturaEntity, Long> {

    boolean existsByUsuarioAndVaga(UsuarioEntity usuario, VagaEntity vaga);

    List<CandidaturaEntity> findByUsuario(UsuarioEntity usuario);

    List<CandidaturaEntity> findByStatus(StatusCandidaturaEnum status);

    Optional<CandidaturaEntity> findByVaga(Long id);

    boolean existsByStatus(StatusCandidaturaEnum status);

    List<CandidaturaEntity> findByUsuarioAndStatus(UsuarioEntity usuarioLogado, StatusCandidaturaEnum status);
}
