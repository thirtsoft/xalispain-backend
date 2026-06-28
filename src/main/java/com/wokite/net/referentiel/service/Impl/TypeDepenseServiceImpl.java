package com.wokite.net.referentiel.service.Impl;

import com.wokite.net.exception.DuplicateXalisPainException;
import com.wokite.net.exception.InvalidXalisPainDataException;
import com.wokite.net.exception.XalisPainNotFoundException;
import com.wokite.net.referentiel.entity.TypeDepense;
import com.wokite.net.referentiel.mapping.DTOFactoryReferentiel;
import com.wokite.net.referentiel.mapping.ModelFactoryReferentiel;
import com.wokite.net.referentiel.repository.TypeDepenseRepository;
import com.wokite.net.referentiel.service.TypeDepenseService;
import com.wokite.net.utils.dto.referentiel.ListTypeDepenseDTO;
import com.wokite.net.utils.dto.referentiel.RechercheAddressDTO;
import com.wokite.net.utils.dto.referentiel.TypeDepenseAddEditDTO;
import com.wokite.net.validator.refentiel.TypeDepenseValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;

import static com.wokite.net.utils.constances.referentiel.ConstantsReferentiel.TYPEDEPENSE_ENTITY;

@Service
@RequiredArgsConstructor
@Slf4j
public class TypeDepenseServiceImpl implements TypeDepenseService {

    private final TypeDepenseRepository typeDepenseRepository;
    private final ModelFactoryReferentiel modelFactoryReferentiel;
    private final DTOFactoryReferentiel dtoFactoryReferentiel;
    private final TypeDepenseValidator typeDepenseValidator;
    private final ObjectMapper objectMapper;

    @Override
    public void saveTypeDepense(TypeDepenseAddEditDTO typeDepenseAddEditDTO) {
        typeDepenseValidator.validateForCreation(typeDepenseAddEditDTO);

        String libelle = typeDepenseAddEditDTO.getLibelle().trim();
        checkDuplicateLibelle(libelle, null);

        normalizeDTOData(typeDepenseAddEditDTO);

        TypeDepense model = modelFactoryReferentiel.fromTypeDepenseDTOToTypeDepenseEntity(typeDepenseAddEditDTO);
        model.setActif(true);
        model.setTenantId(typeDepenseAddEditDTO.getTenantId());

        typeDepenseRepository.save(model);
        log.info("{} créé avec succès, ID: {}", TYPEDEPENSE_ENTITY, model.getId());
    }

    @Override
    public void updateTypeDepense(Long id, TypeDepenseAddEditDTO typeDepenseAddEditDTO) {
        typeDepenseValidator.validateForUpdate(id, typeDepenseAddEditDTO);

        TypeDepense typeDepense = findTypeDepenseEntityById(id);

        String nouvelleLibelle = typeDepenseAddEditDTO.getLibelle().trim();
        if (!nouvelleLibelle.equals(typeDepense.getLibelle())) {
            checkDuplicateLibelle(nouvelleLibelle, id);
        }

        normalizeDTOData(typeDepenseAddEditDTO);

        dtoFactoryReferentiel.updateTypeDepenseFields(typeDepense, typeDepenseAddEditDTO);
        typeDepense.setUpdatedAt(LocalDateTime.now());

        typeDepenseRepository.save(typeDepense);
    }

    @Override
    public TypeDepenseAddEditDTO findTypeDepense(Long id) {
        TypeDepense typeDepense = findTypeDepenseEntityById(id);
        return dtoFactoryReferentiel.fromTypeDepenseToTypeDepenseDTO(typeDepense);
    }

    @Override
    public List<ListTypeDepenseDTO> findListTypeDepenses() {
        return typeDepenseRepository.findAllActiveTypeDepenses().stream()
                .map(dtoFactoryReferentiel::fromTypeDepenseToListTypeDepenseDTO)
                .toList();
    }

    @Override
    public Page<ListTypeDepenseDTO> findTypeDepensesPaged(Pageable pageable) {
        return typeDepenseRepository.findTypeDepensePaged(pageable)
                .map(dtoFactoryReferentiel::fromTypeDepenseToListTypeDepenseDTO);
    }

    @Override
    public Page<ListTypeDepenseDTO> findFilteredTypeDepensesPaged(Pageable pageable, String filtre) {
        try {
            RechercheAddressDTO dto = objectMapper.readValue(filtre, RechercheAddressDTO.class);

            return typeDepenseRepository.findFilteredTypeDepensePaged(
                    dto.getLibelle(),
                    pageable
            ).map(dtoFactoryReferentiel::fromTypeDepenseToListTypeDepenseDTO);

        } catch (Exception e) {
            log.error("Erreur de parsing du filtre pour {}: {}", TYPEDEPENSE_ENTITY, filtre, e);
            throw new InvalidXalisPainDataException(
                    TYPEDEPENSE_ENTITY,
                    "Format de filtre invalide: " + e.getMessage()
            );
        }
    }

    @Override
    public void deleteTypeDepense(Long id) {
        TypeDepense typeDepense = findTypeDepenseEntityById(id);

        if (!typeDepense.isActif()) {
            throw new InvalidXalisPainDataException(
                    TYPEDEPENSE_ENTITY,
                    "Le " + TYPEDEPENSE_ENTITY.toLowerCase() + " est déjà inactif"
            );
        }
        typeDepense.setActif(false);
        typeDepense.setUpdatedAt(LocalDateTime.now());
        typeDepenseRepository.save(typeDepense);
    }

    /**************   UTILITAIRE     ******************/

    private void checkDuplicateLibelle(String libelle, Long excludeId) {
        typeDepenseRepository.findByLibelle(libelle)
                .ifPresent(existing -> {
                    if (!existing.getId().equals(excludeId)) {
                        log.warn("Tentative de création d'un {} avec une libellé existante: {}",
                                TYPEDEPENSE_ENTITY, libelle);
                        throw new DuplicateXalisPainException(TYPEDEPENSE_ENTITY, "dénomination", libelle);
                    }
                });
    }

    private void normalizeDTOData(TypeDepenseAddEditDTO dto) {
        dto.setLibelle(dto.getLibelle().trim());
    }

    private TypeDepense findTypeDepenseEntityById(Long id) {
        return typeDepenseRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("{} non trouvé avec l'ID: {}", TYPEDEPENSE_ENTITY, id);
                    return new XalisPainNotFoundException(TYPEDEPENSE_ENTITY, id);
                });
    }
}