package com.wokite.net.referentiel.service;

import com.wokite.net.utils.dto.referentiel.ListModePaiementDTO;
import com.wokite.net.utils.dto.referentiel.ModePaiementAddEditDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ModePaiementService {

    /**************   ModePaiement  ***********************/
    void saveModePaiement(ModePaiementAddEditDTO modePaiementAddEditDTO);

    void updateModePaiement(Long id, ModePaiementAddEditDTO modePaiementAddEditDTO);

    ModePaiementAddEditDTO findModePaiement(Long id);

    List<ListModePaiementDTO> findListModePaiements();

    Page<ListModePaiementDTO> findModePaiementsPaged(Pageable pageable);

    Page<ListModePaiementDTO> findFilteredModePaiementsPaged(Pageable pageable, String filtre);

    void deleteModePaiement(Long id);

}