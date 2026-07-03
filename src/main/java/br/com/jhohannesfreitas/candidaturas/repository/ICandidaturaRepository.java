package br.com.jhohannesfreitas.candidaturas.repository;

import br.com.jhohannesfreitas.candidaturas.model.CandidaturaEntity;
import br.com.jhohannesfreitas.candidaturas.model.UsuarioEntity;
import br.com.jhohannesfreitas.candidaturas.model.VagaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICandidaturaRepository extends JpaRepository<CandidaturaEntity, Long> {

    boolean existsByUsuarioAndVaga(UsuarioEntity usuario, VagaEntity vaga);

    List<CandidaturaEntity> findByUsuario(UsuarioEntity usuario);
}
