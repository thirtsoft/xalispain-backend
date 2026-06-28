package com.wokite.net.referentiel.repository;

import com.wokite.net.referentiel.entity.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Long> {

    @Query("SELECT r from Region r where r.id=:id and r.actif=1 ")
    Region findRegionById(@Param("id") Long id);

    Optional<Region> findByCode(String code);

    Optional<Region> findByLibelle(String libelle);

    @Query("SELECT r from Region r where r.actif=1")
    List<Region> findAllActiveRegions();

    @Query("SELECT r FROM Region r WHERE r.actif=1 AND r.paysId = :paysId")
    List<Region> findRegionByPays(@Param("paysId") Long paysId);

    @Query(value = "Select r FROM Region r where r.actif=1 order by r.libelle asc")
    Page<Region> findRegionPaged(Pageable pageable);

    @Query(value = """
              SELECT r
              FROM Region r
              WHERE r.actif=1
                AND (:libelle IS NULL OR r.libelle LIKE %:libelle%)
                AND (:paysId IS NULL OR r.paysId = :paysId)
              ORDER BY r.libelle DESC
            """)
    Page<Region> findFilteredRegionPaged(
            @Param("libelle") String libelle,
            @Param("pays") Long paysId,
            Pageable pageable
    );
}