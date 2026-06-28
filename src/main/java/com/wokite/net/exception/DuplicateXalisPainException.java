package com.wokite.net.exception;

/**
 * Exception levée lorsqu'une ressource en double est détectée.
 * S'adapte automatiquement au module appelant.
 *
 * @author Équipe XalisPain
 */
public class DuplicateXalisPainException extends XalisPainException {

    private static final String ERROR_CODE = "DUPLICATE_ENTRY";

    public DuplicateXalisPainException(String entityName, String fieldName, String value) {
        super(
                entityName,
                ERROR_CODE,
                String.format("Un(e) %s avec le %s '%s' existe déjà",
                        formatEntityName(entityName), fieldName.toLowerCase(), value)
        );
    }

    public DuplicateXalisPainException(String entityName, String denomination) {
        this(entityName, "dénomination", denomination);
    }

    public DuplicateXalisPainException(String denomination) {
        this("Fournisseur", denomination);
    }

    public DuplicateXalisPainException(String entityName, String message, boolean isCustomMessage) {
        super(entityName, ERROR_CODE, message);
    }
}