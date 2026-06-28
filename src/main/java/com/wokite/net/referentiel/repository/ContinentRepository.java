package com.wokite.net.referentiel.repository;

import com.wokite.net.referentiel.entity.Continent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContinentRepository extends JpaRepository<Continent, Long> {

    @Query("SELECT c from Continent c where c.id=:id ")
    Continent findContinentById(@Param("id") Long id);

    @Query("SELECT c from Continent c where c.actif=1")
    List<Continent> findAllActiveContinents();

}