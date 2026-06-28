package com.wokite.net.referentiel.service.Impl;

import com.wokite.net.exception.DuplicateXalisPainException;
import com.wokite.net.exception.InvalidXalisPainDataException;
import com.wokite.net.exception.XalisPainNotFoundException;
import com.wokite.net.referentiel.entity.ModePaiement;
import com.wokite.net.referentiel.mapping.DTOFactoryReferentiel;
import com.wokite.net.referentiel.mapping.ModelFactoryReferentiel;
import com.wokite.net.referentiel.repository.ModePaiementRepository;
import com.wokite.net.referentiel.service.ModePaiementService;
import com.wokite.net.utils.dto.referentiel.ListModePaiementDTO;
import com.wokite.net.utils.dto.referentiel.ModePaiementAddEditDTO;
import com.wokite.net.utils.dto.referentiel.RechercheAddressDTO;
import com.wokite.net.validator.refentiel.ModePaiementValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;

import static com.wokite.net.utils.constances.referentiel.ConstantsReferentiel.MOEDPAIEMENT_ENTITY;

@Service
@RequiredArgsConstructor
@Slf4j
public class ModePaiementServiceImpl implements ModePaiementService {

    private final ModePaiementRepository modePaiementRepository;
    private final ModelFactoryReferentiel modelFactoryReferentiel;
    private final DTOFactoryReferentiel dtoFactoryReferentiel;
    private final ObjectMapper objectMapper;
    private final ModePaiementValidator modePaiementValidator;

    @Override
    public void saveModePaiement(ModePaiementAddEditDTO modePaiementAddEditDTO) {
        modePaiementValidator.validateForCreation(modePaiementAddEditDTO);

        String libelle = modePaiementAddEditDTO.getLibelle().trim();
        checkDuplicateLibelle(libelle, null);

        normalizeDTOData(modePaiementAddEditDTO);

        ModePaiement model = modelFactoryReferentiel.fromModePaiementDTOToModePaiementEntity(modePaiementAddEditDTO);
        model.setActif(true);
        model.setTenantId(modePaiementAddEditDTO.getTenantId());

        modePaiementRepository.save(model);
        log.info("{} créé avec succès, ID: {}", MOEDPAIEMENT_ENTITY, model.getId());
    }

    @Override
    public void updateModePaiement(Long id, ModePaiementAddEditDTO modePaiementAddEditDTO) {
        modePaiementValidator.validateForUpdate(id, modePaiementAddEditDTO);

        ModePaiement modePaiement = findModePaiementEntityById(id);

        String nouvelleLibelle = modePaiementAddEditDTO.getLibelle().trim();
        if (!nouvelleLibelle.equals(modePaiement.getLibelle())) {
            checkDuplicateLibelle(nouvelleLibelle, id);
        }

        normalizeDTOData(modePaiementAddEditDTO);

        dtoFactoryReferentiel.updateModePaiementFields(modePaiement, modePaiementAddEditDTO);
        modePaiement.setUpdatedAt(LocalDateTime.now());

        modePaiementRepository.save(modePaiement);
    }

    @Override
    public ModePaiementAddEditDTO findModePaiement(Long id) {
        ModePaiement modePaiement = findModePaiementEntityById(id);
        return dtoFactoryReferentiel.fromModePaiementToModePaiementDTO(modePaiement);
    }

    @Override
    public List<ListModePaiementDTO> findListModePaiements() {
        return modePaiementRepository.findAllActiveModePaiements().stream()
                .map(dtoFactoryReferentiel::fromModePaiementToListModePaiementDTO)
                .toList();
    }

    @Override
    public Page<ListModePaiementDTO> findModePaiementsPaged(Pageable pageable) {
        return modePaiementRepository.findModePaiementPaged(pageable)
                .map(dtoFactoryReferentiel::fromModePaiementToListModePaiementDTO);
    }

    @Override
    public Page<ListModePaiementDTO> findFilteredModePaiementsPaged(Pageable pageable, String filtre) {
        try {
            RechercheAddressDTO dto = objectMapper.readValue(filtre, RechercheAddressDTO.class);

            return modePaiementRepository.findFilteredModePaiementPaged(
                    dto.getLibelle(),
                    pageable
            ).map(dtoFactoryReferentiel::fromModePaiementToListModePaiementDTO);

        } catch (Exception e) {
            log.error("Erreur de parsing du filtre pour {}: {}", MOEDPAIEMENT_ENTITY, filtre, e);
            throw new InvalidXalisPainDataException(
                    MOEDPAIEMENT_ENTITY,
                    "Format de filtre invalide: " + e.getMessage()
            );
        }
    }

    @Override
    public void deleteModePaiement(Long id) {
        ModePaiement modePaiement = findModePaiementEntityById(id);

        if (!modePaiement.isActif()) {
            throw new InvalidXalisPainDataException(
                    MOEDPAIEMENT_ENTITY,
                    "Le " + MOEDPAIEMENT_ENTITY.toLowerCase() + " est déjà inactif"
            );
        }
        modePaiement.setActif(false);
        modePaiement.setUpdatedAt(LocalDateTime.now());
        modePaiementRepository.save(modePaiement);
    }

    private void checkDuplicateLibelle(String libelle, Long excludeId) {
        modePaiementRepository.findByLibelle(libelle)
                .ifPresent(existing -> {
                    if (!existing.getId().equals(excludeId)) {
                        log.warn("Tentative de création d'un {} avec une libellé existante: {}",
                                MOEDPAIEMENT_ENTITY, libelle);
                        throw new DuplicateXalisPainException(MOEDPAIEMENT_ENTITY, "dénomination", libelle);
                    }
                });
    }

    private void normalizeDTOData(ModePaiementAddEditDTO dto) {
        dto.setLibelle(dto.getLibelle().trim());
    }

    private ModePaiement findModePaiementEntityById(Long id) {
        return modePaiementRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("{} non trouvé avec l'ID: {}", MOEDPAIEMENT_ENTITY, id);
                    return new XalisPainNotFoundException(MOEDPAIEMENT_ENTITY, id);
                });
    }
}