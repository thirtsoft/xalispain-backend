package com.wokite.net.referentiel.service;

import com.wokite.net.utils.dto.referentiel.ListTypeDepenseDTO;
import com.wokite.net.utils.dto.referentiel.TypeDepenseAddEditDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeDepenseService {

    /**************   TypeDepense  ***********************/
    void saveTypeDepense(TypeDepenseAddEditDTO typeDepenseAddEditDTO);

    void updateTypeDepense(Long id, TypeDepenseAddEditDTO typeDepenseAddEditDTO);

    TypeDepenseAddEditDTO findTypeDepense(Long id);

    List<ListTypeDepenseDTO> findListTypeDepenses();

    Page<ListTypeDepenseDTO> findTypeDepensesPaged(Pageable pageable);

    Page<ListTypeDepenseDTO> findFilteredTypeDepensesPaged(Pageable pageable, String filtre);

    void deleteTypeDepense(Long id);


}