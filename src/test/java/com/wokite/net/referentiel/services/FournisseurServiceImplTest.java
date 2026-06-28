package com.wokite.net.referentiel.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wokite.net.exception.DuplicateXalisPainException;
import com.wokite.net.exception.InvalidXalisPainDataException;
import com.wokite.net.exception.XalisPainNotFoundException;
import com.wokite.net.referentiel.entity.Fournisseur;
import com.wokite.net.referentiel.mapping.DTOFactoryReferentiel;
import com.wokite.net.referentiel.mapping.ModelFactoryReferentiel;
import com.wokite.net.referentiel.repository.FournisseurRepository;
import com.wokite.net.referentiel.service.ReferentielServiceImpl;
import com.wokite.net.utils.dto.referentiel.FournisseurAddEditDTO;
import com.wokite.net.validator.refentiel.FournisseurValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests du service fournisseur")
class FournisseurServiceImplTest {

    @Mock
    private FournisseurRepository fournisseurRepository;

    @Mock
    private ModelFactoryReferentiel modelFactoryReferentiel;

    @Mock
    private DTOFactoryReferentiel dtoFactoryReferentiel;

    @Mock
    private FournisseurValidator fournisseurValidator;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ReferentielServiceImpl referentielService;

    private FournisseurAddEditDTO validDTO;
    private Fournisseur existingFournisseur;

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

        existingFournisseur = Fournisseur.builder()
                .id(1L)
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
    @DisplayName("Tests de création (saveFournisseur)")
    class SaveFournisseurTests {

        @Test
        @DisplayName("Devrait créer un fournisseur avec succès")
        void shouldCreateFournisseurSuccessfully() {
            doNothing().when(fournisseurValidator).validateForCreation(validDTO);
            when(fournisseurRepository.findByDenomination("ABC Corporation"))
                    .thenReturn(Optional.empty());
            when(modelFactoryReferentiel.fromFournisseurDTOToFournisseurEntity(validDTO))
                    .thenReturn(existingFournisseur);
            when(fournisseurRepository.save(any(Fournisseur.class)))
                    .thenReturn(existingFournisseur);

            assertDoesNotThrow(() -> referentielService.saveFournisseur(validDTO));

            verify(fournisseurValidator).validateForCreation(validDTO);
            verify(fournisseurRepository).findByDenomination("ABC Corporation");
            verify(fournisseurRepository).save(any(Fournisseur.class));
        }

        @Test
        @DisplayName("Devrait lever DuplicateXalisPainException si la dénomination existe déjà")
        void shouldThrowDuplicateExceptionWhenDenominationExists() {
            doNothing().when(fournisseurValidator).validateForCreation(validDTO);
            when(fournisseurRepository.findByDenomination("ABC Corporation"))
                    .thenReturn(Optional.of(existingFournisseur));

            DuplicateXalisPainException exception = assertThrows(
                    DuplicateXalisPainException.class,
                    () -> referentielService.saveFournisseur(validDTO)
            );

            assertTrue(exception.getMessage().contains("ABC Corporation"));
            assertTrue(exception.getMessage().contains("existe déjà"));
            verify(fournisseurRepository, never()).save(any(Fournisseur.class));
        }

        @Test
        @DisplayName("Devrait normaliser les données avant sauvegarde")
        void shouldNormalizeDataBeforeSave() {
            validDTO.setDenomination("  ABC Corporation  ");
            validDTO.setEmail("  CONTACT@ABC.COM  ");
            validDTO.setSigle("  abc  ");

            doNothing().when(fournisseurValidator).validateForCreation(validDTO);
            when(fournisseurRepository.findByDenomination("ABC Corporation"))
                    .thenReturn(Optional.empty());
            when(modelFactoryReferentiel.fromFournisseurDTOToFournisseurEntity(validDTO))
                    .thenReturn(existingFournisseur);
            when(fournisseurRepository.save(any(Fournisseur.class)))
                    .thenReturn(existingFournisseur);

            referentielService.saveFournisseur(validDTO);

            assertEquals("ABC Corporation", validDTO.getDenomination());
            assertEquals("contact@abc.com", validDTO.getEmail());
            assertEquals("ABC", validDTO.getSigle());
        }
    }

    @Nested
    @DisplayName("Tests de mise à jour (updateFournisseur)")
    class UpdateFournisseurTests {

        @Test
        @DisplayName("Devrait mettre à jour un fournisseur avec succès")
        void shouldUpdateFournisseurSuccessfully() {
            Long id = 1L;
            validDTO.setDenomination("XYZ Corporation");

            doNothing().when(fournisseurValidator).validateForUpdate(id, validDTO);
            when(fournisseurRepository.findById(id))
                    .thenReturn(Optional.of(existingFournisseur));
            when(fournisseurRepository.findByDenomination("XYZ Corporation"))
                    .thenReturn(Optional.empty());
            when(fournisseurRepository.save(any(Fournisseur.class)))
                    .thenReturn(existingFournisseur);

            assertDoesNotThrow(() -> referentielService.updateFournisseur(id, validDTO));

            verify(fournisseurRepository).save(existingFournisseur);
            verify(dtoFactoryReferentiel).updateFournisseurFields(existingFournisseur, validDTO);
        }

        @Test
        @DisplayName("Devrait lever XalisPainNotFoundException si le fournisseur n'existe pas")
        void shouldThrowNotFoundExceptionWhenFournisseurNotFound() {
            Long id = 999L;
            doNothing().when(fournisseurValidator).validateForUpdate(id, validDTO);
            when(fournisseurRepository.findById(id)).thenReturn(Optional.empty());

            XalisPainNotFoundException exception = assertThrows(
                    XalisPainNotFoundException.class,
                    () -> referentielService.updateFournisseur(id, validDTO)
            );

            assertTrue(exception.getMessage().contains("999"));
            assertTrue(exception.getMessage().contains("Fournisseur"));
        }

        @Test
        @DisplayName("Devrait accepter la même dénomination pour le même fournisseur")
        void shouldAcceptSameDenominationForSameFournisseur() {
            Long id = 1L;
            validDTO.setDenomination("ABC Corporation"); // Même dénomination

            doNothing().when(fournisseurValidator).validateForUpdate(id, validDTO);
            when(fournisseurRepository.findById(id))
                    .thenReturn(Optional.of(existingFournisseur));
            when(fournisseurRepository.save(any(Fournisseur.class)))
                    .thenReturn(existingFournisseur);

            assertDoesNotThrow(() -> referentielService.updateFournisseur(id, validDTO));

            // findByDenomination ne doit pas être appelé si la dénomination n'a pas changé
            verify(fournisseurRepository, never()).findByDenomination(anyString());
            verify(fournisseurRepository).save(existingFournisseur);
        }
    }

    @Nested
    @DisplayName("Tests de suppression (deleteFournisseur)")
    class DeleteFournisseurTests {

        @Test
        @DisplayName("Devrait désactiver un fournisseur avec succès")
        void shouldDeactivateFournisseurSuccessfully() {
            Long id = 1L;
            when(fournisseurRepository.findById(id))
                    .thenReturn(Optional.of(existingFournisseur));
            when(fournisseurRepository.save(any(Fournisseur.class)))
                    .thenReturn(existingFournisseur);

            assertDoesNotThrow(() -> referentielService.deleteFournisseur(id));

            assertFalse(existingFournisseur.isActif());
            verify(fournisseurRepository).save(existingFournisseur);
        }

        @Test
        @DisplayName("Devrait lever InvalidXalisPainDataException si déjà inactif")
        void shouldThrowExceptionWhenAlreadyInactive() {
            Long id = 1L;
            existingFournisseur.setActif(false);
            when(fournisseurRepository.findById(id))
                    .thenReturn(Optional.of(existingFournisseur));

            InvalidXalisPainDataException exception = assertThrows(
                    InvalidXalisPainDataException.class,
                    () -> referentielService.deleteFournisseur(id)
            );

            assertTrue(exception.getMessage().contains("déjà inactif"));
            verify(fournisseurRepository, never()).save(any(Fournisseur.class));
        }
    }

    @Nested
    @DisplayName("Tests de recherche (findFournisseur)")
    class FindFournisseurTests {

        @Test
        @DisplayName("Devrait trouver un fournisseur par ID")
        void shouldFindFournisseurById() {
            Long id = 1L;
            FournisseurAddEditDTO expectedDTO = FournisseurAddEditDTO.builder()
                    .id(1L)
                    .denomination("ABC Corporation")
                    .build();

            when(fournisseurRepository.findById(id))
                    .thenReturn(Optional.of(existingFournisseur));
            when(dtoFactoryReferentiel.fromFournisseurToFournisseurDTO(existingFournisseur))
                    .thenReturn(expectedDTO);

            FournisseurAddEditDTO result = referentielService.findFournisseur(id);

            assertNotNull(result);
            assertEquals("ABC Corporation", result.getDenomination());
        }

        @Test
        @DisplayName("Devrait lever XalisPainNotFoundException si non trouvé")
        void shouldThrowNotFoundExceptionWhenNotFound() {
            Long id = 999L;
            when(fournisseurRepository.findById(id)).thenReturn(Optional.empty());

            XalisPainNotFoundException exception = assertThrows(
                    XalisPainNotFoundException.class,
                    () -> referentielService.findFournisseur(id)
            );

            assertTrue(exception.getMessage().contains("999"));
            assertTrue(exception.getMessage().contains("Fournisseur"));
        }
    }
}