package br.com.jhohannesfreitas.candidaturas.repository;

import br.com.jhohannesfreitas.candidaturas.domain.model.VagaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IVagaRepository extends JpaRepository<VagaEntity, Long> {

    Optional<VagaEntity> findByEmpresaAndCargo(
            String empresa,
            String cargo);

    Optional<VagaEntity> findByEmpresaContainingIgnoreCase(@Param("empresa") String empresa);

    @Query("""
                SELECT v
                FROM VagaEntity v
                WHERE LOWER(function('unaccent', v.empresa))
                      LIKE LOWER(function('unaccent', concat('%', :empresa, '%')))
            """)
    Optional<VagaEntity> buscarEmpresaSemAcento(@Param("empresa") String empresa);
}
