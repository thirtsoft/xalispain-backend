package com.wokite.net.referentiel.mapping;

import com.wokite.net.referentiel.entity.*;
import com.wokite.net.referentiel.repository.*;
import com.wokite.net.utils.dto.referentiel.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DTOFactoryReferentiel {

    private final CommuneRepository communeRepository;

    private final DepartementRepository departementRepository;

    private final PaysRepository paysRepository;

    private final RegionRepository regionRepository;

    private final ContinentRepository continentRepository;

    public FournisseurAddEditDTO fromFournisseurToFournisseurDTO(Fournisseur model) {
        return FournisseurAddEditDTO.builder()
                .id(model.getId())
                .sigle(model.getSigle())
                .denomination(model.getDenomination())
                .email(model.getEmail())
                .mobile(model.getMobile())
                .telephone(model.getTelephone())
                .adresse(model.getAdresse())
                .communeId(model.getCommuneId())
                .tenantId(model.getTenantId())
                .actif(model.getActif())
                .build();
    }

    public void updateFournisseurFields(Fournisseur fournisseur, FournisseurAddEditDTO dto) {
        fournisseur.setDenomination(dto.getDenomination());
        fournisseur.setSigle(dto.getSigle());
        fournisseur.setMobile(dto.getMobile());
        fournisseur.setTelephone(dto.getTelephone());
        fournisseur.setEmail(dto.getEmail());
        fournisseur.setAdresse(dto.getAdresse());
        fournisseur.setCommuneId(dto.getCommuneId());
        fournisseur.setTenantId(dto.getTenantId());
        fournisseur.setUpdatedAt(LocalDateTime.now());
        fournisseur.setActif(dto.isActif());
    }

    public ListFournisseurDTO fromFournisseurToListFournisseurDTO(Fournisseur model) {
        String communeLibelle = "";
        try {
            communeLibelle = communeRepository.findCommuneById(model.getCommuneId()).getLibelle();
        } catch (Exception e) {
            communeLibelle = "Commune " + model.getCommuneId(); // Valeur par défaut
        }
        return ListFournisseurDTO.builder()
                .id(model.getId())
                .sigle(model.getSigle())
                .denomination(model.getDenomination())
                .email(model.getEmail())
                .mobile(model.getMobile())
                .telephone(model.getTelephone())
                .adresse(model.getAdresse())
                .commune(communeLibelle)
                .tenant(model.getTenantId())
                .actif(model.getActif())
                .build();
    }

    public ModePaiementAddEditDTO fromModePaiementToModePaiementDTO(ModePaiement model) {
        return ModePaiementAddEditDTO.builder()
                .id(model.getId())
                .libelle(model.getLibelle())
                .tenantId(model.getTenantId())
                .actif(model.getActif())
                .build();
    }

    public void updateModePaiementFields(ModePaiement modePaiement, ModePaiementAddEditDTO dto) {
        modePaiement.setLibelle(dto.getLibelle());
        modePaiement.setTenantId(dto.getTenantId());
        modePaiement.setUpdatedAt(LocalDateTime.now());
        modePaiement.setActif(dto.isActif());
    }

    public ListModePaiementDTO fromModePaiementToListModePaiementDTO(ModePaiement model) {
        return ListModePaiementDTO.builder()
                .id(model.getId())
                .libelle(model.getLibelle())
                .tenant(model.getTenantId())
                .actif(model.getActif())
                .build();
    }

    public TypeDepenseAddEditDTO fromTypeDepenseToTypeDepenseDTO(TypeDepense model) {
        return TypeDepenseAddEditDTO.builder()
                .id(model.getId())
                .libelle(model.getLibelle())
                .tenantId(model.getTenantId())
                .actif(model.getActif())
                .build();
    }

    public void updateTypeDepenseFields(TypeDepense typeDepense, TypeDepenseAddEditDTO dto) {
        typeDepense.setLibelle(dto.getLibelle());
        typeDepense.setTenantId(dto.getTenantId());
        typeDepense.setUpdatedAt(LocalDateTime.now());
        typeDepense.setActif(dto.isActif());
    }

    public ListTypeDepenseDTO fromTypeDepenseToListTypeDepenseDTO(TypeDepense model) {
        return ListTypeDepenseDTO.builder()
                .id(model.getId())
                .libelle(model.getLibelle())
                .tenant(model.getTenantId())
                .actif(model.getActif())
                .build();
    }

    public ListEtatDTO fromEtatToListEtatDTO(Etat model) {
        return ListEtatDTO.builder()
                .id(model.getId())
                .code(model.getCode())
                .libelle(model.getLibelle())
                .tenant(model.getTenantId())
                .actif(model.getActif())
                .build();
    }

    public CommuneAddEditDTO fromCommuneToCommuneDTO(Commune model) {
        return CommuneAddEditDTO.builder()
                .id(model.getId())
                .libelle(model.getLibelle())
                .departmentId(model.getDepartmentId())
                .tenantId(model.getTenantId())
                .actif(model.getActif())
                .build();
    }

    public void updateCommuneFields(Commune commune, CommuneAddEditDTO dto) {
        commune.setLibelle(dto.getLibelle());
        commune.setDepartmentId(dto.getDepartmentId());
        commune.setTenantId(dto.getTenantId());
        commune.setUpdatedAt(LocalDateTime.now());
        commune.setActif(dto.isActif());
    }

    public ListCommuneDTO fromCommuneToListCommuneDTO(Commune model) {
        return ListCommuneDTO.builder()
                .id(model.getId())
                .libelle(model.getLibelle())
                .department(departementRepository.findDepartementById(model.getDepartmentId()).getLibelle())
                .tenant(model.getTenantId())
                .actif(model.getActif())
                .build();
    }

    public DepartementAddEditDTO fromDepartementToDepartementDTO(Departement model) {
        return DepartementAddEditDTO.builder()
                .id(model.getId())
                .code(model.getCode())
                .libelle(model.getLibelle())
                .regionId(model.getRegionId())
                .tenantId(model.getTenantId())
                .actif(model.getActif())
                .build();
    }

    public void updateDepartementFields(Departement departement, DepartementAddEditDTO dto) {
        departement.setLibelle(dto.getLibelle());
        departement.setCode(departement.getCode());
        departement.setRegionId(dto.getRegionId());
        departement.setTenantId(dto.getTenantId());
        departement.setUpdatedAt(LocalDateTime.now());
        departement.setActif(dto.isActif());
    }

    public ListDepartementDTO fromDepartementToListDepartementDTO(Departement model) {
        return ListDepartementDTO.builder()
                .id(model.getId())
                .code(model.getCode())
                .libelle(model.getLibelle())
                .region(regionRepository.findRegionById(
                        model.getRegionId()).getLibelle())
                .tenant(model.getTenantId())
                .actif(model.getActif())
                .build();
    }

    public RegionAddEditDTO fromRegionToRegionDTO(Region model) {
        return RegionAddEditDTO.builder()
                .id(model.getId())
                .code(model.getCode())
                .libelle(model.getLibelle())
                .paysId(model.getPaysId())
                .tenantId(model.getTenantId())
                .actif(model.getActif())
                .build();
    }

    public void updateRegionFields(Region region, RegionAddEditDTO dto) {
        region.setLibelle(dto.getLibelle());
        region.setCode(dto.getCode());
        region.setPaysId(dto.getPaysId());
        region.setTenantId(dto.getTenantId());
        region.setUpdatedAt(LocalDateTime.now());
        region.setActif(dto.isActif());
    }

    public ListRegionDTO fromRegionToListRegionDTO(Region model) {
        return ListRegionDTO.builder()
                .id(model.getId())
                .code(model.getCode())
                .libelle(model.getLibelle())
                .pays(paysRepository.findPaysById(model.getPaysId()).getLibelle())
                .tenant(model.getTenantId())
                .actif(model.getActif())
                .build();
    }

    public ListPaysDTO fromPaysToListPaysDTO(Pays model) {
        return ListPaysDTO.builder()
                .id(model.getId())
                .code(model.getCode())
                .libelle(model.getLibelle())
                .continent(continentRepository.findContinentById(
                        model.getContinentId()
                ).getLibelle())
                .tenant(model.getTenantId())
                .actif(model.getActif())
                .build();
    }

    public ListContinentDTO fromContinentToListContinentDTO(Continent model) {
        return ListContinentDTO.builder()
                .id(model.getId())
                .code(model.getCode())
                .libelle(model.getLibelle())
                .tenant(model.getTenantId())
                .actif(model.getActif())
                .build();
    }

}