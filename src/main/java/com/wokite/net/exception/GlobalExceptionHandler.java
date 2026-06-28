package com.wokite.net.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Gestionnaire global des exceptions pour toute l'application.
 * Intercepte toutes les exceptions et retourne des réponses JSON structurées.
 *
 * @author Équipe XalisPain
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(XalisPainNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(XalisPainNotFoundException ex) {
        log.warn("Ressource non trouvée - Entité: {}, Message: {}",
                ex.getEntityName() != null ? ex.getEntityName() : "Inconnue",
                ex.getMessage());

        Map<String, Object> body = buildBaseResponse(
                HttpStatus.NOT_FOUND,
                ex.getEntityName() != null ? ex.getEntityName() + " non trouvé(e)" : "Ressource non trouvée",
                ex.getMessage()
        );

        if (ex.getErrorCode() != null) {
            body.put("errorCode", ex.getErrorCode());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(DuplicateXalisPainException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicate(DuplicateXalisPainException ex) {
        log.warn("Tentative de doublon - Entité: {}, Message: {}",
                ex.getEntityName() != null ? ex.getEntityName() : "Inconnue",
                ex.getMessage());

        Map<String, Object> body = buildBaseResponse(
                HttpStatus.CONFLICT,
                "Conflit de données",
                ex.getMessage()
        );

        if (ex.getErrorCode() != null) {
            body.put("errorCode", ex.getErrorCode());
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(ValidationXalisPainException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(ValidationXalisPainException ex) {
        log.warn("Erreur de validation - Entité: {}, Erreurs: {}",
                ex.getEntityName() != null ? ex.getEntityName() : "Inconnue",
                ex.getValidationErrors() != null ? ex.getValidationErrors().size() : 0);

        Map<String, Object> body = buildBaseResponse(
                HttpStatus.BAD_REQUEST,
                "Erreur de validation",
                ex.getMessage()
        );

        if (ex.getErrorCode() != null) {
            body.put("errorCode", ex.getErrorCode());
        }

        if (ex.getValidationErrors() != null && !ex.getValidationErrors().isEmpty()) {
            body.put("details", ex.getValidationErrors());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(InvalidXalisPainDataException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidData(InvalidXalisPainDataException ex) {
        log.error("Données invalides - Entité: {}, Message: {}",
                ex.getEntityName() != null ? ex.getEntityName() : "Inconnue",
                ex.getMessage());

        Map<String, Object> body = buildBaseResponse(
                HttpStatus.BAD_REQUEST,
                "Données invalides",
                ex.getMessage()
        );

        if (ex.getErrorCode() != null) {
            body.put("errorCode", ex.getErrorCode());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleMessageNotReadable(HttpMessageNotReadableException ex) {
        log.warn("Requête mal formée - Corps JSON invalide: {}", ex.getMessage());

        Map<String, Object> body = buildBaseResponse(
                HttpStatus.BAD_REQUEST,
                "Requête invalide",
                "Le corps de la requête est invalide ou mal formaté. Veuillez vérifier la syntaxe JSON."
        );
        body.put("errorCode", "INVALID_REQUEST_BODY");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        log.warn("Validation Bean échouée: {}", ex.getMessage());

        Map<String, String> errors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        Map<String, Object> body = buildBaseResponse(
                HttpStatus.BAD_REQUEST,
                "Erreur de validation",
                "Des champs obligatoires sont manquants ou invalides"
        );
        body.put("errorCode", "VALIDATION_ERROR");
        body.put("details", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, Object>> handleMissingParams(MissingServletRequestParameterException ex) {
        log.warn("Paramètre manquant: {}", ex.getMessage());

        Map<String, Object> body = buildBaseResponse(
                HttpStatus.BAD_REQUEST,
                "Paramètre manquant",
                "Le paramètre '" + ex.getParameterName() + "' est obligatoire"
        );
        body.put("errorCode", "MISSING_PARAMETER");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        log.warn("Type de paramètre invalide: {}", ex.getMessage());

        Map<String, Object> body = buildBaseResponse(
                HttpStatus.BAD_REQUEST,
                "Type de paramètre invalide",
                "Le paramètre '" + ex.getName() + "' doit être de type " +
                        (ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "valide")
        );
        body.put("errorCode", "TYPE_MISMATCH");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        log.error("Erreur inattendue: ", ex);

        Map<String, Object> body = buildBaseResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erreur interne du serveur",
                "Une erreur inattendue s'est produite. Veuillez contacter l'administrateur."
        );
        body.put("errorCode", "INTERNAL_ERROR");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    private Map<String, Object> buildBaseResponse(HttpStatus status, String error, String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", message);
        return body;
    }

}