package com.wokite.net.referentiel.integration;

import com.wokite.net.referentiel.entity.Fournisseur;
import com.wokite.net.referentiel.repository.FournisseurRepository;
import com.wokite.net.utils.dto.referentiel.FournisseurAddEditDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@DisplayName("Tests d'intégration - Module Fournisseur")
class FournisseurIntegrationTest {

    private static final String BASE_URL = "/referentiel";
    private static final String SAVE_URL = BASE_URL + "/fournisseur/save";
    private static final String UPDATE_URL = BASE_URL + "/fournisseur/{id}/update";
    private static final String GET_BY_ID_URL = BASE_URL + "/fournisseur/{id}";
    private static final String GET_ALL_URL = BASE_URL + "/fournisseurs";
    private static final String GET_BY_COMMUNE_URL = BASE_URL + "/fournisseurs/commune/{communeId}";
    private static final String GET_PAGED_URL = BASE_URL + "/fournisseurs/paged";
    private static final String SEARCH_URL = BASE_URL + "/fournisseurs/search";
    private static final String DELETE_URL = BASE_URL + "/fournisseur/{id}/delete";
    private static final String ACTIVATE_URL = BASE_URL + "/fournisseur/{id}/activate";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FournisseurRepository fournisseurRepository;

    private FournisseurAddEditDTO validDTO;

    @BeforeEach
    void setUp() {
        fournisseurRepository.deleteAll();

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
    }

    @Nested
    @DisplayName("Scénario complet CRUD")
    class FullCrudScenario {

        @Test
        @DisplayName("Devrait exécuter le scénario complet : Création → Lecture → Mise à jour → Suppression → Réactivation")
        void shouldExecuteFullCrudScenario() throws Exception {
            String createResponse = mockMvc.perform(post(SAVE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(validDTO)))
                    .andExpect(status().isCreated())
                    .andReturn().getResponse().getContentAsString();

            List<Fournisseur> fournisseurs = fournisseurRepository.findAll();
            assertThat(fournisseurs).hasSize(1);
            Long createdId = fournisseurs.getFirst().getId();
            assertThat(fournisseurs.getFirst().getDenomination()).isEqualTo("ABC Corporation");
            assertThat(fournisseurs.getFirst().isActif()).isTrue();

            mockMvc.perform(get(GET_BY_ID_URL, createdId)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.denomination").value("ABC Corporation"))
                    .andExpect(jsonPath("$.sigle").value("ABC"))
                    .andExpect(jsonPath("$.mobile").value("771234567"))
                    .andExpect(jsonPath("$.email").value("contact@abc.com"))
                    .andExpect(jsonPath("$.communeId").value(1))
                    .andExpect(jsonPath("$.tenantId").value("tenant-123"));

            FournisseurAddEditDTO updateDTO = FournisseurAddEditDTO.builder()
                    .denomination("ABC Corporation Updated")
                    .sigle("ABCU")
                    .mobile("771234599")
                    .telephone("338201299")
                    .email("updated@abc.com")
                    .adresse("456 Rue du Commerce Modifié")
                    .communeId(2L)
                    .tenantId("tenant-456")
                    .actif(1)
                    .build();

            mockMvc.perform(put(UPDATE_URL, createdId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updateDTO)))
                    .andExpect(status().isOk());

            Fournisseur updated = fournisseurRepository.findById(createdId).orElseThrow();
            assertThat(updated.getDenomination()).isEqualTo("ABC Corporation Updated");
            assertThat(updated.getSigle()).isEqualTo("ABCU");
            assertThat(updated.getMobile()).isEqualTo("771234599");
            assertThat(updated.getEmail()).isEqualTo("updated@abc.com");
            assertThat(updated.getUpdatedAt()).isNotNull();

            FournisseurAddEditDTO secondDTO = FournisseurAddEditDTO.builder()
                    .denomination("XYZ Enterprise")
                    .sigle("XYZ")
                    .mobile("771234568")
                    .telephone("338201235")
                    .email("contact@xyz.com")
                    .adresse("789 Avenue des Affaires")
                    .communeId(1L)
                    .tenantId("tenant-789")
                    .actif(1)
                    .build();

            mockMvc.perform(post(SAVE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(secondDTO)))
                    .andExpect(status().isCreated());

            mockMvc.perform(get(GET_ALL_URL)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[*].denomination", containsInAnyOrder(
                            "ABC Corporation Updated", "XYZ Enterprise")));

            mockMvc.perform(get(GET_BY_COMMUNE_URL, 1L)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(1)))
                    .andExpect(jsonPath("$[0].denomination").value("XYZ Enterprise"));

            mockMvc.perform(delete(DELETE_URL, createdId))
                    .andExpect(status().isOk());

            Fournisseur deactivated = fournisseurRepository.findById(createdId).orElseThrow();
            assertThat(deactivated.isActif()).isFalse();

            mockMvc.perform(get(GET_ALL_URL)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(1)))
                    .andExpect(jsonPath("$[0].denomination").value("XYZ Enterprise"));

            mockMvc.perform(patch(ACTIVATE_URL, fournisseurs.stream()
                            .filter(f -> "XYZ Enterprise".equals(f.getDenomination()))
                            .findFirst().orElseThrow().getId()))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.message").value(containsString("déjà actif")));
        }

        @Test
        @DisplayName("Devrait empêcher la création d'un fournisseur avec une dénomination existante")
        void shouldPreventDuplicateDenomination() throws Exception {
            mockMvc.perform(post(SAVE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(validDTO)))
                    .andExpect(status().isCreated());

            mockMvc.perform(post(SAVE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(validDTO)))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.status").value(409))
                    .andExpect(jsonPath("$.error").value("Conflit de données"))
                    .andExpect(jsonPath("$.message").value(containsString("ABC Corporation")))
                    .andExpect(jsonPath("$.message").value(containsString("existe déjà")));
            assertThat(fournisseurRepository.count()).isEqualTo(1);
        }

        @Test
        @DisplayName("Devrait empêcher la mise à jour avec une dénomination existante")
        void shouldPreventUpdateWithDuplicateDenomination() throws Exception {
            mockMvc.perform(post(SAVE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(validDTO)))
                    .andExpect(status().isCreated());

            FournisseurAddEditDTO secondDTO = FournisseurAddEditDTO.builder()
                    .denomination("XYZ Enterprise")
                    .sigle("XYZ")
                    .mobile("771234568")
                    .communeId(2L)
                    .tenantId("tenant-456")
                    .actif(1)
                    .build();

            mockMvc.perform(post(SAVE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(secondDTO)))
                    .andExpect(status().isCreated());

            List<Fournisseur> fournisseurs = fournisseurRepository.findAll();
            Long secondId = fournisseurs.stream()
                    .filter(f -> "XYZ Enterprise".equals(f.getDenomination()))
                    .findFirst().orElseThrow().getId();

            FournisseurAddEditDTO updateDTO = FournisseurAddEditDTO.builder()
                    .denomination("ABC Corporation")
                    .sigle("XYZ")
                    .mobile("771234568")
                    .communeId(2L)
                    .tenantId("tenant-456")
                    .actif(1)
                    .build();

            mockMvc.perform(put(UPDATE_URL, secondId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updateDTO)))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.message").value(containsString("existe déjà")));
        }

        @Test
        @DisplayName("Devrait valider les champs obligatoires à la création")
        void shouldValidateRequiredFieldsOnCreation() throws Exception {
            FournisseurAddEditDTO invalidDTO = FournisseurAddEditDTO.builder()
                    .denomination("")
                    .mobile("")
                    .communeId(null)
                    .tenantId("tenant-123")
                    .build();

            mockMvc.perform(post(SAVE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(invalidDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.error").value("Erreur de validation"))
                    .andExpect(jsonPath("$.details.denomination").value("La dénomination est obligatoire"))
                    .andExpect(jsonPath("$.details.mobile").value("Le numéro de mobile est obligatoire"))
                    .andExpect(jsonPath("$.details.communeId").value("La commune est obligatoire"));

            assertThat(fournisseurRepository.count()).isZero();
        }
    }

    @Nested
    @DisplayName("Tests de pagination et recherche")
    class PaginationAndSearchTests {

        @BeforeEach
        void setUpData() throws Exception {
            for (int i = 1; i <= 5; i++) {
                FournisseurAddEditDTO dto = FournisseurAddEditDTO.builder()
                        .denomination("Fournisseur " + String.format("%02d", i))
                        .sigle("F" + i)
                        .mobile("77123456" + i)
                        .telephone("33820123" + i)
                        .email("contact" + i + "@test.com")
                        .adresse("Adresse " + i)
                        .communeId((long) (i % 2 + 1))
                        .tenantId("tenant-" + i)
                        .actif(1)
                        .build();

                mockMvc.perform(post(SAVE_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isCreated());
            }
        }

        @Test
        @DisplayName("Devrait paginer les résultats correctement")
        void shouldPaginateResultsCorrectly() throws Exception {
            mockMvc.perform(get(GET_PAGED_URL)
                            .param("page", "0")
                            .param("size", "2")
                            .param("sort", "denomination,asc")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content", hasSize(2)))
                    .andExpect(jsonPath("$.totalElements").value(5))
                    .andExpect(jsonPath("$.totalPages").value(3))
                    .andExpect(jsonPath("$.number").value(0))
                    .andExpect(jsonPath("$.content[0].denomination").value("Fournisseur 01"))
                    .andExpect(jsonPath("$.content[1].denomination").value("Fournisseur 02"));

            mockMvc.perform(get(GET_PAGED_URL)
                            .param("page", "1")
                            .param("size", "2")
                            .param("sort", "denomination,asc")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content", hasSize(2)))
                    .andExpect(jsonPath("$.number").value(1))
                    .andExpect(jsonPath("$.content[0].denomination").value("Fournisseur 03"))
                    .andExpect(jsonPath("$.content[1].denomination").value("Fournisseur 04"));

            mockMvc.perform(get(GET_PAGED_URL)
                            .param("page", "2")
                            .param("size", "2")
                            .param("sort", "denomination,asc")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content", hasSize(1)))
                    .andExpect(jsonPath("$.number").value(2))
                    .andExpect(jsonPath("$.content[0].denomination").value("Fournisseur 05"));
        }

        @Test
        @DisplayName("Devrait trier par ordre décroissant")
        void shouldSortDescending() throws Exception {
            mockMvc.perform(get(GET_PAGED_URL)
                            .param("page", "0")
                            .param("size", "5")
                            .param("sort", "denomination,desc")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content", hasSize(5)))
                    .andExpect(jsonPath("$.content[0].denomination").value("Fournisseur 05"))
                    .andExpect(jsonPath("$.content[4].denomination").value("Fournisseur 01"));
        }

        @Test
        @DisplayName("Devrait filtrer les fournisseurs par dénomination")
        void shouldFilterFournisseursByDenomination() throws Exception {
            mockMvc.perform(post(SEARCH_URL)
                            .param("filtre", "{\"libelle\":\"01\"}")
                            .param("page", "0")
                            .param("size", "10")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content", hasSize(2)))
                    .andExpect(jsonPath("$.content[0].denomination").value(containsString("01")));
        }

        @Test
        @DisplayName("Devrait filtrer par commune")
        void shouldFilterByCommune() throws Exception {
            mockMvc.perform(get(GET_BY_COMMUNE_URL, 1L)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(3)))
                    .andExpect(jsonPath("$[*].denomination", hasItems(
                            "Fournisseur 01", "Fournisseur 03", "Fournisseur 05")));
        }

        @Test
        @DisplayName("Devrait retourner 204 pour une commune sans fournisseur")
        void shouldReturn204ForCommuneWithoutFournisseur() throws Exception {
            mockMvc.perform(get(GET_BY_COMMUNE_URL, 99L)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());
        }
    }

    @Nested
    @DisplayName("Tests de normalisation des données")
    class DataNormalizationTests {

        @Test
        @DisplayName("Devrait normaliser les espaces et la casse à la création")
        void shouldNormalizeDataOnCreation() throws Exception {
            FournisseurAddEditDTO dtoWithSpaces = FournisseurAddEditDTO.builder()
                    .denomination("  Test Corporation  ")
                    .sigle("  tst  ")
                    .mobile("771234567")
                    .telephone("338201234")
                    .email("  TEST@EXAMPLE.COM  ")
                    .adresse("  123 Rue Test  ")
                    .communeId(1L)
                    .tenantId("tenant-123")
                    .actif(1)
                    .build();

            mockMvc.perform(post(SAVE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(dtoWithSpaces)))
                    .andExpect(status().isCreated());

            Fournisseur saved = fournisseurRepository.findAll().getFirst();
            assertThat(saved.getDenomination()).isEqualTo("Test Corporation");
            assertThat(saved.getSigle()).isEqualTo("TST");
            assertThat(saved.getEmail()).isEqualTo("test@example.com");
            assertThat(saved.getAdresse()).isEqualTo("123 Rue Test");
        }

        @Test
        @DisplayName("Devrait normaliser les données à la mise à jour")
        void shouldNormalizeDataOnUpdate() throws Exception {
            mockMvc.perform(post(SAVE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(validDTO)))
                    .andExpect(status().isCreated());

            Long id = fournisseurRepository.findAll().getFirst().getId();

            FournisseurAddEditDTO updateDTO = FournisseurAddEditDTO.builder()
                    .denomination("  Updated Corp  ")
                    .sigle("  upd  ")
                    .mobile("771234599")
                    .email("  UPDATED@CORP.COM  ")
                    .adresse("  456 Rue Updated  ")
                    .communeId(2L)
                    .tenantId("tenant-456")
                    .actif(1)
                    .build();

            mockMvc.perform(put(UPDATE_URL, id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(updateDTO)))
                    .andExpect(status().isOk());

            Fournisseur updated = fournisseurRepository.findById(id).orElseThrow();
            assertThat(updated.getDenomination()).isEqualTo("Updated Corp");
            assertThat(updated.getSigle()).isEqualTo("UPD");
            assertThat(updated.getEmail()).isEqualTo("updated@corp.com");
            assertThat(updated.getAdresse()).isEqualTo("456 Rue Updated");
        }
    }

    @Nested
    @DisplayName("Tests de gestion des erreurs")
    class ErrorHandlingTests {

        @Test
        @DisplayName("Devrait retourner 404 pour un fournisseur inexistant")
        void shouldReturn404ForNonExistentFournisseur() throws Exception {
            mockMvc.perform(get(GET_BY_ID_URL, 999L)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.status").value(404))
                    .andExpect(jsonPath("$.error").value(containsString("non trouvé")))
                    .andExpect(jsonPath("$.message").value(containsString("999")));

            mockMvc.perform(put(UPDATE_URL, 999L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(validDTO)))
                    .andExpect(status().isNotFound());

            mockMvc.perform(delete(DELETE_URL, 999L))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Devrait retourner 400 pour un corps de requête vide")
        void shouldReturn400ForEmptyRequestBody() throws Exception {
            mockMvc.perform(post(SAVE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(""))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.error").value("Requête invalide"));
        }

        @Test
        @DisplayName("Devrait retourner 400 pour un JSON invalide")
        void shouldReturn400ForInvalidJson() throws Exception {
            mockMvc.perform(post(SAVE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{invalid json}"))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.error").value("Requête invalide"));
        }

        @Test
        @DisplayName("Devrait retourner 400 pour un format de mobile invalide")
        void shouldReturn400ForInvalidMobileFormat() throws Exception {
            validDTO.setMobile("12345");

            mockMvc.perform(post(SAVE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(validDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.details.mobile").value(
                            "Le numéro de mobile doit contenir entre 8 et 15 chiffres"));
        }

        @Test
        @DisplayName("Devrait retourner 400 pour un email invalide")
        void shouldReturn400ForInvalidEmail() throws Exception {
            validDTO.setEmail("invalid-email");

            mockMvc.perform(post(SAVE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(validDTO)))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.details.email").value("Le format de l'email est invalide"));
        }
    }

    @Nested
    @DisplayName("Tests de performance simples")
    class PerformanceTests {

        @Test
        @DisplayName("Devrait gérer la création de 100 fournisseurs en moins de 5 secondes")
        void shouldHandleBulkCreation() throws Exception {
            long startTime = System.currentTimeMillis();

            for (int i = 0; i < 100; i++) {
                FournisseurAddEditDTO dto = FournisseurAddEditDTO.builder()
                        .denomination("Bulk Fournisseur " + i)
                        .sigle("BF" + i)
                        .mobile("7712345" + String.format("%03d", i))
                        .communeId((long) (i % 5 + 1))
                        .tenantId("tenant-bulk")
                        .actif(1)
                        .build();

                mockMvc.perform(post(SAVE_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                        .andExpect(status().isCreated());
            }

            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            assertThat(duration).isLessThan(5000);
            assertThat(fournisseurRepository.count()).isEqualTo(100);
        }
    }
}