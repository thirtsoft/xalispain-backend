package com.wokite.net.exception;

import lombok.Getter;

import java.util.Collections;
import java.util.Map;

/**
 * Exception levée lors d'erreurs de validation des données.
 * S'adapte automatiquement au module appelant.
 *
 * @author Équipe XalisPain
 */
@Getter
public class ValidationXalisPainException extends XalisPainException {

    private static final String ERROR_CODE = "VALIDATION_ERROR";
    private final Map<String, String> validationErrors;

    /**
     * Constructeur avec message simple
     *
     * @param entityName Nom de l'entité concernée
     * @param message    Message de validation
     */
    public ValidationXalisPainException(String entityName, String message) {
        super(entityName, ERROR_CODE, message);
        this.validationErrors = null;
    }

    /**
     * Constructeur avec map d'erreurs détaillées
     *
     * @param entityName       Nom de l'entité concernée
     * @param validationErrors Map des erreurs (champ -> message)
     */
    public ValidationXalisPainException(String entityName, Map<String, String> validationErrors) {
        super(
                entityName,
                ERROR_CODE,
                String.format("Erreur de validation des données du %s",
                        formatEntityName(entityName).toLowerCase())
        );
        this.validationErrors = Collections.unmodifiableMap(validationErrors);
    }

    /**
     * Constructeur simple avec message uniquement (ajoute le nom d'entité par défaut)
     *
     * @param message Message d'erreur
     */
    public ValidationXalisPainException(String message) {
        super("Entité", ERROR_CODE, message);
        this.validationErrors = null;
    }

    /**
     * Constructeur avec map d'erreurs (rétrocompatibilité - ajoute le nom d'entité par défaut)
     */
    public ValidationXalisPainException(Map<String, String> validationErrors) {
        super(
                "Entité",
                ERROR_CODE,
                "Erreur de validation des données"
        );
        this.validationErrors = Collections.unmodifiableMap(validationErrors);
    }

}