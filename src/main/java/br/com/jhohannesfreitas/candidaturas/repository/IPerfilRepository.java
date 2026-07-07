package br.com.jhohannesfreitas.candidaturas.repository;

import br.com.jhohannesfreitas.candidaturas.domain.model.PerfilEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPerfilRepository extends JpaRepository<PerfilEntity, Long> {
}
