package com.wokite.net.referentiel.repository;

import com.wokite.net.referentiel.entity.Etat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EtatRepository extends JpaRepository<Etat, Long> {

    @Query("SELECT e from Etat e where e.id=:id")
    Etat findEtatById(@Param("id") Long id);

    @Query("SELECT e from Etat e where e.actif=1")
    List<Etat> findAllActiveEtats();
}