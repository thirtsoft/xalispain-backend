package com.wokite.net.referentiel.repository;

import com.wokite.net.referentiel.entity.ModePaiement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ModePaiementRepository extends JpaRepository<ModePaiement, Long> {

    @Query("SELECT m from ModePaiement m where m.id=:id and m.actif=1 ")
    ModePaiement findModePaiementById(@Param("id") Long id);

    Optional<ModePaiement> findByLibelle(String libelle);

    @Query("SELECT m from ModePaiement m where m.actif=1")
    List<ModePaiement> findAllActiveModePaiements();

    @Query(value = "Select m FROM ModePaiement m where m.actif=1 order by m.libelle asc")
    Page<ModePaiement> findModePaiementPaged(Pageable pageable);

    @Query(value = """
              SELECT m
              FROM ModePaiement m
              WHERE m.actif=1
                AND (:libelle IS NULL OR m.libelle LIKE %:libelle%)
              ORDER BY m.libelle DESC
            """)
    Page<ModePaiement> findFilteredModePaiementPaged(
            @Param("libelle") String libelle,
            Pageable pageable
    );
}