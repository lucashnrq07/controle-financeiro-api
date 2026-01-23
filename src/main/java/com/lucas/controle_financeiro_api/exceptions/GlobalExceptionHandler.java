package com.lucas.controle_financeiro_api.exceptions;

import com.lucas.controle_financeiro_api.exceptions.CategoryNotFoundException;
import com.lucas.controle_financeiro_api.exceptions.MovementNotFoundException;
import com.lucas.controle_financeiro_api.exceptions.UserNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<StandardError> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(StandardError.notFound(ex.getMessage()));
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<StandardError> handleCategoryNotFound(CategoryNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(StandardError.notFound(ex.getMessage()));
    }

    @ExceptionHandler(MovementNotFoundException.class)
    public ResponseEntity<StandardError> handleMovementNotFound(MovementNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(StandardError.notFound(ex.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> handleDataIntegrity(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new StandardError(
                        Instant.now(),
                        409,
                        "Data integrity violation",
                        "Email already exists"
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .findFirst()
                .orElse("Invalid request");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new StandardError(
                        Instant.now(),
                        400,
                        "Validation error",
                        message
                ));
    }

    @ExceptionHandler(GoalNotFoundException.class)
    public ResponseEntity<StandardError> handleGoalNotFound(GoalNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(StandardError.notFound(ex.getMessage()));
    }

    @ExceptionHandler(InvalidAmountException.class)
    public ResponseEntity<StandardError> handleInvalidAmount(InvalidAmountException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new StandardError(
                        Instant.now(),
                        400,
                        "Invalid amount",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(InsufficientGoalBalanceException.class)
    public ResponseEntity<StandardError> handleInsufficientBalance(InsufficientGoalBalanceException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new StandardError(
                        Instant.now(),
                        409,
                        "Business rule violation",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new StandardError(
                        Instant.now(),
                        500,
                        "Unexpected error",
                        "An unexpected error occurred"
                ));
    }
}
