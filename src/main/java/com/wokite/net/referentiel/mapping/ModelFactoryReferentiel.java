package com.wokite.net.referentiel.mapping;

import com.wokite.net.referentiel.entity.*;
import com.wokite.net.utils.dto.referentiel.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ModelFactoryReferentiel {

    public Fournisseur  fromFournisseurDTOToFournisseurEntity(FournisseurAddEditDTO dto) {
        return Fournisseur.builder()
                .sigle(dto.getSigle())
                .denomination(dto.getDenomination())
                .email(dto.getEmail())
                .mobile(dto.getMobile())
                .telephone(dto.getTelephone())
                .adresse(dto.getAdresse())
                .communeId(dto.getCommuneId())
                .build();
    }

    public ModePaiement fromModePaiementDTOToModePaiementEntity(ModePaiementAddEditDTO dto) {
        return ModePaiement.builder()
                .libelle(dto.getLibelle())
                .build();
    }

    public TypeDepense fromTypeDepenseDTOToTypeDepenseEntity(TypeDepenseAddEditDTO dto) {
        return TypeDepense.builder()
                .libelle(dto.getLibelle())
                .build();
    }

    public Commune fromCommuneDTOToCommuneEntity(CommuneAddEditDTO communeAddEditDTO) {
        return Commune.builder()
                .libelle(communeAddEditDTO.getLibelle())
                .departmentId(communeAddEditDTO.getDepartmentId())
                .build();
    }

    public Departement fromDepartementDTOToDepartementEntity(DepartementAddEditDTO dto) {
        return Departement.builder()
                .code(dto.getCode())
                .libelle(dto.getLibelle())
                .regionId(dto.getRegionId())
                .build();
    }

    public Region fromRegionDTOToRegionEntity(RegionAddEditDTO dto) {
        return Region.builder()
                .code(dto.getCode())
                .libelle(dto.getLibelle())
                .paysId(dto.getPaysId())
                .build();
    }



}