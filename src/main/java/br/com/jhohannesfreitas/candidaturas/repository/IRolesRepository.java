package br.com.jhohannesfreitas.candidaturas.repository;

import br.com.jhohannesfreitas.candidaturas.domain.model.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRolesRepository extends JpaRepository<RolesEntity, Integer> {
    Optional<RolesEntity> findByNome(String role);
}
