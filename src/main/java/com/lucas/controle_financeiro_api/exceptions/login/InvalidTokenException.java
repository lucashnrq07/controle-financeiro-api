package com.lucas.controle_financeiro_api.exceptions.login;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) {
        super(message);
    }
}
