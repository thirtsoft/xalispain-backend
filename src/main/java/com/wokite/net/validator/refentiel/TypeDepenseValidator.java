package com.wokite.net.validator.refentiel;

import com.wokite.net.exception.ValidationXalisPainException;
import com.wokite.net.utils.dto.referentiel.TypeDepenseAddEditDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.wokite.net.utils.constances.referentiel.ConstantsReferentiel.TYPEDEPENSE_ENTITY;

@Component
@Slf4j
public class TypeDepenseValidator {

    public void validateForCreation(TypeDepenseAddEditDTO dto) {
        if (dto == null) {
            throw new ValidationXalisPainException(
                    TYPEDEPENSE_ENTITY,
                    "L'objet " + TYPEDEPENSE_ENTITY.toLowerCase() + " ne peut pas être null"
            );
        }

        Map<String, String> errors = new HashMap<>();

        validateLibelle(dto.getLibelle(), errors);

        if (!errors.isEmpty()) {
            log.warn("Validation échouée pour la création d'un {}: {}", TYPEDEPENSE_ENTITY, errors);
            throw new ValidationXalisPainException(TYPEDEPENSE_ENTITY, errors);
        }
    }

    public void validateForUpdate(Long id, TypeDepenseAddEditDTO dto) {
        if (id == null || id <= 0) {
            throw new ValidationXalisPainException(
                    TYPEDEPENSE_ENTITY,
                    "L'ID du " + TYPEDEPENSE_ENTITY.toLowerCase() + " est invalide pour la mise à jour"
            );
        }

        if (dto == null) {
            throw new ValidationXalisPainException(
                    TYPEDEPENSE_ENTITY,
                    "L'objet " + TYPEDEPENSE_ENTITY.toLowerCase() + " ne peut pas être null"
            );
        }

        validateForCreation(dto);
    }

    private void validateLibelle(String libelle, Map<String, String> errors) {
        if (libelle == null || libelle.trim().isEmpty()) {
            errors.put("libelle", "Le libelle est obligatoire");
        } else {
            String trimmed = libelle.trim();
            if (trimmed.length() < 2) {
                errors.put("denomination", "Le libelle doit contenir au moins 2 caractères");
            } else if (trimmed.length() > 100) {
                errors.put("libelle", "Le libelle ne doit pas dépasser 100 caractères");
            }
        }
    }

    private void validateTenantId(String tenantId, Map<String, String> errors) {
        if (tenantId == null || tenantId.trim().isEmpty()) {
            errors.put("tenantId", "Le tenant est obligatoire");
        }
    }
}