package com.wokite.net.referentiel.service;

import com.wokite.net.utils.dto.referentiel.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LocaliteService {

    /**************   Commune  ***********************/
    void saveCommune(CommuneAddEditDTO communeAddEditDTO);

    void updateCommune(Long id, CommuneAddEditDTO communeAddEditDTO);

    CommuneAddEditDTO findCommune(Long id);

    List<ListCommuneDTO> findListCommunes();

    List<ListCommuneDTO> findListCommunesByDepartment(Long departmentId);

    Page<ListCommuneDTO> findCommunesPaged(Pageable pageable);

    Page<ListCommuneDTO> findFilteredCommunesPaged(Pageable pageable, String filtre);

    void deleteCommune(Long id);

    /**************   Departement  ***********************/
    void saveDepartement(DepartementAddEditDTO departementAddEditDTO);

    void updateDepartement(Long id, DepartementAddEditDTO departementAddEditDTO);

    DepartementAddEditDTO findDepartementById(Long id);

    List<ListDepartementDTO> findListDepartements();

    List<ListDepartementDTO> findDepartmentsByRegion(Long regionId);

    Page<ListDepartementDTO> findDepartmentsPaged(Pageable pageable);

    Page<ListDepartementDTO> findFilteredDepartmentsPaged(Pageable pageable, String filtre);

    void deleteDepartment(Long id);

    /**************   Region  ***********************/
    void saveRegion(RegionAddEditDTO regionAddEditDTO);

    void updateRegion(Long id, RegionAddEditDTO regionAddEditDTO);

    RegionAddEditDTO findRegionById(Long id);

    List<ListRegionDTO> findListRegions();

    List<ListRegionDTO> findRegionByPays(Long paysId);

    Page<ListRegionDTO> findRegionsPaged(Pageable pageable);

    Page<ListRegionDTO> findFilteredRegionsPaged(Pageable pageable, String filtre);

    void deleteRegion(Long id);

    /**************   Pays  ***********************/
    List<ListPaysDTO> findListPays();

    List<ListPaysDTO> findPaysByContinent(Long continentId);

    Page<ListPaysDTO> findPaysPaged(Pageable pageable);

    Page<ListPaysDTO> findFilteredPaysPaged(Pageable pageable, String filtre);

    void deletePays(Long id);

    /**************   Region  ***********************/
    List<ListContinentDTO> findListContinents();

}