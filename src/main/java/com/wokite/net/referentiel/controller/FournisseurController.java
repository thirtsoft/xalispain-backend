package com.wokite.net.referentiel.controller;

import com.wokite.net.referentiel.controller.Api.FournisseurApi;
import com.wokite.net.referentiel.service.FournisseurService;
import com.wokite.net.utils.dto.referentiel.FournisseurAddEditDTO;
import com.wokite.net.utils.dto.referentiel.ListFournisseurDTO;
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
public class FournisseurController implements FournisseurApi {

    private final FournisseurService fournisseurService;

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
}