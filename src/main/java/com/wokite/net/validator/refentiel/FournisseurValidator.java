package com.wokite.net.validator.refentiel;

import com.wokite.net.exception.ValidationXalisPainException;
import com.wokite.net.utils.dto.referentiel.FournisseurAddEditDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.wokite.net.utils.constances.referentiel.ConstantsReferentiel.FOURNISSEUR_ENTITY;

@Component
@Slf4j
public class FournisseurValidator {

    private static final String REGEX_PHONE = "^[0-9]{8,15}$";
    private static final String REGEX_EMAIL = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    public void validateForCreation(FournisseurAddEditDTO dto) {
        if (dto == null) {
            throw new ValidationXalisPainException(
                    FOURNISSEUR_ENTITY,
                    "L'objet " + FOURNISSEUR_ENTITY.toLowerCase() + " ne peut pas être null"
            );
        }

        Map<String, String> errors = new HashMap<>();

        validateDenomination(dto.getDenomination(), errors);
        validateMobile(dto.getMobile(), errors);
        validateCommune(dto.getCommuneId(), errors);

        validateSigle(dto.getSigle(), errors);
        validateEmail(dto.getEmail(), errors);
        validateTelephone(dto.getTelephone(), errors);
        validateAdresse(dto.getAdresse(), errors);

        if (!errors.isEmpty()) {
            log.warn("Validation échouée pour la création d'un {}: {}", FOURNISSEUR_ENTITY, errors);
            throw new ValidationXalisPainException(FOURNISSEUR_ENTITY, errors);
        }
    }

    public void validateForUpdate(Long id, FournisseurAddEditDTO dto) {
        if (id == null || id <= 0) {
            throw new ValidationXalisPainException(
                    FOURNISSEUR_ENTITY,
                    "L'ID du " + FOURNISSEUR_ENTITY.toLowerCase() + " est invalide pour la mise à jour"
            );
        }

        if (dto == null) {
            throw new ValidationXalisPainException(
                    FOURNISSEUR_ENTITY,
                    "L'objet " + FOURNISSEUR_ENTITY.toLowerCase() + " ne peut pas être null"
            );
        }

        validateForCreation(dto);
    }

    private void validateDenomination(String denomination, Map<String, String> errors) {
        if (denomination == null || denomination.trim().isEmpty()) {
            errors.put("denomination", "La dénomination est obligatoire");
        } else {
            String trimmed = denomination.trim();
            if (trimmed.length() < 2) {
                errors.put("denomination", "La dénomination doit contenir au moins 2 caractères");
            } else if (trimmed.length() > 100) {
                errors.put("denomination", "La dénomination ne doit pas dépasser 100 caractères");
            }
        }
    }

    private void validateMobile(String mobile, Map<String, String> errors) {
        if (mobile == null || mobile.trim().isEmpty()) {
            errors.put("mobile", "Le numéro de mobile est obligatoire");
        } else if (!mobile.trim().matches(REGEX_PHONE)) {
            errors.put("mobile", "Le numéro de mobile doit contenir entre 8 et 15 chiffres");
        }
    }

    private void validateCommune(Long communeId, Map<String, String> errors) {
        if (communeId == null || communeId <= 0) {
            errors.put("communeId", "La commune est obligatoire");
        }
    }

    private void validateSigle(String sigle, Map<String, String> errors) {
        if (sigle != null && sigle.trim().length() > 10) {
            errors.put("sigle", "Le sigle ne doit pas dépasser 10 caractères");
        }
    }

    private void validateEmail(String email, Map<String, String> errors) {
        if (email != null && !email.trim().isEmpty()) {
            String trimmedEmail = email.trim().toLowerCase();
            if (!trimmedEmail.matches(REGEX_EMAIL)) {
                errors.put("email", "Le format de l'email est invalide");
            } else if (trimmedEmail.length() > 100) {
                errors.put("email", "L'email ne doit pas dépasser 100 caractères");
            }
        }
    }

    private void validateTelephone(String telephone, Map<String, String> errors) {
        if (telephone != null && !telephone.trim().isEmpty() && !telephone.trim().matches(REGEX_PHONE)) {
            errors.put("telephone", "Le numéro de téléphone doit contenir entre 8 et 15 chiffres");
        }
    }

    private void validateAdresse(String adresse, Map<String, String> errors) {
        if (adresse != null && adresse.trim().length() > 150) {
            errors.put("adresse", "L'adresse ne doit pas dépasser 150 caractères");
        }
    }

    private void validateTenantId(String tenantId, Map<String, String> errors) {
        if (tenantId == null || tenantId.trim().isEmpty()) {
            errors.put("tenantId", "Le tenant est obligatoire");
        }
    }
}