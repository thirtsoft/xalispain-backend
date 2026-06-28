package com.wokite.net.referentiel.controller.Api;


import com.wokite.net.utils.dto.referentiel.FournisseurAddEditDTO;
import com.wokite.net.utils.dto.referentiel.ListFournisseurDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping()
@Tag(name = "REFERENTIEL", description = "API de gestion du référentiel")
public interface FournisseurApi {

    @Operation(summary = "Créer un fournisseur", description = "Crée un nouveau fournisseur avec validation des champs obligatoires")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Fournisseur créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "409", description = "Conflit - Dénomination existante",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PostMapping(value = "/fournisseur/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> createFournisseur(@Valid @RequestBody FournisseurAddEditDTO fournisseurAddEditDTO);

    @Operation(summary = "Mettre à jour un fournisseur", description = "Met à jour un fournisseur existant par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fournisseur mis à jour avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Fournisseur non trouvé"),
            @ApiResponse(responseCode = "409", description = "Conflit - Dénomination existante"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PutMapping(value = "/fournisseur/{id}/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> updateFournisseur(
            @Parameter(description = "ID du fournisseur à mettre à jour", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody FournisseurAddEditDTO fournisseurAddEditDTO);

    @Operation(summary = "Récupérer un fournisseur", description = "Récupère un fournisseur par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fournisseur trouvé",
                    content = @Content(schema = @Schema(implementation = FournisseurAddEditDTO.class))),
            @ApiResponse(responseCode = "404", description = "Fournisseur non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping(value = "/fournisseur/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<FournisseurAddEditDTO> getFournisseurById(
            @Parameter(description = "ID du fournisseur", required = true, example = "1")
            @PathVariable Long id);


    @Operation(summary = "Lister tous les fournisseurs", description = "Récupère la liste de tous les fournisseurs actifs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des fournisseurs récupérée avec succès",
                    content = @Content(schema = @Schema(implementation = ListFournisseurDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping(value = "/fournisseurs", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ListFournisseurDTO>> getAllFournisseurs();

    @Operation(summary = "Lister les fournisseurs par commune", description = "Récupère les fournisseurs d'une commune spécifique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des fournisseurs récupérée"),
            @ApiResponse(responseCode = "404", description = "Aucun fournisseur trouvé pour cette commune"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping(value = "/fournisseurs/commune/{communeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ListFournisseurDTO>> getFournisseursByCommune(
            @Parameter(description = "ID de la commune", required = true, example = "1")
            @PathVariable Long communeId);

    @Operation(summary = "Lister les fournisseurs paginés", description = "Récupère les fournisseurs avec pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Page des fournisseurs récupérée"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping(value = "/fournisseurs/paged", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Page<ListFournisseurDTO>> getFournisseursPaged(
            @Parameter(description = "Paramètres de pagination et tri")
            @PageableDefault(size = 20, sort = "denomination", direction = Sort.Direction.ASC)
            Pageable pageable);

    @Operation(summary = "Rechercher des fournisseurs", description = "Recherche des fournisseurs avec filtres et pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Résultats de la recherche"),
            @ApiResponse(responseCode = "400", description = "Format de filtre invalide"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PostMapping(value = "/fournisseurs/search", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Page<ListFournisseurDTO>> searchFournisseurs(
            @Parameter(description = "Filtres de recherche au format JSON")
            @RequestParam String filtre,
            @PageableDefault(size = 20, sort = "denomination", direction = Sort.Direction.ASC)
            Pageable pageable);


    @Operation(summary = "Désactiver un fournisseur", description = "Désactive un fournisseur (suppression logique)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fournisseur désactivé avec succès"),
            @ApiResponse(responseCode = "400", description = "Fournisseur déjà inactif"),
            @ApiResponse(responseCode = "404", description = "Fournisseur non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @DeleteMapping("/fournisseur/{id}/delete")
    ResponseEntity<Void> deleteFournisseur(
            @Parameter(description = "ID du fournisseur à désactiver", required = true, example = "1")
            @PathVariable Long id);

}