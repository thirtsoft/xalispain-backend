package com.wokite.net.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Exception de base pour toute l'application XalisPain.
 * Toutes les exceptions métier doivent hériter de cette classe.
 *
 * @author Équipe XalisPain
 * @version 1.0
 */
@Getter
@Setter
public class XalisPainException extends RuntimeException {

    private final String entityName;
    private final String errorCode;

    public XalisPainException(String message) {
        super(message);
        this.entityName = null;
        this.errorCode = null;
    }

    public XalisPainException(String message, Throwable cause) {
        super(message, cause);
        this.entityName = null;
        this.errorCode = null;
    }

    public XalisPainException(String entityName, String message) {
        super(message);
        this.entityName = entityName;
        this.errorCode = null;
    }

    public XalisPainException(String entityName, String errorCode, String message) {
        super(message);
        this.entityName = entityName;
        this.errorCode = errorCode;
    }

    public XalisPainException(String entityName, String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.entityName = entityName;
        this.errorCode = errorCode;
    }

    protected static String formatEntityName(String entityName) {
        if (entityName == null || entityName.isEmpty()) {
            return "L'objet";
        }
        return entityName.substring(0, 1).toUpperCase() +
                (entityName.length() > 1 ? entityName.substring(1).toLowerCase() : "");
    }
}