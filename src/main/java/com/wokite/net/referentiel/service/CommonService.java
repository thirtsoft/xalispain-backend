package com.wokite.net.referentiel.service;

import com.wokite.net.utils.dto.referentiel.ListEtatDTO;

import java.util.List;

public interface CommonService {

    /**************   Etat  ***********************/
    List<ListEtatDTO> findListEtats();

}