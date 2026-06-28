package com.wokite.net.validator.refentiel;

import com.wokite.net.exception.ValidationXalisPainException;
import com.wokite.net.utils.dto.referentiel.CommuneAddEditDTO;
import com.wokite.net.utils.dto.referentiel.DepartementAddEditDTO;
import com.wokite.net.utils.dto.referentiel.RegionAddEditDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.wokite.net.utils.constances.referentiel.ConstantsReferentiel.*;

@Component
@Slf4j
public class LocaliteValidator {

    public void validateForCreationCommune(CommuneAddEditDTO dto) {
        if (dto == null) {
            throw new ValidationXalisPainException(
                    COMMUNE_ENTITY,
                    "L'objet " + COMMUNE_ENTITY.toLowerCase() + " ne peut pas être null"
            );
        }

        Map<String, String> errors = new HashMap<>();

        validateLibelle(dto.getLibelle(), errors);
        validateDepartmenId(dto.getDepartmentId(), errors);

        if (!errors.isEmpty()) {
            log.warn("Validation échouée pour la création de la {}: {}", COMMUNE_ENTITY, errors);
            throw new ValidationXalisPainException(COMMUNE_ENTITY, errors);
        }
    }

    public void validateForUpdateCommune(Long id, CommuneAddEditDTO dto) {
        if (id == null || id <= 0) {
            throw new ValidationXalisPainException(
                    COMMUNE_ENTITY,
                    "L'ID du " + COMMUNE_ENTITY.toLowerCase() + " est invalide pour la mise à jour"
            );
        }

        if (dto == null) {
            throw new ValidationXalisPainException(
                    COMMUNE_ENTITY,
                    "L'objet " + COMMUNE_ENTITY.toLowerCase() + " ne peut pas être null"
            );
        }

        validateForCreationCommune(dto);
    }

    public void validateForCreationDepartment(DepartementAddEditDTO dto) {
        if (dto == null) {
            throw new ValidationXalisPainException(
                    COMMUNE_ENTITY,
                    "L'objet " + COMMUNE_ENTITY.toLowerCase() + " ne peut pas être null"
            );
        }

        Map<String, String> errors = new HashMap<>();

        validateCode(dto.getCode(), errors);
        validateLibelle(dto.getLibelle(), errors);
        validateRegionId(dto.getRegionId(), errors);

        if (!errors.isEmpty()) {
            log.warn("Validation échouée pour la création du {}: {}", DEPARTEMENT_ENTITY, errors);
            throw new ValidationXalisPainException(DEPARTEMENT_ENTITY, errors);
        }
    }

    public void validateForUpdateDepartement(Long id, DepartementAddEditDTO dto) {
        if (id == null || id <= 0) {
            throw new ValidationXalisPainException(
                    DEPARTEMENT_ENTITY,
                    "L'ID du " + DEPARTEMENT_ENTITY.toLowerCase() + " est invalide pour la mise à jour"
            );
        }

        if (dto == null) {
            throw new ValidationXalisPainException(
                    DEPARTEMENT_ENTITY,
                    "L'objet " + DEPARTEMENT_ENTITY.toLowerCase() + " ne peut pas être null"
            );
        }

        validateForCreationDepartment(dto);
    }

    public void validateForCreationRegion(RegionAddEditDTO dto) {
        if (dto == null) {
            throw new ValidationXalisPainException(
                    REGION_ENTITY,
                    "L'objet " + REGION_ENTITY.toLowerCase() + " ne peut pas être null"
            );
        }

        Map<String, String> errors = new HashMap<>();

        validateCode(dto.getCode(), errors);
        validateLibelle(dto.getLibelle(), errors);
        validatePaysId(dto.getPaysId(), errors);

        if (!errors.isEmpty()) {
            log.warn("Validation échouée pour la création de l'a {}: {}", REGION_ENTITY, errors);
            throw new ValidationXalisPainException(REGION_ENTITY, errors);
        }
    }

    public void validateForUpdateRegion(Long id, RegionAddEditDTO dto) {
        if (id == null || id <= 0) {
            throw new ValidationXalisPainException(
                    REGION_ENTITY,
                    "L'ID du " + REGION_ENTITY.toLowerCase() + " est invalide pour la mise à jour"
            );
        }

        if (dto == null) {
            throw new ValidationXalisPainException(
                    REGION_ENTITY,
                    "L'objet " + REGION_ENTITY.toLowerCase() + " ne peut pas être null"
            );
        }

        validateForCreationRegion(dto);
    }


    private void validateCode(String code, Map<String, String> errors) {
        if (code == null || code.trim().isEmpty()) {
            errors.put("code", "Le code est obligatoire");
        } else {
            String trimmed = code.trim();
            if (trimmed.length() > 10) {
                errors.put("code", "Le code ne doit pas dépasser 10 caractères");
            }
        }
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

    private void validateDepartmenId(Long departmentId, Map<String, String> errors) {
        if (departmentId == null) {
            errors.put("department", "L'identifiant du département est obligatoire");
        }
    }

    private void validateRegionId(Long regionId, Map<String, String> errors) {
        if (regionId == null) {
            errors.put("region", "L'identifiant de la région est obligatoire");
        }
    }

    private void validatePaysId(Long paysId, Map<String, String> errors) {
        if (paysId == null) {
            errors.put("pays", "L'identifiant du pays est obligatoire");
        }
    }

    private void validateContinentId(Long continentId, Map<String, String> errors) {
        if (continentId == null) {
            errors.put("continen", "L'identifiant du continent est obligatoire");
        }
    }

    private void validateTenantId(String tenantId, Map<String, String> errors) {
        if (tenantId == null || tenantId.trim().isEmpty()) {
            errors.put("tenantId", "Le tenant est obligatoire");
        }
    }
}