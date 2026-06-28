package com.wokite.net.referentiel.repository;

import com.wokite.net.referentiel.entity.Commune;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommuneRepository extends JpaRepository<Commune, Long> {

    @Query("SELECT c from Commune c where c.id=:id ")
    Commune findCommuneById(@Param("id") Long id);

    Optional<Commune> findByLibelle(String libelle);

    @Query("SELECT c from Commune c where c.actif=1")
    List<Commune> findAllActiveCommunes();

    @Query("SELECT c FROM Commune c WHERE c.actif=1 AND c.departmentId = :departmentId")
    List<Commune> findCommuneByDepartement(@Param("departmentId") Long departmentId);

    @Query(value = "SELECT c FROM Commune c where c.actif=1 order by c.libelle asc")
    Page<Commune> findCommunePaged(Pageable pageable);

    @Query(value = """
              SELECT c
              FROM Commune c
              WHERE c.actif=1
                AND (:libelle IS NULL OR c.libelle LIKE %:libelle%)
               AND (:departmentId IS NULL OR c.departmentId = :departmentId)
              ORDER BY c.libelle DESC
            """)
    Page<Commune> findFilteredCommunePaged(
            @Param("libelle") String libelle,
            @Param("departmentId") Long departmentId,
            Pageable pageable
    );
}