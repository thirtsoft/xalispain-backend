package com.wokite.net.referentiel.service.Impl;

import com.wokite.net.exception.DuplicateXalisPainException;
import com.wokite.net.exception.InvalidXalisPainDataException;
import com.wokite.net.exception.XalisPainNotFoundException;
import com.wokite.net.referentiel.entity.Commune;
import com.wokite.net.referentiel.entity.Departement;
import com.wokite.net.referentiel.entity.Pays;
import com.wokite.net.referentiel.entity.Region;
import com.wokite.net.referentiel.mapping.DTOFactoryReferentiel;
import com.wokite.net.referentiel.mapping.ModelFactoryReferentiel;
import com.wokite.net.referentiel.repository.CommuneRepository;
import com.wokite.net.referentiel.repository.DepartementRepository;
import com.wokite.net.referentiel.repository.PaysRepository;
import com.wokite.net.referentiel.repository.RegionRepository;
import com.wokite.net.referentiel.service.LocaliteService;
import com.wokite.net.utils.dto.referentiel.*;
import com.wokite.net.validator.refentiel.LocaliteValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;

import static com.wokite.net.utils.constances.referentiel.ConstantsReferentiel.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocaliteServiceImpl implements LocaliteService {

    private final PaysRepository paysRepository;
    private final RegionRepository regionRepository;
    private final DepartementRepository departementRepository;
    private final CommuneRepository communeRepository;
    private final ModelFactoryReferentiel modelFactoryReferentiel;
    private final DTOFactoryReferentiel dtoFactoryReferentiel;

    private final LocaliteValidator localiteValidator;
    private final ObjectMapper objectMapper;

    @Override
    public void saveCommune(CommuneAddEditDTO communeAddEditDTO) {
        localiteValidator.validateForCreationCommune(communeAddEditDTO);

        String libelle = communeAddEditDTO.getLibelle().trim();
        checkDuplicateLibelleDepartment(libelle, null);

        normalizeCommuneData(communeAddEditDTO);

        Commune model = modelFactoryReferentiel.fromCommuneDTOToCommuneEntity(communeAddEditDTO);
        model.setActif(true);
        model.setTenantId(communeAddEditDTO.getTenantId());

        communeRepository.save(model);
        log.info("{} créé avec succès, COMMUNEID: {}", COMMUNE_ENTITY, model.getId());
    }

    @Override
    public void updateCommune(Long id, CommuneAddEditDTO communeAddEditDTO) {
        localiteValidator.validateForUpdateCommune(id, communeAddEditDTO);

        Commune commune = findCommuneEntityById(id);

        String nouvelleLibelle = communeAddEditDTO.getLibelle().trim();
        if (!nouvelleLibelle.equals(commune.getLibelle())) {
            checkDuplicateLibelleCommune(nouvelleLibelle, id);
        }

        normalizeCommuneData(communeAddEditDTO);

        dtoFactoryReferentiel.updateCommuneFields(commune, communeAddEditDTO);
        commune.setUpdatedAt(LocalDateTime.now());

        communeRepository.save(commune);
    }

    @Override
    public CommuneAddEditDTO findCommune(Long id) {

        Commune commune = findCommuneEntityById(id);
        return dtoFactoryReferentiel.fromCommuneToCommuneDTO(commune);
    }

    @Override
    public List<ListCommuneDTO> findListCommunes() {
        return communeRepository.findAllActiveCommunes().stream()
                .map(dtoFactoryReferentiel::fromCommuneToListCommuneDTO)
                .toList();
    }

    @Override
    public List<ListCommuneDTO> findListCommunesByDepartment(Long departmentId) {
        return communeRepository.findCommuneByDepartement(departmentId).stream()
                .map(dtoFactoryReferentiel::fromCommuneToListCommuneDTO)
                .toList();
    }

    @Override
    public Page<ListCommuneDTO> findCommunesPaged(Pageable pageable) {
        return communeRepository.findCommunePaged(pageable)
                .map(dtoFactoryReferentiel::fromCommuneToListCommuneDTO);
    }

    @Override
    public Page<ListCommuneDTO> findFilteredCommunesPaged(Pageable pageable, String filtre) {
        try {
            RechercheAddressDTO dto = objectMapper.readValue(filtre, RechercheAddressDTO.class);

            return communeRepository.findFilteredCommunePaged(
                    dto.getLibelle(),
                    dto.getDepartmentId(),
                    pageable
            ).map(dtoFactoryReferentiel::fromCommuneToListCommuneDTO);

        } catch (Exception e) {
            log.error("Erreur de parsing du filtre pour {}: {}", COMMUNE_ENTITY, filtre, e);
            throw new InvalidXalisPainDataException(
                    COMMUNE_ENTITY,
                    "Format de filtre invalide: " + e.getMessage()
            );
        }
    }

    @Override
    public void deleteCommune(Long id) {
        Commune commune = findCommuneEntityById(id);

        if (!commune.isActif()) {
            throw new InvalidXalisPainDataException(
                    COMMUNE_ENTITY,
                    "Le " + COMMUNE_ENTITY.toLowerCase() + " est déjà inactif"
            );
        }
        commune.setActif(false);
        commune.setUpdatedAt(LocalDateTime.now());
        communeRepository.save(commune);
    }

    @Override
    public void saveDepartement(DepartementAddEditDTO departementAddEditDTO) {
        localiteValidator.validateForCreationDepartment(departementAddEditDTO);

        String code = departementAddEditDTO.getCode().trim();
        checkDuplicateCodeDepartment(code, null);

        String libelle = departementAddEditDTO.getLibelle().trim();
        checkDuplicateLibelleDepartment(libelle, null);

        normalizeDepartmentData(departementAddEditDTO);

        Departement model = modelFactoryReferentiel.fromDepartementDTOToDepartementEntity(departementAddEditDTO);
        model.setActif(true);
        model.setTenantId(departementAddEditDTO.getTenantId());

        departementRepository.save(model);
        log.info("{} créé avec succès, DEPTID: {}", DEPARTEMENT_ENTITY, model.getId());
    }

    @Override
    public void updateDepartement(Long id, DepartementAddEditDTO departementAddEditDTO) {
        localiteValidator.validateForUpdateDepartement(id, departementAddEditDTO);

        Departement departement = findDepartementEntityById(id);

        String nouvelleCode = departementAddEditDTO.getCode().trim();
        if (!nouvelleCode.equals(departement.getCode())) {
            checkDuplicateCodeDepartment(nouvelleCode, id);
        }

        String nouvelleLibelle = departementAddEditDTO.getLibelle().trim();
        if (!nouvelleLibelle.equals(departement.getLibelle())) {
            checkDuplicateLibelleDepartment(nouvelleLibelle, id);
        }

        normalizeDepartmentData(departementAddEditDTO);

        dtoFactoryReferentiel.updateDepartementFields(departement, departementAddEditDTO);
        departement.setUpdatedAt(LocalDateTime.now());

        departementRepository.save(departement);
    }

    @Override
    public DepartementAddEditDTO findDepartementById(Long id) {
        Departement departement = findDepartementEntityById(id);
        return dtoFactoryReferentiel.fromDepartementToDepartementDTO(departement);
    }

    @Override
    public List<ListDepartementDTO> findListDepartements() {
        return departementRepository.findAllActiveDepartements().stream()
                .map(dtoFactoryReferentiel::fromDepartementToListDepartementDTO)
                .toList();
    }

    @Override
    public List<ListDepartementDTO> findDepartmentsByRegion(Long regionId) {
        return departementRepository.findDepartementByRegion(regionId).stream()
                .map(dtoFactoryReferentiel::fromDepartementToListDepartementDTO)
                .toList();
    }

    @Override
    public Page<ListDepartementDTO> findDepartmentsPaged(Pageable pageable) {
        return departementRepository.findDepartementPaged(pageable)
                .map(dtoFactoryReferentiel::fromDepartementToListDepartementDTO);
    }

    @Override
    public Page<ListDepartementDTO> findFilteredDepartmentsPaged(Pageable pageable, String filtre) {
        try {
            RechercheAddressDTO dto = objectMapper.readValue(filtre, RechercheAddressDTO.class);

            return departementRepository.findFilteredDepartementPaged(
                    dto.getLibelle(),
                    dto.getRegionId(),
                    pageable
            ).map(dtoFactoryReferentiel::fromDepartementToListDepartementDTO);

        } catch (Exception e) {
            log.error("Erreur de parsing du filtre pour {}: {}", DEPARTEMENT_ENTITY, filtre, e);
            throw new InvalidXalisPainDataException(
                    DEPARTEMENT_ENTITY,
                    "Format de filtre invalide: " + e.getMessage()
            );
        }
    }

    @Override
    public void deleteDepartment(Long id) {
        Departement departement = findDepartementEntityById(id);

        if (!departement.isActif()) {
            throw new InvalidXalisPainDataException(
                    DEPARTEMENT_ENTITY,
                    "Le " + DEPARTEMENT_ENTITY.toLowerCase() + " est déjà inactif"
            );
        }
        departement.setActif(false);
        departement.setUpdatedAt(LocalDateTime.now());
        departementRepository.save(departement);
    }

    @Override
    public void saveRegion(RegionAddEditDTO regionAddEditDTO) {
        localiteValidator.validateForCreationRegion(regionAddEditDTO);

        String code = regionAddEditDTO.getCode().trim();
        checkDuplicateCodeRegion(code, null);

        String libelle = regionAddEditDTO.getLibelle().trim();
        checkDuplicateLibelleRegion(libelle, null);

        normalizeRegionData(regionAddEditDTO);

        Region model = modelFactoryReferentiel.fromRegionDTOToRegionEntity(regionAddEditDTO);
        model.setActif(true);
        model.setTenantId(regionAddEditDTO.getTenantId());

        regionRepository.save(model);
        log.info("{} créé avec succès, RegionID: {}", REGION_ENTITY, model.getId());
    }

    @Override
    public void updateRegion(Long id, RegionAddEditDTO regionAddEditDTO) {
        localiteValidator.validateForUpdateRegion(id, regionAddEditDTO);

        Region region = findRegionEntityById(id);

        String nouvelleCode = regionAddEditDTO.getCode().trim();
        if (!nouvelleCode.equals(region.getCode())) {
            checkDuplicateCodeRegion(nouvelleCode, id);
        }

        String nouvelleLibelle = regionAddEditDTO.getLibelle().trim();
        if (!nouvelleLibelle.equals(region.getLibelle())) {
            checkDuplicateLibelleRegion(nouvelleLibelle, id);
        }

        normalizeRegionData(regionAddEditDTO);

        dtoFactoryReferentiel.updateRegionFields(region, regionAddEditDTO);
        region.setUpdatedAt(LocalDateTime.now());

        regionRepository.save(region);
    }

    @Override
    public RegionAddEditDTO findRegionById(Long id) {
        Region region = findRegionEntityById(id);
        return dtoFactoryReferentiel.fromRegionToRegionDTO(region);
    }

    @Override
    public List<ListRegionDTO> findListRegions() {
        return regionRepository.findAllActiveRegions().stream()
                .map(dtoFactoryReferentiel::fromRegionToListRegionDTO)
                .toList();
    }

    @Override
    public List<ListRegionDTO> findRegionByPays(Long paysId) {
        return regionRepository.findRegionByPays(paysId).stream()
                .map(dtoFactoryReferentiel::fromRegionToListRegionDTO)
                .toList();
    }

    @Override
    public Page<ListRegionDTO> findRegionsPaged(Pageable pageable) {
        return regionRepository.findRegionPaged(pageable)
                .map(dtoFactoryReferentiel::fromRegionToListRegionDTO);
    }

    @Override
    public Page<ListRegionDTO> findFilteredRegionsPaged(Pageable pageable, String filtre) {
        try {
            RechercheAddressDTO dto = objectMapper.readValue(filtre, RechercheAddressDTO.class);

            return regionRepository.findFilteredRegionPaged(
                    dto.getLibelle(),
                    dto.getPaysId(),
                    pageable
            ).map(dtoFactoryReferentiel::fromRegionToListRegionDTO);

        } catch (Exception e) {
            log.error("Erreur de parsing du filtre pour {}: {}", REGION_ENTITY, filtre, e);
            throw new InvalidXalisPainDataException(
                    REGION_ENTITY,
                    "Format de filtre invalide: " + e.getMessage()
            );
        }
    }

    @Override
    public void deleteRegion(Long id) {
        Region region = findRegionEntityById(id);

        if (!region.isActif()) {
            throw new InvalidXalisPainDataException(
                    REGION_ENTITY,
                    "Le " + REGION_ENTITY.toLowerCase() + " est déjà inactif"
            );
        }
        region.setActif(false);
        region.setUpdatedAt(LocalDateTime.now());
        regionRepository.save(region);
    }

    @Override
    public List<ListPaysDTO> findListPays() {
        return paysRepository.findAllActivePays().stream()
                .map(dtoFactoryReferentiel::fromPaysToListPaysDTO)
                .toList();
    }

    @Override
    public List<ListPaysDTO> findPaysByContinent(Long continentId) {
        return paysRepository.findPaysByContinent(continentId).stream()
                .map(dtoFactoryReferentiel::fromPaysToListPaysDTO)
                .toList();
    }

    @Override
    public Page<ListPaysDTO> findPaysPaged(Pageable pageable) {
        return paysRepository.findPaysPaged(pageable)
                .map(dtoFactoryReferentiel::fromPaysToListPaysDTO);
    }

    @Override
    public Page<ListPaysDTO> findFilteredPaysPaged(Pageable pageable, String filtre) {
        try {
            RechercheAddressDTO dto = objectMapper.readValue(filtre, RechercheAddressDTO.class);

            return paysRepository.findFilteredPaysPaged(
                    dto.getLibelle(),
                    dto.getContinentId(),
                    pageable
            ).map(dtoFactoryReferentiel::fromPaysToListPaysDTO);

        } catch (Exception e) {
            log.error("Erreur de parsing du filtre pour {}: {}", PAYS_ENTITY, filtre, e);
            throw new InvalidXalisPainDataException(
                    PAYS_ENTITY,
                    "Format de filtre invalide: " + e.getMessage()
            );
        }
    }

    @Override
    public void deletePays(Long id) {
        Pays pays = findPaysEntityById(id);

        if (!pays.isActif()) {
            throw new InvalidXalisPainDataException(
                    PAYS_ENTITY,
                    "Le " + PAYS_ENTITY.toLowerCase() + " est déjà inactif"
            );
        }
        pays.setActif(false);
        pays.setUpdatedAt(LocalDateTime.now());
        paysRepository.save(pays);
    }

    @Override
    public List<ListContinentDTO> findListContinents() {
        return List.of();
    }

    /**************   UTILITAIRE     ******************/

    private void checkDuplicateLibelleCommune(String libelle, Long excludeId) {
        communeRepository.findByLibelle(libelle)
                .ifPresent(existing -> {
                    if (!existing.getId().equals(excludeId)) {
                        log.warn("Tentative de création d'un {} avec une libellé existante: {}",
                                COMMUNE_ENTITY, libelle);
                        throw new DuplicateXalisPainException(COMMUNE_ENTITY, "libelle", libelle);
                    }
                });
    }

    private void normalizeCommuneData(CommuneAddEditDTO dto) {
        dto.setLibelle(dto.getLibelle().trim());
    }

    private Commune findCommuneEntityById(Long id) {
        return communeRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("{} non trouvé avec l'ID: {}", COMMUNE_ENTITY, id);
                    return new XalisPainNotFoundException(COMMUNE_ENTITY, id);
                });
    }

    //
    private void checkDuplicateCodeDepartment(String code, Long excludeId) {
        departementRepository.findByCode(code)
                .ifPresent(existing -> {
                    if (!existing.getId().equals(excludeId)) {
                        log.warn("Tentative de création d'un {} avec un libellé existant: {}",
                                DEPARTEMENT_ENTITY, code);
                        throw new DuplicateXalisPainException(DEPARTEMENT_ENTITY, "code", code);
                    }
                });
    }

    private void checkDuplicateLibelleDepartment(String libelle, Long excludeId) {
        departementRepository.findByLibelle(libelle)
                .ifPresent(existing -> {
                    if (!existing.getId().equals(excludeId)) {
                        log.warn("Tentative de création d'un {} avec un libellé existante: {}",
                                DEPARTEMENT_ENTITY, libelle);
                        throw new DuplicateXalisPainException(DEPARTEMENT_ENTITY, "libelle", libelle);
                    }
                });
    }

    private void normalizeDepartmentData(DepartementAddEditDTO dto) {
        dto.setLibelle(dto.getLibelle().trim());
        dto.setCode(dto.getCode().trim());
    }

    private Departement findDepartementEntityById(Long id) {
        return departementRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("{} non trouvé avec l'Id: {}", DEPARTEMENT_ENTITY, id);
                    return new XalisPainNotFoundException(DEPARTEMENT_ENTITY, id);
                });
    }

    private void checkDuplicateCodeRegion(String code, Long excludeId) {
        regionRepository.findByCode(code)
                .ifPresent(existing -> {
                    if (!existing.getId().equals(excludeId)) {
                        log.warn("Tentative de création d'un {} avec un code existant: {}",
                                REGION_ENTITY, code);
                        throw new DuplicateXalisPainException(REGION_ENTITY, "code", code);
                    }
                });
    }

    private void checkDuplicateLibelleRegion(String libelle, Long excludeId) {
        regionRepository.findByLibelle(libelle)
                .ifPresent(existing -> {
                    if (!existing.getId().equals(excludeId)) {
                        log.warn("Tentative de la création d'un {} avec un libellé existant: {}",
                                REGION_ENTITY, libelle);
                        throw new DuplicateXalisPainException(REGION_ENTITY, "libelle", libelle);
                    }
                });
    }

    private void normalizeRegionData(RegionAddEditDTO dto) {
        dto.setLibelle(dto.getLibelle().trim());
        dto.setCode(dto.getCode().trim());
    }

    private Region findRegionEntityById(Long id) {
        return regionRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("{} non trouvé avec l'Idd: {}", REGION_ENTITY, id);
                    return new XalisPainNotFoundException(REGION_ENTITY, id);
                });
    }

    private Pays findPaysEntityById(Long id) {
        return paysRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("{} non trouvé avec l'Identifiant: {}", PAYS_ENTITY, id);
                    return new XalisPainNotFoundException(PAYS_ENTITY, id);
                });
    }
}