package com.lucas.controle_financeiro_api.exceptions.application;

import java.math.BigDecimal;

public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException(BigDecimal amount) {
        super("Invalid amount: " + amount + ". Value must be greater than zero.");
    }
}

