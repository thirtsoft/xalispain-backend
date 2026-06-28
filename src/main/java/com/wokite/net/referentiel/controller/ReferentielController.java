package com.wokite.net.referentiel.controller;

import com.wokite.net.referentiel.controller.Api.ReferentielApi;
import com.wokite.net.referentiel.service.*;
import com.wokite.net.utils.dto.referentiel.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ReferentielController implements ReferentielApi {

    private final ReferentielService referentielService;
    private final FournisseurService fournisseurService;
    private final TypeDepenseService typeDepenseService;
    private final ModePaiementService modePaiementService;
    private final LocaliteService localiteService;

    @Override
    public ResponseEntity<Void> createFournisseur(FournisseurAddEditDTO fournisseurAddEditDTO) {
        fournisseurService.saveFournisseur(fournisseurAddEditDTO);
        return ResponseEntity.created(URI.create("/xalispain/api/v1/referentiel/fournisseur/save")).build();
    }

    @Override
    public ResponseEntity<Void> updateFournisseur(Long id, FournisseurAddEditDTO fournisseurAddEditDTO) {
        fournisseurService.updateFournisseur(id, fournisseurAddEditDTO);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<FournisseurAddEditDTO> getFournisseurById(Long id) {
        FournisseurAddEditDTO fournisseur = fournisseurService.findFournisseur(id);
        return ResponseEntity.ok(fournisseur);
    }

    @Override
    public ResponseEntity<List<ListFournisseurDTO>> getAllFournisseurs() {
        List<ListFournisseurDTO> fournisseurs = fournisseurService.findListFournisseurs();
        if (fournisseurs.isEmpty()) {
            log.info("Aucun fournisseur trouvé");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(fournisseurs);
    }

    @Override
    public ResponseEntity<List<ListFournisseurDTO>> getFournisseursByCommune(Long communeId) {
        List<ListFournisseurDTO> fournisseurs = fournisseurService.findListFournisseursByCommune(communeId);
        if (fournisseurs.isEmpty()) {
            log.info("Aucun fournisseur trouvé pour la commune ID: {}", communeId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(fournisseurs);
    }

    @Override
    public ResponseEntity<Page<ListFournisseurDTO>> getFournisseursPaged(Pageable pageable) {
        Page<ListFournisseurDTO> fournisseurs = fournisseurService.findFournisseursPaged(pageable);
        if (fournisseurs.isEmpty()) {
            log.info("Aucun fournisseur trouvé pour cette page");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(fournisseurs);
    }

    @Override
    public ResponseEntity<Page<ListFournisseurDTO>> searchFournisseurs(String filtre, Pageable pageable) {
        Page<ListFournisseurDTO> fournisseurs = fournisseurService.findFilteredFournisseursPaged(pageable, filtre);
        if (fournisseurs.isEmpty()) {
            log.info("Aucun résultat trouvé pour la recherche");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(fournisseurs);
    }

    @Override
    public ResponseEntity<Void> deleteFournisseur(Long id) {
        fournisseurService.deleteFournisseur(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> createTypeDepense(TypeDepenseAddEditDTO typeDepenseAddEditDTO) {
        typeDepenseService.saveTypeDepense(typeDepenseAddEditDTO);
        return ResponseEntity.created(URI.create("/xalispain/api/v1/referentiel/typedepense/save")).build();
    }

    @Override
    public ResponseEntity<Void> updateTypeDepense(Long id, TypeDepenseAddEditDTO typeDepenseAddEditDTO) {
        typeDepenseService.updateTypeDepense(id, typeDepenseAddEditDTO);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<TypeDepenseAddEditDTO> getTypeDepenseById(Long id) {
        TypeDepenseAddEditDTO typeDepense = typeDepenseService.findTypeDepense(id);
        return ResponseEntity.ok(typeDepense);
    }

    @Override
    public ResponseEntity<List<ListTypeDepenseDTO>> getAllTypeDepenses() {
        List<ListTypeDepenseDTO> typeDepenses = typeDepenseService.findListTypeDepenses();
        if (typeDepenses.isEmpty()) {
            log.info("Aucun typeDepenses trouvé");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(typeDepenses);
    }

    @Override
    public ResponseEntity<Page<ListTypeDepenseDTO>> getTypeDepensesPaged(Pageable pageable) {
        Page<ListTypeDepenseDTO> typeDepenses = typeDepenseService.findTypeDepensesPaged(pageable);
        if (typeDepenses.isEmpty()) {
            log.info("Aucun typeDepenses trouvé pour cette page");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(typeDepenses);
    }

    @Override
    public ResponseEntity<Page<ListTypeDepenseDTO>> searchTypeDepenses(String filtre, Pageable pageable) {
        Page<ListTypeDepenseDTO> typeDepenses = typeDepenseService.findFilteredTypeDepensesPaged(pageable, filtre);
        if (typeDepenses.isEmpty()) {
            log.info("Aucun type de Depenses trouvé pour cette page");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(typeDepenses);
    }

    @Override
    public ResponseEntity<Void> deleteTypeDepense(Long id) {
        typeDepenseService.deleteTypeDepense(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> createModePaiement(ModePaiementAddEditDTO modePaiementAddEditDTO) {
        modePaiementService.saveModePaiement(modePaiementAddEditDTO);
        return ResponseEntity.created(URI.create("/xalispain/api/v1/referentiel/modepaiement/save")).build();
    }

    @Override
    public ResponseEntity<Void> updateModePaiement(Long id, ModePaiementAddEditDTO modePaiementAddEditDTO) {
        modePaiementService.updateModePaiement(id, modePaiementAddEditDTO);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<ModePaiementAddEditDTO> getModePaiementById(Long id) {
        ModePaiementAddEditDTO modePaiement = modePaiementService.findModePaiement(id);
        return ResponseEntity.ok(modePaiement);
    }

    @Override
    public ResponseEntity<List<ListModePaiementDTO>> getAllModePaiements() {
        List<ListModePaiementDTO> modePaiements = modePaiementService.findListModePaiements();
        if (modePaiements.isEmpty()) {
            log.info("Aucun modePaiements trouvé");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(modePaiements);
    }

    @Override
    public ResponseEntity<Page<ListModePaiementDTO>> getModePaiementsPaged(Pageable pageable) {
        Page<ListModePaiementDTO> modePaiements = modePaiementService.findModePaiementsPaged(pageable);
        if (modePaiements.isEmpty()) {
            log.info("Aucun mode de Paiements trouvé pour cette page");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(modePaiements);
    }

    @Override
    public ResponseEntity<Page<ListModePaiementDTO>> searchModePaiements(String filtre, Pageable pageable) {
        Page<ListModePaiementDTO> modePaiements = modePaiementService.findFilteredModePaiementsPaged(pageable, filtre);
        if (modePaiements.isEmpty()) {
            log.info("Aucun mode dePaiements trouvé pour cette page");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(modePaiements);
    }

    @Override
    public ResponseEntity<Void> deleteModePaiement(Long id) {
        modePaiementService.deleteModePaiement(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> createCommune(CommuneAddEditDTO communeAddEditDTO) {
        localiteService.saveCommune(communeAddEditDTO);
        return ResponseEntity.created(URI.create("/xalispain/api/v1/referentiel/commune/save")).build();
    }

    @Override
    public ResponseEntity<Void> updateCommune(Long id, CommuneAddEditDTO communeAddEditDTO) {
        localiteService.updateCommune(id, communeAddEditDTO);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<CommuneAddEditDTO> getCommuneById(Long id) {
        CommuneAddEditDTO commune = localiteService.findCommune(id);
        return ResponseEntity.ok(commune);
    }

    @Override
    public ResponseEntity<List<ListCommuneDTO>> getAllCommunes() {
        List<ListCommuneDTO> communes = localiteService.findListCommunes();
        if (communes.isEmpty()) {
            log.info("Aucun communes trouvé");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(communes);
    }

    @Override
    public ResponseEntity<List<ListCommuneDTO>> getCommunesByDepartment(Long departmentId) {
        List<ListCommuneDTO> communes = localiteService.findListCommunesByDepartment(departmentId);
        if (communes.isEmpty()) {
            log.info("Aucun commune trouvé");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(communes);
    }

    @Override
    public ResponseEntity<Page<ListCommuneDTO>> getCommunesPaged(Pageable pageable) {
        Page<ListCommuneDTO> communes = localiteService.findCommunesPaged(pageable);
        if (communes.isEmpty()) {
            log.info("Aucun communes trouvé pour cette page");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(communes);
    }

    @Override
    public ResponseEntity<Page<ListCommuneDTO>> searchCommunes(String filtre, Pageable pageable) {
        Page<ListCommuneDTO> communes = localiteService.findFilteredCommunesPaged(pageable, filtre);
        if (communes.isEmpty()) {
            log.info("Aucun communess trouvé pour cette page");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(communes);
    }

    @Override
    public ResponseEntity<Void> deleteCommune(Long id) {
        localiteService.deleteCommune(id);
        return  ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> createDepartement(DepartementAddEditDTO departementAddEditDTO) {
        localiteService.saveDepartement(departementAddEditDTO);
        return ResponseEntity.created(URI.create("/xalispain/api/v1/referentiel/departement/save")).build();
    }

    @Override
    public ResponseEntity<Void> updateDepartement(Long id, DepartementAddEditDTO departementAddEditDTO) {
        localiteService.updateDepartement(id, departementAddEditDTO);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<DepartementAddEditDTO> getDepartementById(Long id) {
        DepartementAddEditDTO departement = localiteService.findDepartementById(id);
        return ResponseEntity.ok(departement);
    }

    @Override
    public ResponseEntity<List<ListDepartementDTO>> getAllDepartements() {
        List<ListDepartementDTO> departements = localiteService.findListDepartements();
        if (departements.isEmpty()) {
            log.info("Aucun departements trouvé");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(departements);
    }

    @Override
    public ResponseEntity<List<ListDepartementDTO>> getDepartementsByRegion(Long regionId) {
        List<ListDepartementDTO> departements = localiteService.findDepartmentsByRegion(regionId);
        if (departements.isEmpty()) {
            log.info("Aucun departementss trouvé");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(departements);
    }

    @Override
    public ResponseEntity<Page<ListDepartementDTO>> getDepartementsPaged(Pageable pageable) {
        Page<ListDepartementDTO> departements = localiteService.findDepartmentsPaged(pageable);
        if (departements.isEmpty()) {
            log.info("Aucun departements trouvé pour cette page");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(departements);
    }

    @Override
    public ResponseEntity<Page<ListDepartementDTO>> searchDepartements(String filtre, Pageable pageable) {
        Page<ListDepartementDTO> departements = localiteService.findFilteredDepartmentsPaged(pageable, filtre);
        if (departements.isEmpty()) {
            log.info("Aucun departementss trouvé pour cette page");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(departements);
    }

    @Override
    public ResponseEntity<Void> deleteDepartement(Long id) {
        localiteService.deleteDepartment(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> createRegion(RegionAddEditDTO regionAddEditDTO) {
        localiteService.saveRegion(regionAddEditDTO);
        return ResponseEntity.created(URI.create("/xalispain/api/v1/referentiel/region/save")).build();
    }

    @Override
    public ResponseEntity<Void> updateRegion(Long id, RegionAddEditDTO regionAddEditDTO) {
       localiteService.updateRegion(id, regionAddEditDTO);
       return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<RegionAddEditDTO> getRegionById(Long id) {
        RegionAddEditDTO region = localiteService.findRegionById(id);
        return ResponseEntity.ok(region);
    }

    @Override
    public ResponseEntity<List<ListRegionDTO>> getAllRegions() {
        List<ListRegionDTO> regions = localiteService.findListRegions();
        if (regions.isEmpty()) {
            log.info("Aucun regions trouvé");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(regions);
    }

    @Override
    public ResponseEntity<List<ListRegionDTO>> getRegionsByPays(Long paysId) {
        List<ListRegionDTO> regions = localiteService.findRegionByPays(paysId);
        if (regions.isEmpty()) {
            log.info("Aucun regionss trouvé");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(regions);
    }

    @Override
    public ResponseEntity<Page<ListRegionDTO>> getRegionsPaged(Pageable pageable) {
        Page<ListRegionDTO> regions = localiteService.findRegionsPaged(pageable);
        if (regions.isEmpty()) {
            log.info("Aucun regions trouvé pour cette page");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(regions);
    }

    @Override
    public ResponseEntity<Page<ListRegionDTO>> searchRegions(String filtre, Pageable pageable) {
        Page<ListRegionDTO> regions = localiteService.findFilteredRegionsPaged(pageable, filtre);
        if (regions.isEmpty()) {
            log.info("Aucun régions trouvé pour cette page");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(regions);
    }

    @Override
    public ResponseEntity<Void> deleteRegion(Long id) {
       localiteService.deleteRegion(id);
       return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<ListPaysDTO>> getAllPays() {
        List<ListPaysDTO> pays = localiteService.findListPays();
        if (pays.isEmpty()) {
            log.info("Aucun pays trouvé pour cette page");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pays);
    }

    @Override
    public ResponseEntity<List<ListPaysDTO>> getPaysByContinent(Long continentId) {
        List<ListPaysDTO> pays = localiteService.findPaysByContinent(continentId);
        if (pays.isEmpty()) {
            log.info("Aucun payss trouvé pour cette page");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pays);
    }

    @Override
    public ResponseEntity<Page<ListPaysDTO>> getPaysPaged(Pageable pageable) {
        Page<ListPaysDTO> pays = localiteService.findPaysPaged(pageable);
        if (pays.isEmpty()) {
            log.info("Aucun paysss trouvé pour cette page");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pays);
    }

    @Override
    public ResponseEntity<Page<ListPaysDTO>> searchPays(String filtre, Pageable pageable) {
        Page<ListPaysDTO> pays = localiteService.findFilteredPaysPaged(pageable, filtre);
        if (pays.isEmpty()) {
            log.info("Aucun pay trouvé pour cette page");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pays);
    }

    @Override
    public ResponseEntity<Void> deletePays(Long id) {
        localiteService.deletePays(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<ListContinentDTO>> getAllContinents() {
        List<ListContinentDTO> continents = referentielService.findListContinents();
        if (continents.isEmpty()) {
            log.info("Aucun continents trouvé pour l'ID: {}", continents);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(continents);
    }
}