package com.wokite.net.referentiel.repository;

import com.wokite.net.referentiel.entity.Fournisseur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {

    @Query("SELECT f FROM Fournisseur f WHERE f.id = :id AND f.actif = 1")
    Fournisseur findFournisseurById(@Param("id") Long id);

    Optional<Fournisseur> findByDenomination(String denomination);

    @Query("SELECT f FROM Fournisseur f WHERE f.actif = 1")
    List<Fournisseur> findAllActiveFournisseurs();

    @Query("SELECT f FROM Fournisseur f WHERE f.actif = 1 AND f.communeId = :communeId")
    List<Fournisseur> findFournisseursByCommune(@Param("communeId") Long communeId);

    // Supprimer le ORDER BY pour laisser Pageable gérer le tri
    @Query("SELECT f FROM Fournisseur f WHERE f.actif = 1")
    Page<Fournisseur> findFournisseurPaged(Pageable pageable);

    // Supprimer le ORDER BY pour laisser Pageable gérer le tri
    @Query("""
            SELECT f FROM Fournisseur f
            WHERE f.actif = 1
              AND (:denomination IS NULL OR LOWER(f.denomination) LIKE LOWER(CONCAT('%', :denomination, '%')))
            """)
    Page<Fournisseur> findFilteredFournisseurPaged(
            @Param("denomination") String denomination,
            Pageable pageable
    );
}