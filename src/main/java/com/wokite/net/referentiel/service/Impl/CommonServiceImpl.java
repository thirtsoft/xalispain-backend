package com.wokite.net.referentiel.service.Impl;

import com.wokite.net.referentiel.mapping.DTOFactoryReferentiel;
import com.wokite.net.referentiel.repository.EtatRepository;
import com.wokite.net.referentiel.service.CommonService;
import com.wokite.net.utils.dto.referentiel.ListEtatDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommonServiceImpl implements CommonService {

    private final EtatRepository etatRepository;
    private final DTOFactoryReferentiel dtoFactoryReferentiel;

    @Override
    public List<ListEtatDTO> findListEtats() {
        return etatRepository.findAllActiveEtats().stream()
                .map(dtoFactoryReferentiel::fromEtatToListEtatDTO)
                .toList();
    }

}