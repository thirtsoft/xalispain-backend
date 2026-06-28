package com.wokite.net.referentiel.service;

import com.wokite.net.utils.dto.referentiel.FournisseurAddEditDTO;
import com.wokite.net.utils.dto.referentiel.ListFournisseurDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FournisseurService {

    /**************   Fournisseur  ***********************/
    void saveFournisseur(FournisseurAddEditDTO fournisseurAddEditDTO);

    void updateFournisseur(Long id, FournisseurAddEditDTO fournisseurAddEditDTO);

    FournisseurAddEditDTO findFournisseur(Long id);

    List<ListFournisseurDTO> findListFournisseurs();

    List<ListFournisseurDTO> findListFournisseursByCommune(Long communeId);

    Page<ListFournisseurDTO> findFournisseursPaged(Pageable pageable);

    Page<ListFournisseurDTO> findFilteredFournisseursPaged(Pageable pageable, String filtre);

    void deleteFournisseur(Long id);

}