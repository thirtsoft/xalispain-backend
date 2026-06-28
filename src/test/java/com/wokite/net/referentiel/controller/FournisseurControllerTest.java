package com.wokite.net.referentiel.controller;

import com.wokite.net.exception.DuplicateXalisPainException;
import com.wokite.net.exception.InvalidXalisPainDataException;
import com.wokite.net.exception.ValidationXalisPainException;
import com.wokite.net.exception.XalisPainNotFoundException;
import com.wokite.net.referentiel.service.ReferentielService;
import com.wokite.net.utils.dto.referentiel.FournisseurAddEditDTO;
import com.wokite.net.utils.dto.referentiel.ListFournisseurDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReferentielController.class)
@DisplayName("Tests du contrôleur Fournisseur")
class FournisseurControllerTest {

    private static final String BASE_URL = "/referentiel";
    private static final String SAVE_URL = BASE_URL + "/fournisseur/save";
    private static final String UPDATE_URL = BASE_URL + "/fournisseur/{id}/update";
    private static final String GET_BY_ID_URL = BASE_URL + "/fournisseur/{id}";
    private static final String GET_ALL_URL = BASE_URL + "/fournisseurs";
    private static final String GET_BY_COMMUNE_URL = BASE_URL + "/fournisseurs/commune/{communeId}";
    private static final String GET_PAGED_URL = BASE_URL + "/fournisseurs/paged";
    private static final String SEARCH_URL = BASE_URL + "/fournisseurs/search";
    private static final String DELETE_URL = BASE_URL + "/fournisseur/{id}/delete";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ReferentielService referentielService;

    private FournisseurAddEditDTO validDTO;
    private ListFournisseurDTO listDTO1;
    private ListFournisseurDTO listDTO2;

    @BeforeEach
    void setUp() {
        validDTO = FournisseurAddEditDTO.builder()
                .denomination("ABC Corporation")
                .sigle("ABC")
                .mobile("771234567")
                .telephone("338201234")
                .email("contact@abc.com")
                .adresse("123 Rue du Commerce")
                .communeId(1L)
                .tenantId("tenant-123")
                .actif(1)
                .build();

        listDTO1 = ListFournisseurDTO.builder()
                .id(1L)
                .denomination("ABC Corporation")
                .sigle("ABC")
                .mobile("771234567")
                .telephone("338201234")
                .email("contact@abc.com")
                .adresse("123 Rue du Commerce")
                .commune("Dakar")
                .tenant("tenant-123")
                .actif(1)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        listDTO2 = ListFournisseurDTO.builder()
                .id(2L)
                .denomination("XYZ Enterprise")
                .sigle("XYZ")
                .mobile("771234568")
                .telephone("338201235")
                .email("contact@xyz.com")
                .adresse("456 Avenue des Affaires")
                .commune("Thiès")
                .tenant("tenant-456")
                .actif(1)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Nested
    @DisplayName("Tests POST - Création")
    class CreateFournisseurTests {

        @Test
        @DisplayName("Devrait créer un fournisseur et retourner 201 Created")
        void shouldCreateFournisseurAndReturn201() throws Exception {
            doNothing().when(referentielService).saveFournisseur(any(FournisseurAddEditDTO.class));

            mockMvc.perform(post(SAVE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(validDTO)))
                    .andExpect(status().isCreated());

            verify(referentielService).saveFournisseur(any(FournisseurAddEditDTO.class));
        }

        @Test
        @DisplayName("Devrait retourner 409 Conflict si dénomination en double")
        void shouldReturn409WhenDuplicateDenomination() throws Exception {
            doThrow(new DuplicateXalisPainException("Fournisseur", "ABC Corporation"))
                    .when(referentielService).saveFournisseur(any(FournisseurAddEditDTO.class));

            mockMvc.perform(post(SAVE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(validDTO)))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.status").value(409))
                    .andExpect(jsonPath("$.error").value("Conflit de données"))
                    .andExpect(jsonPath("$.message", containsString("ABC Corporation")))
                    .andExpect(jsonPath("$.message", containsString("existe déjà")));
        }

        @Test
        @DisplayName("Devrait retourner 400 Bad Request si validation échoue")
        void shouldReturn400WhenValidationFails() throws Exception {
            Map<String, String> errors = Map.of(
                    "denomination", "La dénomination est obligatoire",
                    "mobile", "Le mobile est obligatoire"
            );

            doThrow(new ValidationXalisPainException("Fournisseur", errors))
                    .when(referentielService).saveFournisseur(any(FournisseurAddEditDTO.class));

            mockMvc.perform(post(SAVE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(validDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.error").value("Erreur de validation"))
                    .andExpect(jsonPath("$.details.denomination").value("La dénomination est obligatoire"))
                    .andExpect(jsonPath("$.details.mobile").value("Le mobile est obligatoire"));
        }

        @Test
        @DisplayName("Devrait retourner 400 Bad Request si le corps est vide")
        void shouldReturn400WhenBodyIsEmpty() throws Exception {
            mockMvc.perform(post(SAVE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(""))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Devrait retourner 400 Bad Request si le corps est invalide")
        void shouldReturn400WhenBodyIsInvalidJson() throws Exception {
            mockMvc.perform(post(SAVE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{invalid json}"))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("Tests PUT - Mise à jour")
    class UpdateFournisseurTests {

        @Test
        @DisplayName("Devrait mettre à jour un fournisseur et retourner 200 OK")
        void shouldUpdateFournisseurAndReturn200() throws Exception {
            doNothing().when(referentielService)
                    .updateFournisseur(eq(1L), any(FournisseurAddEditDTO.class));

            mockMvc.perform(put(UPDATE_URL, 1L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(validDTO)))
                    .andExpect(status().isOk());

            verify(referentielService).updateFournisseur(eq(1L), any(FournisseurAddEditDTO.class));
        }

        @Test
        @DisplayName("Devrait retourner 404 Not Found si le fournisseur n'existe pas")
        void shouldReturn404WhenFournisseurNotFound() throws Exception {
            doThrow(new XalisPainNotFoundException("Fournisseur", 999L))
                    .when(referentielService)
                    .updateFournisseur(eq(999L), any(FournisseurAddEditDTO.class));

            mockMvc.perform(put(UPDATE_URL, 999L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(validDTO)))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.status").value(404))
                    .andExpect(jsonPath("$.message", containsString("999")));
        }

        @Test
        @DisplayName("Devrait retourner 409 Conflict si la nouvelle dénomination existe déjà")
        void shouldReturn409WhenNewDenominationExists() throws Exception {
            doThrow(new DuplicateXalisPainException("Fournisseur", "dénomination", "XYZ Corp"))
                    .when(referentielService)
                    .updateFournisseur(eq(1L), any(FournisseurAddEditDTO.class));

            mockMvc.perform(put(UPDATE_URL, 1L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(validDTO)))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.message", containsString("existe déjà")));
        }
    }

    @Nested
    @DisplayName("Tests GET - Recherche par ID")
    class GetFournisseurByIdTests {

        @Test
        @DisplayName("Devrait retourner un fournisseur et 200 OK")
        void shouldReturnFournisseurAnd200() throws Exception {
            when(referentielService.findFournisseur(1L)).thenReturn(validDTO);

            mockMvc.perform(get(GET_BY_ID_URL, 1L)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.denomination").value("ABC Corporation"))
                    .andExpect(jsonPath("$.sigle").value("ABC"))
                    .andExpect(jsonPath("$.mobile").value("771234567"))
                    .andExpect(jsonPath("$.email").value("contact@abc.com"))
                    .andExpect(jsonPath("$.communeId").value(1));
        }

        @Test
        @DisplayName("Devrait retourner 404 Not Found si le fournisseur n'existe pas")
        void shouldReturn404WhenFournisseurNotFound() throws Exception {
            when(referentielService.findFournisseur(999L))
                    .thenThrow(new XalisPainNotFoundException("Fournisseur", 999L));

            mockMvc.perform(get(GET_BY_ID_URL, 999L)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.status").value(404))
                    .andExpect(jsonPath("$.message", containsString("999")));
        }
    }

    @Nested
    @DisplayName("Tests GET - Liste complète")
    class GetAllFournisseursTests {

        @Test
        @DisplayName("Devrait retourner la liste des fournisseurs et 200 OK")
        void shouldReturnAllFournisseursAnd200() throws Exception {
            when(referentielService.findListFournisseurs())
                    .thenReturn(List.of(listDTO1, listDTO2));

            mockMvc.perform(get(GET_ALL_URL)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[0].denomination").value("ABC Corporation"))
                    .andExpect(jsonPath("$[1].denomination").value("XYZ Enterprise"))
                    .andExpect(jsonPath("$[0].commune").value("Dakar"))
                    .andExpect(jsonPath("$[1].commune").value("Thiès"));
        }

        @Test
        @DisplayName("Devrait retourner 204 No Content si la liste est vide")
        void shouldReturn204WhenListIsEmpty() throws Exception {
            when(referentielService.findListFournisseurs())
                    .thenReturn(Collections.emptyList());

            mockMvc.perform(get(GET_ALL_URL)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());
        }
    }

    @Nested
    @DisplayName("Tests GET - Par commune")
    class GetFournisseursByCommuneTests {

        @Test
        @DisplayName("Devrait retourner les fournisseurs d'une commune et 200 OK")
        void shouldReturnFournisseursByCommuneAnd200() throws Exception {
            when(referentielService.findListFournisseursByCommune(1L))
                    .thenReturn(List.of(listDTO1));

            mockMvc.perform(get(GET_BY_COMMUNE_URL, 1L)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(1)))
                    .andExpect(jsonPath("$[0].denomination").value("ABC Corporation"))
                    .andExpect(jsonPath("$[0].commune").value("Dakar"));
        }

        @Test
        @DisplayName("Devrait retourner 204 No Content si aucun fournisseur dans la commune")
        void shouldReturn204WhenNoFournisseurInCommune() throws Exception {
            when(referentielService.findListFournisseursByCommune(99L))
                    .thenReturn(Collections.emptyList());

            mockMvc.perform(get(GET_BY_COMMUNE_URL, 99L)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());
        }
    }

    @Nested
    @DisplayName("Tests GET - Pagination")
    class GetFournisseursPagedTests {

        @Test
        @DisplayName("Devrait retourner les fournisseurs paginés et 200 OK")
        void shouldReturnPagedFournisseursAnd200() throws Exception {
            Page<ListFournisseurDTO> page = new PageImpl<>(
                    List.of(listDTO1, listDTO2),
                    PageRequest.of(0, 20),
                    2
            );

            when(referentielService.findFournisseursPaged(any(Pageable.class)))
                    .thenReturn(page);

            mockMvc.perform(get(GET_PAGED_URL)
                            .param("page", "0")
                            .param("size", "20")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content", hasSize(2)))
                    .andExpect(jsonPath("$.totalElements").value(2))
                    .andExpect(jsonPath("$.totalPages").value(1))
                    .andExpect(jsonPath("$.content[0].denomination").value("ABC Corporation"))
                    .andExpect(jsonPath("$.content[1].denomination").value("XYZ Enterprise"));
        }

        @Test
        @DisplayName("Devrait supporter le tri par dénomination")
        void shouldSupportSorting() throws Exception {
            Page<ListFournisseurDTO> page = new PageImpl<>(
                    List.of(listDTO1),
                    PageRequest.of(0, 20, Sort.by("denomination").ascending()),
                    1
            );

            when(referentielService.findFournisseursPaged(any(Pageable.class)))
                    .thenReturn(page);

            mockMvc.perform(get(GET_PAGED_URL)
                            .param("page", "0")
                            .param("size", "20")
                            .param("sort", "denomination,asc")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Devrait retourner 204 No Content si page vide")
        void shouldReturn204WhenPageIsEmpty() throws Exception {
            Page<ListFournisseurDTO> emptyPage = new PageImpl<>(
                    Collections.emptyList(),
                    PageRequest.of(10, 20),
                    0
            );

            when(referentielService.findFournisseursPaged(any(Pageable.class)))
                    .thenReturn(emptyPage);

            mockMvc.perform(get(GET_PAGED_URL)
                            .param("page", "10")
                            .param("size", "20")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());
        }
    }

    @Nested
    @DisplayName("Tests POST - Recherche filtrée")
    class SearchFournisseursTests {

        @Test
        @DisplayName("Devrait retourner les résultats filtrés et 200 OK")
        void shouldReturnFilteredResultsAnd200() throws Exception {
            Page<ListFournisseurDTO> page = new PageImpl<>(
                    List.of(listDTO1),
                    PageRequest.of(0, 20),
                    1
            );

            when(referentielService.findFilteredFournisseursPaged(any(Pageable.class), anyString()))
                    .thenReturn(page);

            mockMvc.perform(post(SEARCH_URL)
                            .param("filtre", "{\"libelle\":\"ABC\"}")
                            .param("page", "0")
                            .param("size", "20")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content", hasSize(1)))
                    .andExpect(jsonPath("$.content[0].denomination").value("ABC Corporation"));
        }

        @Test
        @DisplayName("Devrait retourner 400 Bad Request si le filtre est invalide")
        void shouldReturn400WhenFilterIsInvalid() throws Exception {
            when(referentielService.findFilteredFournisseursPaged(any(Pageable.class), anyString()))
                    .thenThrow(new InvalidXalisPainDataException("Format de filtre invalide"));

            mockMvc.perform(post(SEARCH_URL)
                            .param("filtre", "{invalid}")
                            .param("page", "0")
                            .param("size", "20")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Devrait retourner 204 No Content si aucun résultat")
        void shouldReturn204WhenNoResults() throws Exception {
            Page<ListFournisseurDTO> emptyPage = new PageImpl<>(
                    Collections.emptyList(),
                    PageRequest.of(0, 20),
                    0
            );

            when(referentielService.findFilteredFournisseursPaged(any(Pageable.class), anyString()))
                    .thenReturn(emptyPage);

            mockMvc.perform(post(SEARCH_URL)
                            .param("filtre", "{\"libelle\":\"ZORGLUB\"}")
                            .param("page", "0")
                            .param("size", "20")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());
        }
    }

    @Nested
    @DisplayName("Tests DELETE - Suppression")
    class DeleteFournisseurTests {

        @Test
        @DisplayName("Devrait désactiver un fournisseur et retourner 200 OK")
        void shouldDeactivateFournisseurAndReturn200() throws Exception {
            doNothing().when(referentielService).deleteFournisseur(1L);

            mockMvc.perform(delete(DELETE_URL, 1L))
                    .andExpect(status().isOk());

            verify(referentielService).deleteFournisseur(1L);
        }

        @Test
        @DisplayName("Devrait retourner 404 Not Found si le fournisseur n'existe pas")
        void shouldReturn404WhenFournisseurNotFound() throws Exception {
            doThrow(new XalisPainNotFoundException("Fournisseur", 999L))
                    .when(referentielService).deleteFournisseur(999L);

            mockMvc.perform(delete(DELETE_URL, 999L))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.message", containsString("999")));
        }

        @Test
        @DisplayName("Devrait retourner 400 Bad Request si déjà inactif")
        void shouldReturn400WhenAlreadyInactive() throws Exception {
            doThrow(new InvalidXalisPainDataException("Fournisseur", "Le fournisseur est déjà inactif"))
                    .when(referentielService).deleteFournisseur(1L);

            mockMvc.perform(delete(DELETE_URL, 1L))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message", containsString("déjà inactif")));
        }
    }

    @Nested
    @DisplayName("Tests de gestion des erreurs globales")
    class GlobalErrorHandlingTests {

        @Test
        @DisplayName("Devrait retourner 500 Internal Server Error pour les erreurs inattendues")
        void shouldReturn500ForUnexpectedErrors() throws Exception {
            doThrow(new RuntimeException("Erreur inattendue"))
                    .when(referentielService).saveFournisseur(any(FournisseurAddEditDTO.class));

            mockMvc.perform(post(SAVE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(validDTO)))
                    .andExpect(status().isInternalServerError())
                    .andExpect(jsonPath("$.status").value(500))
                    .andExpect(jsonPath("$.error").value("Erreur interne du serveur"));
        }
    }
}