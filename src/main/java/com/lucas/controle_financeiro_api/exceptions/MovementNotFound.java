package com.lucas.controle_financeiro_api.exceptions;

public class MovementNotFound extends RuntimeException {
    public MovementNotFound(Long id) {
    super("Movement not found with ID: " + id);
    }
}
