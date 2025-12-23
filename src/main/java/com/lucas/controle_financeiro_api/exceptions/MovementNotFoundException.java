package com.lucas.controle_financeiro_api.exceptions;

public class MovementNotFoundException extends RuntimeException {
    public MovementNotFoundException(Long id) {
    super("Movement not found with ID: " + id);
    }
}
