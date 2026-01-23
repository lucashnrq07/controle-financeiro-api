package com.lucas.controle_financeiro_api.exceptions;

public class GoalNotFoundException extends RuntimeException {
    public GoalNotFoundException(Long id) {
        super("Goal not found with ID: " + id);
    }
}
