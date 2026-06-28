package com.wokite.net.referentiel.repository;

import com.wokite.net.referentiel.entity.TypeDepense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TypeDepenseRepository extends JpaRepository<TypeDepense, Long> {

    @Query("SELECT dep from TypeDepense dep where dep.id=:id and dep.actif=1 ")
    TypeDepense findTypeDepenseById(@Param("id") Long id);

    Optional<TypeDepense> findByLibelle(String libelle);

    @Query("SELECT dep from TypeDepense dep where dep.actif=1")
    List<TypeDepense> findAllActiveTypeDepenses();

    @Query(value = "Select dep FROM TypeDepense dep where dep.actif=1 order by dep.libelle asc")
    Page<TypeDepense> findTypeDepensePaged(Pageable pageable);

    @Query(value = """
              SELECT dep
              FROM TypeDepense dep
              WHERE dep.actif=1
                AND (:libelle IS NULL OR dep.libelle LIKE %:libelle%)
              ORDER BY dep.libelle DESC
            """)
    Page<TypeDepense> findFilteredTypeDepensePaged(
            @Param("libelle") String libelle,
            Pageable pageable
    );
}