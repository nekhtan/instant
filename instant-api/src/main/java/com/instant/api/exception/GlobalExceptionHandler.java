package com.instant.api.exception;

import jakarta.validation.ConstraintViolationException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles exceptions globally for the application.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    ResponseEntity<Set<String>> handleConstraintViolationException(ConstraintViolationException e) {
        logger.error(e.getMessage(), e);
        return ResponseEntity.badRequest()
            .body(e.getConstraintViolations()
                .stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage() + " (" + v.getInvalidValue() + ")")
                .collect(Collectors.toSet()));
    }

    @ExceptionHandler
    ResponseEntity<String> handleException(Exception e) {
        logger.error(e.getMessage(), e);
        return ResponseEntity.internalServerError().body("Internal server error occurred");
    }
}
