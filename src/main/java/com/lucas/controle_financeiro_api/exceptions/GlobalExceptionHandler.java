package com.lucas.controle_financeiro_api.exceptions;

import com.lucas.controle_financeiro_api.exceptions.CategoryNotFoundException;
import com.lucas.controle_financeiro_api.exceptions.MovementNotFoundException;
import com.lucas.controle_financeiro_api.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}
