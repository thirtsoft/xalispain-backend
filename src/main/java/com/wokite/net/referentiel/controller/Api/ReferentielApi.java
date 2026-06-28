package com.wokite.net.referentiel.controller.Api;


import com.wokite.net.utils.dto.referentiel.*;
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

@RequestMapping("/referentiel")
@Tag(name = "REFERENTIEL", description = "API de gestion du référentiel")
public interface ReferentielApi {

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

    /*************   TYPEDEPENSE    *************/

    @Operation(summary = "Créer un TypeDepense", description = "Crée un nouveau TypeDepense avec validation des champs obligatoires")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "TypeDepense créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "409", description = "Conflit - Dénomination existante",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PostMapping(value = "/typedepense/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> createTypeDepense(@Valid @RequestBody TypeDepenseAddEditDTO typeDepenseAddEditDTO);

    @Operation(summary = "Mettre à jour un TypeDepense", description = "Met à jour un TypeDepense existant par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "TypeDepense mis à jour avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "TypeDepense non trouvé"),
            @ApiResponse(responseCode = "409", description = "Conflit - Dénomination existante"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PutMapping(value = "/typedepense/{id}/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> updateTypeDepense(
            @Parameter(description = "ID du TypeDepense à mettre à jour", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody TypeDepenseAddEditDTO typeDepenseAddEditDTO);

    @Operation(summary = "Récupérer un TypeDepense", description = "Récupère un TypeDepense par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "TypeDepense trouvé",
                    content = @Content(schema = @Schema(implementation = TypeDepenseAddEditDTO.class))),
            @ApiResponse(responseCode = "404", description = "TypeDepense non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping(value = "/typedepense/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<TypeDepenseAddEditDTO> getTypeDepenseById(
            @Parameter(description = "ID du TypeDepense", required = true, example = "1")
            @PathVariable Long id);

    @Operation(summary = "Lister tous les TypeDepenses", description = "Récupère la liste de tous les TypeDepenses actifs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des TypeDepenses récupérée avec succès",
                    content = @Content(schema = @Schema(implementation = ListTypeDepenseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping(value = "/typedepenses", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ListTypeDepenseDTO>> getAllTypeDepenses();

    @Operation(summary = "Lister les TypeDepenses paginés", description = "Récupère les TypeDepenses avec pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Page des TypeDepenses récupérée"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping(value = "/typedepenses/paged", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Page<ListTypeDepenseDTO>> getTypeDepensesPaged(
            @Parameter(description = "Paramètres de pagination et tri")
            @PageableDefault(size = 20, sort = "denomination", direction = Sort.Direction.ASC)
            Pageable pageable);

    @Operation(summary = "Rechercher des TypeDepenses", description = "Recherche des TypeDepenses avec filtres et pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Résultats de la recherche"),
            @ApiResponse(responseCode = "400", description = "Format de filtre invalide"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PostMapping(value = "/typedepenses/search", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Page<ListTypeDepenseDTO>> searchTypeDepenses(
            @Parameter(description = "Filtres de recherche au format JSON")
            @RequestParam String filtre,
            @PageableDefault(size = 20, sort = "denomination", direction = Sort.Direction.ASC)
            Pageable pageable);

    @Operation(summary = "Désactiver un TypeDepense", description = "Désactive un TypeDepense (suppression logique)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "TypeDepense désactivé avec succès"),
            @ApiResponse(responseCode = "400", description = "TypeDepense déjà inactif"),
            @ApiResponse(responseCode = "404", description = "TypeDepense non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @DeleteMapping("/typedepense/{id}/delete")
    ResponseEntity<Void> deleteTypeDepense(
            @Parameter(description = "ID du TypeDepense à désactiver", required = true, example = "1")
            @PathVariable Long id);

    /*****************    MODEPAIEMENT    ***********/

    @Operation(summary = "Créer un ModePaiement", description = "Crée un nouveau ModePaiement avec validation des champs obligatoires")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "ModePaiement créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "409", description = "Conflit - Dénomination existante",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PostMapping(value = "/modepaiement/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> createModePaiement(@Valid @RequestBody ModePaiementAddEditDTO modePaiementAddEditDTO);

    @Operation(summary = "Mettre à jour un ModePaiement", description = "Met à jour un ModePaiement existant par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ModePaiement mis à jour avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "ModePaiement non trouvé"),
            @ApiResponse(responseCode = "409", description = "Conflit - Dénomination existante"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PutMapping(value = "/modepaiement/{id}/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> updateModePaiement(
            @Parameter(description = "ID du ModePaiement à mettre à jour", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody ModePaiementAddEditDTO modePaiementAddEditDTO);

    @Operation(summary = "Récupérer un ModePaiement", description = "Récupère un ModePaiement par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ModePaiement trouvé",
                    content = @Content(schema = @Schema(implementation = ModePaiementAddEditDTO.class))),
            @ApiResponse(responseCode = "404", description = "ModePaiement non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping(value = "/modepaiement/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ModePaiementAddEditDTO> getModePaiementById(
            @Parameter(description = "ID du ModePaiement", required = true, example = "1")
            @PathVariable Long id);

    @Operation(summary = "Lister tous les ModePaiements", description = "Récupère la liste de tous les ModePaiements actifs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des ModePaiements récupérée avec succès",
                    content = @Content(schema = @Schema(implementation = ListModePaiementDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping(value = "/modepaiements", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ListModePaiementDTO>> getAllModePaiements();

    @Operation(summary = "Lister les ModePaiements paginés", description = "Récupère les ModePaiements avec pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Page des ModePaiements récupérée"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping(value = "/modepaiements/paged", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Page<ListModePaiementDTO>> getModePaiementsPaged(
            @Parameter(description = "Paramètres de pagination et tri")
            @PageableDefault(size = 20, sort = "denomination", direction = Sort.Direction.ASC)
            Pageable pageable);

    @Operation(summary = "Rechercher des ModePaiements", description = "Recherche des ModePaiements avec filtres et pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Résultats de la recherche"),
            @ApiResponse(responseCode = "400", description = "Format de filtre invalide"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PostMapping(value = "/modepaiements/search", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Page<ListModePaiementDTO>> searchModePaiements(
            @Parameter(description = "Filtres de recherche au format JSON")
            @RequestParam String filtre,
            @PageableDefault(size = 20, sort = "denomination", direction = Sort.Direction.ASC)
            Pageable pageable);

    @Operation(summary = "Désactiver un ModePaiement", description = "Désactive un ModePaiement (suppression logique)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ModePaiement désactivé avec succès"),
            @ApiResponse(responseCode = "400", description = "ModePaiement déjà inactif"),
            @ApiResponse(responseCode = "404", description = "ModePaiement non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @DeleteMapping("/modepaiement/{id}/delete")
    ResponseEntity<Void> deleteModePaiement(
            @Parameter(description = "ID du ModePaiement à désactiver", required = true, example = "1")
            @PathVariable Long id);

    /********************     COMMUNE    ******************/

    @Operation(summary = "Créer une Commune", description = "Crée une nouvelle Commune avec validation des champs obligatoires")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Commune créée avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "409", description = "Conflit - Dénomination existante",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PostMapping(value = "/commune/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> createCommune(@Valid @RequestBody CommuneAddEditDTO communeAddEditDTO);

    @Operation(summary = "Mettre à jour une Commune", description = "Met à jour une Commune existante par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Commune mise à jour avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Commune non trouvée"),
            @ApiResponse(responseCode = "409", description = "Conflit - Dénomination existante"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PutMapping(value = "/commune/{id}/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> updateCommune(
            @Parameter(description = "ID de la Commune à mettre à jour", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody CommuneAddEditDTO communeAddEditDTO);

    @Operation(summary = "Récupérer une Commune", description = "Récupère une Commune par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Commune trouvée",
                    content = @Content(schema = @Schema(implementation = CommuneAddEditDTO.class))),
            @ApiResponse(responseCode = "404", description = "Commune non trouvée"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping(value = "/commune/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CommuneAddEditDTO> getCommuneById(
            @Parameter(description = "ID de la Commune", required = true, example = "1")
            @PathVariable Long id);

    @Operation(summary = "Lister toutes les Communes", description = "Récupère la liste de toutes les Communes actives")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des Communes récupérée avec succès",
                    content = @Content(schema = @Schema(implementation = ListCommuneDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping(value = "/communes", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ListCommuneDTO>> getAllCommunes();

    @Operation(summary = "Lister les Communes par département", description = "Récupère les Communes d'un département spécifique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des Communes récupérée"),
            @ApiResponse(responseCode = "404", description = "Aucune Commune trouvée pour ce département"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping(value = "/communes/departement/{departmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ListCommuneDTO>> getCommunesByDepartment(
            @Parameter(description = "ID du département", required = true, example = "1")
            @PathVariable Long departmentId);

    @Operation(summary = "Lister les Communes paginées", description = "Récupère les Communes avec pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Page des Communes récupérée"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping(value = "/communes/paged", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Page<ListCommuneDTO>> getCommunesPaged(
            @Parameter(description = "Paramètres de pagination et tri")
            @PageableDefault(size = 20, sort = "denomination", direction = Sort.Direction.ASC)
            Pageable pageable);

    @Operation(summary = "Rechercher des Communes", description = "Recherche des Communes avec filtres et pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Résultats de la recherche"),
            @ApiResponse(responseCode = "400", description = "Format de filtre invalide"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PostMapping(value = "/communes/search", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Page<ListCommuneDTO>> searchCommunes(
            @Parameter(description = "Filtres de recherche au format JSON")
            @RequestParam String filtre,
            @PageableDefault(size = 20, sort = "denomination", direction = Sort.Direction.ASC)
            Pageable pageable);

    @Operation(summary = "Désactiver une Commune", description = "Désactive une Commune (suppression logique)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Commune désactivée avec succès"),
            @ApiResponse(responseCode = "400", description = "Commune déjà inactive"),
            @ApiResponse(responseCode = "404", description = "Commune non trouvée"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @DeleteMapping("/commune/{id}/delete")
    ResponseEntity<Void> deleteCommune(
            @Parameter(description = "ID de la Commune à désactiver", required = true, example = "1")
            @PathVariable Long id);

    /*****************   DEPARTEMENT       ********/

    @Operation(summary = "Créer un Departement", description = "Crée un nouveau Departement avec validation des champs obligatoires")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Departement créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "409", description = "Conflit - Dénomination existante",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PostMapping(value = "/departement/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> createDepartement(@Valid @RequestBody DepartementAddEditDTO departementAddEditDTO);

    @Operation(summary = "Mettre à jour un Departement", description = "Met à jour un Departement existant par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Departement mis à jour avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Departement non trouvé"),
            @ApiResponse(responseCode = "409", description = "Conflit - Dénomination existante"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PutMapping(value = "/departement/{id}/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> updateDepartement(
            @Parameter(description = "ID du Departement à mettre à jour", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody DepartementAddEditDTO departementAddEditDTO);

    @Operation(summary = "Récupérer un Departement", description = "Récupère un Departement par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Departement trouvé",
                    content = @Content(schema = @Schema(implementation = DepartementAddEditDTO.class))),
            @ApiResponse(responseCode = "404", description = "Departement non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping(value = "/departement/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<DepartementAddEditDTO> getDepartementById(
            @Parameter(description = "ID du Departement", required = true, example = "1")
            @PathVariable Long id);

    @Operation(summary = "Lister tous les Departements", description = "Récupère la liste de tous les Departements actifs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des Departements récupérée avec succès",
                    content = @Content(schema = @Schema(implementation = ListDepartementDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping(value = "/departements", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ListDepartementDTO>> getAllDepartements();

    @Operation(summary = "Lister les Departements par région", description = "Récupère les Departements d'une région spécifique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des Departements récupérée"),
            @ApiResponse(responseCode = "404", description = "Aucun Departement trouvé pour cette région"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping(value = "/departements/region/{regionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ListDepartementDTO>> getDepartementsByRegion(
            @Parameter(description = "ID de la région", required = true, example = "1")
            @PathVariable Long regionId);

    @Operation(summary = "Lister les Departements paginés", description = "Récupère les Departements avec pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Page des Departements récupérée"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping(value = "/departements/paged", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Page<ListDepartementDTO>> getDepartementsPaged(
            @Parameter(description = "Paramètres de pagination et tri")
            @PageableDefault(size = 20, sort = "denomination", direction = Sort.Direction.ASC)
            Pageable pageable);

    @Operation(summary = "Rechercher des Departements", description = "Recherche des Departements avec filtres et pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Résultats de la recherche"),
            @ApiResponse(responseCode = "400", description = "Format de filtre invalide"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PostMapping(value = "/departements/search", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Page<ListDepartementDTO>> searchDepartements(
            @Parameter(description = "Filtres de recherche au format JSON")
            @RequestParam String filtre,
            @PageableDefault(size = 20, sort = "denomination", direction = Sort.Direction.ASC)
            Pageable pageable);

    @Operation(summary = "Désactiver un Departement", description = "Désactive un Departement (suppression logique)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Departement désactivé avec succès"),
            @ApiResponse(responseCode = "400", description = "Departement déjà inactif"),
            @ApiResponse(responseCode = "404", description = "Departement non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @DeleteMapping("/departement/{id}/delete")
    ResponseEntity<Void> deleteDepartement(
            @Parameter(description = "ID du Departement à désactiver", required = true, example = "1")
            @PathVariable Long id);

    /******************      REGION           *******************/

    @Operation(summary = "Créer une Region", description = "Crée une nouvelle Region avec validation des champs obligatoires")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Region créée avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "409", description = "Conflit - Dénomination existante",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PostMapping(value = "/region/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> createRegion(@Valid @RequestBody RegionAddEditDTO regionAddEditDTO);

    @Operation(summary = "Mettre à jour une Region", description = "Met à jour une Region existante par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Region mise à jour avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "404", description = "Region non trouvée"),
            @ApiResponse(responseCode = "409", description = "Conflit - Dénomination existante"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PutMapping(value = "/region/{id}/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> updateRegion(
            @Parameter(description = "ID de la Region à mettre à jour", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody RegionAddEditDTO regionAddEditDTO);

    @Operation(summary = "Récupérer une Region", description = "Récupère une Region par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Region trouvée",
                    content = @Content(schema = @Schema(implementation = RegionAddEditDTO.class))),
            @ApiResponse(responseCode = "404", description = "Region non trouvée"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping(value = "/region/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<RegionAddEditDTO> getRegionById(
            @Parameter(description = "ID de la Region", required = true, example = "1")
            @PathVariable Long id);

    @Operation(summary = "Lister toutes les Regions", description = "Récupère la liste de toutes les Regions actives")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des Regions récupérée avec succès",
                    content = @Content(schema = @Schema(implementation = ListRegionDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping(value = "/regions", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ListRegionDTO>> getAllRegions();

    @Operation(summary = "Lister les Regions par pays", description = "Récupère les Regions d'un pays spécifique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des Regions récupérée"),
            @ApiResponse(responseCode = "404", description = "Aucune Region trouvée pour ce pays"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping(value = "/regions/pays/{paysId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ListRegionDTO>> getRegionsByPays(
            @Parameter(description = "ID du pays", required = true, example = "1")
            @PathVariable Long paysId);

    @Operation(summary = "Lister les Regions paginées", description = "Récupère les Regions avec pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Page des Regions récupérée"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping(value = "/regions/paged", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Page<ListRegionDTO>> getRegionsPaged(
            @Parameter(description = "Paramètres de pagination et tri")
            @PageableDefault(size = 20, sort = "denomination", direction = Sort.Direction.ASC)
            Pageable pageable);

    @Operation(summary = "Rechercher des Regions", description = "Recherche des Regions avec filtres et pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Résultats de la recherche"),
            @ApiResponse(responseCode = "400", description = "Format de filtre invalide"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PostMapping(value = "/regions/search", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Page<ListRegionDTO>> searchRegions(
            @Parameter(description = "Filtres de recherche au format JSON")
            @RequestParam String filtre,
            @PageableDefault(size = 20, sort = "denomination", direction = Sort.Direction.ASC)
            Pageable pageable);

    @Operation(summary = "Désactiver une Region", description = "Désactive une Region (suppression logique)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Region désactivée avec succès"),
            @ApiResponse(responseCode = "400", description = "Region déjà inactive"),
            @ApiResponse(responseCode = "404", description = "Region non trouvée"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @DeleteMapping("/region/{id}/delete")
    ResponseEntity<Void> deleteRegion(
            @Parameter(description = "ID de la Region à désactiver", required = true, example = "1")
            @PathVariable Long id);

    /************************   PAYS     ******************/

    @Operation(summary = "Lister tous les Pays", description = "Récupère la liste de tous les Pays actifs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des Pays récupérée avec succès",
                    content = @Content(schema = @Schema(implementation = ListPaysDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping(value = "/pays", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ListPaysDTO>> getAllPays();

    @Operation(summary = "Lister les Pays par continent", description = "Récupère les Pays d'un continent spécifique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des Pays récupérée"),
            @ApiResponse(responseCode = "404", description = "Aucun Pays trouvé pour ce continent"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping(value = "/pays/continent/{continentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ListPaysDTO>> getPaysByContinent(
            @Parameter(description = "ID du continent", required = true, example = "1")
            @PathVariable Long continentId);

    @Operation(summary = "Lister les Pays paginés", description = "Récupère les Pays avec pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Page des Pays récupérée"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping(value = "/pays/paged", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Page<ListPaysDTO>> getPaysPaged(
            @Parameter(description = "Paramètres de pagination et tri")
            @PageableDefault(size = 20, sort = "denomination", direction = Sort.Direction.ASC)
            Pageable pageable);

    @Operation(summary = "Rechercher des Pays", description = "Recherche des Pays avec filtres et pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Résultats de la recherche"),
            @ApiResponse(responseCode = "400", description = "Format de filtre invalide"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @PostMapping(value = "/pays/search", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Page<ListPaysDTO>> searchPays(
            @Parameter(description = "Filtres de recherche au format JSON")
            @RequestParam String filtre,
            @PageableDefault(size = 20, sort = "denomination", direction = Sort.Direction.ASC)
            Pageable pageable);

    @Operation(summary = "Désactiver un Pays", description = "Désactive un Pays (suppression logique)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pays désactivé avec succès"),
            @ApiResponse(responseCode = "400", description = "Pays déjà inactif"),
            @ApiResponse(responseCode = "404", description = "Pays non trouvé"),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @DeleteMapping("/pays/{id}/delete")
    ResponseEntity<Void> deletePays(
            @Parameter(description = "ID du Pays à désactiver", required = true, example = "1")
            @PathVariable Long id);

    /***************     CONTINENT       *********/

    @Operation(summary = "Lister tous les Continents", description = "Récupère la liste de tous les Continents actifs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste des Continents récupérée avec succès",
                    content = @Content(schema = @Schema(implementation = ListContinentDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur")
    })
    @GetMapping(value = "/continents", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ListContinentDTO>> getAllContinents();

}