package com.wokite.net.exception;

/**
 * Exception levée lorsqu'une ressource n'est pas trouvée.
 * S'adapte automatiquement au module appelant.
 *
 * @author Équipe XalisPain
 */
public class XalisPainNotFoundException extends XalisPainException {

    private static final String ERROR_CODE = "NOT_FOUND";

    public XalisPainNotFoundException(String entityName, Long id) {
        super(
                entityName,
                ERROR_CODE,
                String.format("%s avec l'ID %d n'a pas été trouvé(e)",
                        formatEntityName(entityName), id)
        );
    }

    public XalisPainNotFoundException(Long id) {
        this("Fournisseur", id);
    }

    public XalisPainNotFoundException(String entityName, String message) {
        super(entityName, ERROR_CODE, message);
    }

    public XalisPainNotFoundException(String message) {
        super(ERROR_CODE, message);
    }
}