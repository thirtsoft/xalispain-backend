package com.wokite.net.exception;

/**
 * Exception levée lorsque des données invalides sont fournies.
 * S'adapte automatiquement au module appelant.
 *
 * @author Équipe XalisPain
 */
public class InvalidXalisPainDataException extends XalisPainException {

    private static final String ERROR_CODE = "INVALID_DATA";

    public InvalidXalisPainDataException(String entityName, String message) {
        super(
                entityName,
                ERROR_CODE,
                String.format("Données invalides pour %s : %s",
                        formatEntityName(entityName).toLowerCase(), message)
        );
    }

    public InvalidXalisPainDataException(String message) {
        super(ERROR_CODE, message);
    }

    public InvalidXalisPainDataException(String entityName, String message, Throwable cause) {
        super(entityName, ERROR_CODE,
                String.format("Données invalides pour %s : %s",
                        formatEntityName(entityName).toLowerCase(), message),
                cause);
    }
}