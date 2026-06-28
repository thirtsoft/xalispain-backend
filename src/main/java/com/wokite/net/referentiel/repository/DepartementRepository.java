package com.wokite.net.referentiel.repository;

import com.wokite.net.referentiel.entity.Departement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DepartementRepository extends JpaRepository<Departement, Long> {

    @Query("SELECT d from Departement d where d.id=:id ")
    Departement findDepartementById(@Param("id") Long id);

    Optional<Departement> findByCode(String code);

    Optional<Departement> findByLibelle(String libelle);

    @Query("SELECT d from Departement d where d.actif=1")
    List<Departement> findAllActiveDepartements();

    @Query("SELECT d FROM Departement d WHERE d.actif=1 AND d.regionId = :regionId")
    List<Departement> findDepartementByRegion(@Param("regionId") Long regionId);

    @Query(value = "SELECT d FROM Departement d where d.actif=1 order by d.libelle asc")
    Page<Departement> findDepartementPaged(Pageable pageable);

    @Query(value = """
              SELECT d
              FROM Departement d
              WHERE d.actif=1
                AND (:libelle IS NULL OR d.libelle LIKE %:libelle%)
               AND (:regionId IS NULL OR d.regionId = :regionId)
              ORDER BY d.libelle DESC
            """)
    Page<Departement> findFilteredDepartementPaged(
            @Param("libelle") String libelle,
            @Param("regionId") Long regionId,
            Pageable pageable
    );
}