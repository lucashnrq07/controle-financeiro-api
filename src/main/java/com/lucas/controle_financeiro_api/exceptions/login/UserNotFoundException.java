package com.lucas.controle_financeiro_api.exceptions.login;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("User not found with id: " + id);
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
