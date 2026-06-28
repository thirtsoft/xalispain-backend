package com.wokite.net.referentiel.validation;

import com.wokite.net.exception.ValidationXalisPainException;
import com.wokite.net.utils.dto.referentiel.FournisseurAddEditDTO;
import com.wokite.net.validator.refentiel.FournisseurValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests du validateur de fournisseur")
class FournisseurValidatorTest {

    private FournisseurValidator validator;
    private FournisseurAddEditDTO validDTO;

    @BeforeEach
    void setUp() {
        validator = new FournisseurValidator();
        validDTO = createValidDTO();
    }

    @Nested
    @DisplayName("Tests de validation pour la création")
    class ValidationCreation {

        @Test
        @DisplayName("Devrait valider un DTO correct sans exception")
        void shouldValidateCorrectDTO() {
            assertDoesNotThrow(() -> validator.validateForCreation(validDTO));
        }

        @Test
        @DisplayName("Devrait rejeter un DTO null")
        void shouldRejectNullDTO() {
            ValidationXalisPainException exception = assertThrows(
                    ValidationXalisPainException.class,
                    () -> validator.validateForCreation(null)
            );

            // Vérifier que le message existe (peu importe le format exact)
            assertNotNull(exception.getMessage());
            // Le message devrait contenir des informations sur l'erreur
            assertTrue(
                    exception.getMessage().contains("ne peut pas être null") ||
                            exception.getMessage().contains("null"),
                    "Message reçu: " + exception.getMessage()
            );
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"  ", "\t", "\n"})
        @DisplayName("Devrait rejeter une dénomination vide ou null")
        void shouldRejectEmptyDenomination(String denomination) {
            validDTO.setDenomination(denomination);

            ValidationXalisPainException exception = assertThrows(
                    ValidationXalisPainException.class,
                    () -> validator.validateForCreation(validDTO)
            );

            Map<String, String> errors = exception.getValidationErrors();
            assertNotNull(errors, "La map d'erreurs ne devrait pas être null");
            assertTrue(errors.containsKey("denomination"),
                    "Les erreurs devraient contenir 'denomination'");
            assertEquals("La dénomination est obligatoire",
                    errors.get("denomination"));
        }

        @Test
        @DisplayName("Devrait rejeter une dénomination trop courte")
        void shouldRejectShortDenomination() {
            validDTO.setDenomination("A");

            ValidationXalisPainException exception = assertThrows(
                    ValidationXalisPainException.class,
                    () -> validator.validateForCreation(validDTO)
            );

            Map<String, String> errors = exception.getValidationErrors();
            assertNotNull(errors);
            assertTrue(errors.containsKey("denomination"));
            assertTrue(errors.get("denomination").contains("au moins 2 caractères"));
        }

        @Test
        @DisplayName("Devrait rejeter une dénomination trop longue")
        void shouldRejectLongDenomination() {
            validDTO.setDenomination("A".repeat(101));

            ValidationXalisPainException exception = assertThrows(
                    ValidationXalisPainException.class,
                    () -> validator.validateForCreation(validDTO)
            );

            Map<String, String> errors = exception.getValidationErrors();
            assertNotNull(errors);
            assertTrue(errors.containsKey("denomination"));
            assertTrue(errors.get("denomination").contains("ne doit pas dépasser 100"));
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"abc", "12345", "1234567890123456"})
        @DisplayName("Devrait rejeter un mobile invalide")
        void shouldRejectInvalidMobile(String mobile) {
            validDTO.setMobile(mobile);

            ValidationXalisPainException exception = assertThrows(
                    ValidationXalisPainException.class,
                    () -> validator.validateForCreation(validDTO)
            );

            Map<String, String> errors = exception.getValidationErrors();
            assertNotNull(errors);
            assertTrue(errors.containsKey("mobile"),
                    "Les erreurs devraient contenir 'mobile' pour la valeur: " + mobile);
        }

        @Test
        @DisplayName("Devrait accepter un mobile valide")
        void shouldAcceptValidMobile() {
            validDTO.setMobile("771234567");
            assertDoesNotThrow(() -> validator.validateForCreation(validDTO));
        }

        @Test
        @DisplayName("Devrait rejeter un communeId null")
        void shouldRejectNullCommune() {
            validDTO.setCommuneId(null);

            ValidationXalisPainException exception = assertThrows(
                    ValidationXalisPainException.class,
                    () -> validator.validateForCreation(validDTO)
            );

            Map<String, String> errors = exception.getValidationErrors();
            assertNotNull(errors);
            assertTrue(errors.containsKey("communeId"));
        }

        @Test
        @DisplayName("Devrait rejeter un communeId négatif")
        void shouldRejectNegativeCommuneId() {
            validDTO.setCommuneId(-1L);

            ValidationXalisPainException exception = assertThrows(
                    ValidationXalisPainException.class,
                    () -> validator.validateForCreation(validDTO)
            );

            Map<String, String> errors = exception.getValidationErrors();
            assertNotNull(errors);
            assertTrue(errors.containsKey("communeId"));
        }

        @Test
        @DisplayName("Devrait rejeter un communeId zéro")
        void shouldRejectZeroCommuneId() {
            validDTO.setCommuneId(0L);

            ValidationXalisPainException exception = assertThrows(
                    ValidationXalisPainException.class,
                    () -> validator.validateForCreation(validDTO)
            );

            Map<String, String> errors = exception.getValidationErrors();
            assertNotNull(errors);
            assertTrue(errors.containsKey("communeId"));
        }

        @Test
        @DisplayName("Devrait rejeter un email invalide")
        void shouldRejectInvalidEmail() {
            validDTO.setEmail("invalid-email");

            ValidationXalisPainException exception = assertThrows(
                    ValidationXalisPainException.class,
                    () -> validator.validateForCreation(validDTO)
            );

            Map<String, String> errors = exception.getValidationErrors();
            assertNotNull(errors);
            assertTrue(errors.containsKey("email"));
            assertEquals("Le format de l'email est invalide", errors.get("email"));
        }

        @Test
        @DisplayName("Devrait rejeter un email trop long")
        void shouldRejectTooLongEmail() {
            validDTO.setEmail("test@" + "a".repeat(95) + ".com");

            ValidationXalisPainException exception = assertThrows(
                    ValidationXalisPainException.class,
                    () -> validator.validateForCreation(validDTO)
            );

            Map<String, String> errors = exception.getValidationErrors();
            assertNotNull(errors);
            assertTrue(errors.containsKey("email"));
            assertTrue(errors.get("email").contains("ne doit pas dépasser 100"));
        }

        @Test
        @DisplayName("Devrait accepter un email valide")
        void shouldAcceptValidEmail() {
            validDTO.setEmail("test@example.com");
            assertDoesNotThrow(() -> validator.validateForCreation(validDTO));
        }

        @Test
        @DisplayName("Devrait accepter un email null (optionnel)")
        void shouldAcceptNullEmail() {
            validDTO.setEmail(null);
            assertDoesNotThrow(() -> validator.validateForCreation(validDTO));
        }

        @Test
        @DisplayName("Devrait accepter un email vide (optionnel)")
        void shouldAcceptEmptyEmail() {
            validDTO.setEmail("");
            assertDoesNotThrow(() -> validator.validateForCreation(validDTO));
        }

        @Test
        @DisplayName("Devrait rejeter un sigle trop long")
        void shouldRejectLongSigle() {
            validDTO.setSigle("A".repeat(11));

            ValidationXalisPainException exception = assertThrows(
                    ValidationXalisPainException.class,
                    () -> validator.validateForCreation(validDTO)
            );

            Map<String, String> errors = exception.getValidationErrors();
            assertNotNull(errors);
            assertTrue(errors.containsKey("sigle"));
            assertTrue(errors.get("sigle").contains("ne doit pas dépasser 10"));
        }

        @Test
        @DisplayName("Devrait accepter un sigle null")
        void shouldAcceptNullSigle() {
            validDTO.setSigle(null);
            assertDoesNotThrow(() -> validator.validateForCreation(validDTO));
        }

        @Test
        @DisplayName("Devrait accepter un sigle vide")
        void shouldAcceptEmptySigle() {
            validDTO.setSigle("");
            assertDoesNotThrow(() -> validator.validateForCreation(validDTO));
        }

        @Test
        @DisplayName("Devrait rejeter un téléphone invalide")
        void shouldRejectInvalidTelephone() {
            validDTO.setTelephone("abc");

            ValidationXalisPainException exception = assertThrows(
                    ValidationXalisPainException.class,
                    () -> validator.validateForCreation(validDTO)
            );

            Map<String, String> errors = exception.getValidationErrors();
            assertNotNull(errors);
            assertTrue(errors.containsKey("telephone"));
        }

        @Test
        @DisplayName("Devrait accepter un téléphone valide")
        void shouldAcceptValidTelephone() {
            validDTO.setTelephone("338201234");
            assertDoesNotThrow(() -> validator.validateForCreation(validDTO));
        }

        @Test
        @DisplayName("Devrait accepter un téléphone null")
        void shouldAcceptNullTelephone() {
            validDTO.setTelephone(null);
            assertDoesNotThrow(() -> validator.validateForCreation(validDTO));
        }

        @Test
        @DisplayName("Devrait accepter un téléphone vide")
        void shouldAcceptEmptyTelephone() {
            validDTO.setTelephone("");
            assertDoesNotThrow(() -> validator.validateForCreation(validDTO));
        }

        @Test
        @DisplayName("Devrait rejeter une adresse trop longue")
        void shouldRejectTooLongAdresse() {
            validDTO.setAdresse("A".repeat(151));

            ValidationXalisPainException exception = assertThrows(
                    ValidationXalisPainException.class,
                    () -> validator.validateForCreation(validDTO)
            );

            Map<String, String> errors = exception.getValidationErrors();
            assertNotNull(errors);
            assertTrue(errors.containsKey("adresse"));
            assertTrue(errors.get("adresse").contains("ne doit pas dépasser 150"));
        }

        @Test
        @DisplayName("Devrait accepter une adresse null")
        void shouldAcceptNullAdresse() {
            validDTO.setAdresse(null);
            assertDoesNotThrow(() -> validator.validateForCreation(validDTO));
        }

        @Test
        @DisplayName("Devrait accepter une adresse vide")
        void shouldAcceptEmptyAdresse() {
            validDTO.setAdresse("");
            assertDoesNotThrow(() -> validator.validateForCreation(validDTO));
        }

        @Test
        @DisplayName("Devrait retourner plusieurs erreurs simultanément")
        void shouldReturnMultipleErrors() {
            validDTO.setDenomination("");
            validDTO.setMobile("");
            validDTO.setCommuneId(null);

            ValidationXalisPainException exception = assertThrows(
                    ValidationXalisPainException.class,
                    () -> validator.validateForCreation(validDTO)
            );

            Map<String, String> errors = exception.getValidationErrors();
            assertNotNull(errors);
            assertTrue(errors.size() >= 3,
                    "Devrait avoir au moins 3 erreurs, mais a: " + errors.size());
            assertTrue(errors.containsKey("denomination"));
            assertTrue(errors.containsKey("mobile"));
            assertTrue(errors.containsKey("communeId"));
        }

        // ... autres tests
    }

    @Nested
    @DisplayName("Tests de validation pour la mise à jour")
    class ValidationUpdate {

        @Test
        @DisplayName("Devrait valider un DTO correct pour la mise à jour")
        void shouldValidateCorrectDTOForUpdate() {
            assertDoesNotThrow(() -> validator.validateForUpdate(1L, validDTO));
        }

        @Test
        @DisplayName("Devrait rejeter un ID null")
        void shouldRejectNullId() {
            ValidationXalisPainException exception = assertThrows(
                    ValidationXalisPainException.class,
                    () -> validator.validateForUpdate(null, validDTO)
            );

            assertNotNull(exception.getMessage());
            assertTrue(
                    exception.getMessage().contains("ID") ||
                            exception.getMessage().contains("invalide"),
                    "Message reçu: " + exception.getMessage()
            );
        }

        @Test
        @DisplayName("Devrait rejeter un ID zéro")
        void shouldRejectZeroId() {
            ValidationXalisPainException exception = assertThrows(
                    ValidationXalisPainException.class,
                    () -> validator.validateForUpdate(0L, validDTO)
            );

            assertNotNull(exception.getMessage());
            assertTrue(
                    exception.getMessage().contains("ID") ||
                            exception.getMessage().contains("invalide"),
                    "Message reçu: " + exception.getMessage()
            );
        }

        @Test
        @DisplayName("Devrait rejeter un ID négatif")
        void shouldRejectNegativeId() {
            ValidationXalisPainException exception = assertThrows(
                    ValidationXalisPainException.class,
                    () -> validator.validateForUpdate(-1L, validDTO)
            );

            assertNotNull(exception.getMessage());
            assertTrue(
                    exception.getMessage().contains("ID") ||
                            exception.getMessage().contains("invalide"),
                    "Message reçu: " + exception.getMessage()
            );
        }

        @Test
        @DisplayName("Devrait rejeter un DTO null pour la mise à jour")
        void shouldRejectNullDTOForUpdate() {
            ValidationXalisPainException exception = assertThrows(
                    ValidationXalisPainException.class,
                    () -> validator.validateForUpdate(1L, null)
            );

            assertNotNull(exception.getMessage());
            assertTrue(
                    exception.getMessage().contains("ne peut pas être null") ||
                            exception.getMessage().contains("null"),
                    "Message reçu: " + exception.getMessage()
            );
        }

        @Test
        @DisplayName("Devrait propager les erreurs de validation du DTO lors de la mise à jour")
        void shouldPropagateDTOValidationErrorsOnUpdate() {
            validDTO.setDenomination("");
            validDTO.setMobile("");

            ValidationXalisPainException exception = assertThrows(
                    ValidationXalisPainException.class,
                    () -> validator.validateForUpdate(1L, validDTO)
            );

            Map<String, String> errors = exception.getValidationErrors();
            assertNotNull(errors);
            assertTrue(errors.size() >= 2,
                    "Devrait avoir au moins 2 erreurs, mais a: " + errors.size());
            assertTrue(errors.containsKey("denomination"));
            assertTrue(errors.containsKey("mobile"));
        }
    }

    // Helper method
    private FournisseurAddEditDTO createValidDTO() {
        return FournisseurAddEditDTO.builder()
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
}