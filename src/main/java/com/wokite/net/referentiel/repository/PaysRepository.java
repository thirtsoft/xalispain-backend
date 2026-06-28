package com.wokite.net.referentiel.repository;

import com.wokite.net.referentiel.entity.Pays;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaysRepository extends JpaRepository<Pays, Long> {

    @Query("SELECT p from Pays p where p.id=:id and p.actif=1 ")
    Pays findPaysById(@Param("id") Long id);

    @Query("SELECT p from  Pays p where p.actif=1 and p.libelle=:libelle")
    Pays findByLibelle(@Param("libelle") String libelle);

    @Query("SELECT p from  Pays p where p.actif=1 and p.code=:code")
    Pays findByCode(@Param("code") String code);

    @Query("SELECT p from Pays p where p.actif=1")
    List<Pays> findAllActivePays();

    @Query("SELECT p FROM Pays p WHERE p.actif=1 AND p.continentId = :continentId")
    List<Pays> findPaysByContinent(@Param("continentId") Long continentId);

    @Query(value = "Select p FROM Pays p where p.actif=1 order by p.libelle asc")
    Page<Pays> findPaysPaged(Pageable pageable);

    @Query(value = """
              SELECT p
              FROM Pays p
              WHERE p.actif=1
                AND (:libelle IS NULL OR p.libelle LIKE %:libelle%)
                AND (:continent IS NULL OR p.continentId = :continent)
              ORDER BY p.libelle DESC
            """)
    Page<Pays> findFilteredPaysPaged(
            @Param("libelle") String libelle,
            @Param("continent") Long continentId,
            Pageable pageable
    );

}