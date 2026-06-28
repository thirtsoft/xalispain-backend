package com.wokite.net.referentiel.service;

import com.wokite.net.exception.DuplicateXalisPainException;
import com.wokite.net.exception.InvalidXalisPainDataException;
import com.wokite.net.exception.XalisPainNotFoundException;
import com.wokite.net.referentiel.entity.Fournisseur;
import com.wokite.net.referentiel.mapping.DTOFactoryReferentiel;
import com.wokite.net.referentiel.mapping.ModelFactoryReferentiel;
import com.wokite.net.referentiel.repository.*;
import com.wokite.net.utils.dto.referentiel.*;
import com.wokite.net.validator.refentiel.FournisseurValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;

import static com.wokite.net.utils.constances.referentiel.ConstantsReferentiel.FOURNISSEUR_ENTITY;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReferentielServiceImpl implements ReferentielService {

    private final FournisseurRepository fournisseurRepository;
    private final TypeDepenseRepository typeDepenseRepository;
    private final EtatRepository etatRepository;
    private final ModePaiementRepository modePaiementRepository;
    private final ContinentRepository continentRepository;
    private final PaysRepository paysRepository;
    private final RegionRepository regionRepository;
    private final DepartementRepository departementRepository;
    private final CommuneRepository communeRepository;
    private final ModelFactoryReferentiel modelFactoryReferentiel;
    private final DTOFactoryReferentiel dtoFactoryReferentiel;

    private final FournisseurValidator fournisseurValidator;
    private final ObjectMapper objectMapper;

    @Override
    public void saveFournisseur(FournisseurAddEditDTO fournisseurAddEditDTO) {

        fournisseurValidator.validateForCreation(fournisseurAddEditDTO);

        String denomination = fournisseurAddEditDTO.getDenomination().trim();
        checkDuplicateDenomination(denomination, null);

        normalizeDTOData(fournisseurAddEditDTO);

        Fournisseur model = modelFactoryReferentiel.fromFournisseurDTOToFournisseurEntity(fournisseurAddEditDTO);
        model.setActif(true);
        model.setTenantId(fournisseurAddEditDTO.getTenantId());

        Fournisseur savedFournisseur = fournisseurRepository.save(model);
        log.info("{} créé avec succès, ID: {}", FOURNISSEUR_ENTITY, savedFournisseur.getId());
    }

    @Override
    public void updateFournisseur(Long id, FournisseurAddEditDTO fournisseurAddEditDTO) {
        fournisseurValidator.validateForUpdate(id, fournisseurAddEditDTO);

        Fournisseur fournisseur = findFournisseurEntityById(id);

        String nouvelleDenomination = fournisseurAddEditDTO.getDenomination().trim();
        if (!nouvelleDenomination.equals(fournisseur.getDenomination())) {
            checkDuplicateDenomination(nouvelleDenomination, id);
        }

        normalizeDTOData(fournisseurAddEditDTO);

        dtoFactoryReferentiel.updateFournisseurFields(fournisseur, fournisseurAddEditDTO);
        fournisseur.setUpdatedAt(LocalDateTime.now());

        fournisseurRepository.save(fournisseur);
    }

    private void checkDuplicateDenomination(String denomination, Long excludeId) {
        fournisseurRepository.findByDenomination(denomination)
                .ifPresent(existing -> {
                    if (!existing.getId().equals(excludeId)) {
                        log.warn("Tentative de création d'un {} avec une dénomination existante: {}",
                                FOURNISSEUR_ENTITY, denomination);
                        throw new DuplicateXalisPainException(FOURNISSEUR_ENTITY, "dénomination", denomination);
                    }
                });
    }

    private void normalizeDTOData(FournisseurAddEditDTO dto) {
        dto.setDenomination(dto.getDenomination().trim());
        dto.setMobile(dto.getMobile().trim());

        if (dto.getSigle() != null) {
            dto.setSigle(dto.getSigle().trim().toUpperCase());
        }

        if (dto.getEmail() != null && !dto.getEmail().trim().isEmpty()) {
            dto.setEmail(dto.getEmail().trim().toLowerCase());
        }

        if (dto.getTelephone() != null) {
            dto.setTelephone(dto.getTelephone().trim());
        }

        if (dto.getAdresse() != null) {
            dto.setAdresse(dto.getAdresse().trim());
        }
    }

    private Fournisseur findFournisseurEntityById(Long id) {
        return fournisseurRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("{} non trouvé avec l'ID: {}", FOURNISSEUR_ENTITY, id);
                    return new XalisPainNotFoundException(FOURNISSEUR_ENTITY, id);
                });
    }

    @Override
    @Transactional
    public FournisseurAddEditDTO findFournisseur(Long id) {
        Fournisseur fournisseur = findFournisseurEntityById(id);
        return dtoFactoryReferentiel.fromFournisseurToFournisseurDTO(fournisseur);
    }

    @Override
    public List<ListFournisseurDTO> findListFournisseurs() {
        return fournisseurRepository.findAllActiveFournisseurs().stream()
                .map(dtoFactoryReferentiel::fromFournisseurToListFournisseurDTO)
                .toList();
    }

    @Override
    public List<ListFournisseurDTO> findListFournisseursByCommune(Long communeId) {
        return fournisseurRepository.findFournisseursByCommune(communeId).stream()
                .map(dtoFactoryReferentiel::fromFournisseurToListFournisseurDTO)
                .toList();
    }

    @Override
    public Page<ListFournisseurDTO> findFournisseursPaged(Pageable pageable) {
        return fournisseurRepository.findFournisseurPaged(pageable)
                .map(dtoFactoryReferentiel::fromFournisseurToListFournisseurDTO);
    }

    @Override
    public Page<ListFournisseurDTO> findFilteredFournisseursPaged(Pageable pageable, String filtre) {
        try {
            RechercheAddressDTO dto = objectMapper.readValue(filtre, RechercheAddressDTO.class);

            return fournisseurRepository.findFilteredFournisseurPaged(
                    dto.getLibelle(),
                    pageable
            ).map(dtoFactoryReferentiel::fromFournisseurToListFournisseurDTO);

        } catch (Exception e) {
            log.error("Erreur de parsing du filtre pour {}: {}", FOURNISSEUR_ENTITY, filtre, e);
            throw new InvalidXalisPainDataException(
                    FOURNISSEUR_ENTITY,
                    "Format de filtre invalide: " + e.getMessage()
            );
        }
    }

    @Override
    public void deleteFournisseur(Long id) {
        Fournisseur fournisseur = findFournisseurEntityById(id);

        if (!fournisseur.isActif()) {
            throw new InvalidXalisPainDataException(
                    FOURNISSEUR_ENTITY,
                    "Le " + FOURNISSEUR_ENTITY.toLowerCase() + " est déjà inactif"
            );
        }
        fournisseur.setActif(false);
        fournisseur.setUpdatedAt(LocalDateTime.now());
        fournisseurRepository.save(fournisseur);
    }

    @Override
    public void saveTypeDepense(TypeDepenseAddEditDTO typeDepenseAddEditDTO) {

    }

    @Override
    public void updateTypeDepense(Long id, TypeDepenseAddEditDTO typeDepenseAddEditDTO) {

    }

    @Override
    public TypeDepenseAddEditDTO findTypeDepense(Long id) {
        return null;
    }

    @Override
    public List<ListTypeDepenseDTO> findListTypeDepenses() {
        return List.of();
    }

    @Override
    public Page<ListTypeDepenseDTO> findTypeDepensesPaged(Pageable pageable) {
        return null;
    }

    @Override
    public Page<ListTypeDepenseDTO> findFilteredTypeDepensesPaged(Pageable pageable, String filtre) {
        return null;
    }

    @Override
    public void deleteTypeDepense(Long id) {

    }

    @Override
    public void saveModePaiement(ModePaiementAddEditDTO modePaiementAddEditDTO) {

    }

    @Override
    public void updateModePaiement(Long id, ModePaiementAddEditDTO modePaiementAddEditDTO) {

    }

    @Override
    public ModePaiementAddEditDTO findModePaiement(Long id) {
        return null;
    }

    @Override
    public List<ListModePaiementDTO> findListModePaiements() {
        return List.of();
    }

    @Override
    public Page<ListModePaiementDTO> findModePaiementsPaged(Pageable pageable) {
        return null;
    }

    @Override
    public Page<ListModePaiementDTO> findFilteredModePaiementsPaged(Pageable pageable, String filtre) {
        return null;
    }

    @Override
    public void deleteModePaiement(Long id) {

    }

    @Override
    public List<ListEtatDTO> findListEtats() {
        return List.of();
    }

    @Override
    public void saveCommune(CommuneAddEditDTO communeAddEditDTO) {

    }

    @Override
    public void updateCommune(Long id, CommuneAddEditDTO communeAddEditDTO) {

    }

    @Override
    public CommuneAddEditDTO findCommune(Long id) {
        return null;
    }

    @Override
    public List<ListCommuneDTO> findListCommunes() {
        return List.of();
    }

    @Override
    public List<ListCommuneDTO> findListCommunesByDepartment(Long departmentId) {
        return List.of();
    }

    @Override
    public Page<ListCommuneDTO> findCommunesPaged(Pageable pageable) {
        return null;
    }

    @Override
    public Page<ListCommuneDTO> findFilteredCommunesPaged(Pageable pageable, String filtre) {
        return null;
    }

    @Override
    public void deleteCommune(Long id) {

    }

    @Override
    public void saveDepartement(DepartementAddEditDTO departementAddEditDTO) {

    }

    @Override
    public void updateDepartement(Long id, DepartementAddEditDTO departementAddEditDTO) {

    }

    @Override
    public DepartementAddEditDTO findDepartementById(Long id) {
        return null;
    }

    @Override
    public List<ListDepartementDTO> findListDepartements() {
        return List.of();
    }

    @Override
    public List<ListDepartementDTO> findDepartmentsByRegion(Long regionId) {
        return List.of();
    }

    @Override
    public Page<ListDepartementDTO> findDepartmentsPaged(Pageable pageable) {
        return null;
    }

    @Override
    public Page<ListDepartementDTO> findFilteredDepartmentsPaged(Pageable pageable, String filtre) {
        return null;
    }

    @Override
    public void deleteDepartment(Long id) {

    }

    @Override
    public void saveRegion(RegionAddEditDTO regionAddEditDTO) {

    }

    @Override
    public void updateRegion(Long id, RegionAddEditDTO regionAddEditDTO) {

    }

    @Override
    public RegionAddEditDTO findRegionById(Long id) {
        return null;
    }

    @Override
    public List<ListRegionDTO> findListRegions() {
        return List.of();
    }

    @Override
    public List<ListRegionDTO> findRegionByPays(Long paysId) {
        return List.of();
    }

    @Override
    public Page<ListRegionDTO> findRegionsPaged(Pageable pageable) {
        return null;
    }

    @Override
    public Page<ListRegionDTO> findFilteredRegionsPaged(Pageable pageable, String filtre) {
        return null;
    }

    @Override
    public void deleteRegion(Long id) {

    }

    @Override
    public List<ListPaysDTO> findListPays() {
        return List.of();
    }

    @Override
    public List<ListPaysDTO> findPaysByContinent(Long continentId) {
        return List.of();
    }

    @Override
    public Page<ListPaysDTO> findPaysPaged(Pageable pageable) {
        return null;
    }

    @Override
    public Page<ListPaysDTO> findFilteredPaysPaged(Pageable pageable, String filtre) {
        return null;
    }

    @Override
    public void deletePays(Long id) {

    }

    @Override
    public List<ListContinentDTO> findListContinents() {
        return List.of();
    }

    @Override
    public Page<ListContinentDTO> findContinentsPaged(Pageable pageable) {
        return null;
    }

    @Override
    public Page<ListContinentDTO> findFilteredContinentsPaged(Pageable pageable, String filtre) {
        return null;
    }
}