package com.lucas.controle_financeiro_api.exceptions.login;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
