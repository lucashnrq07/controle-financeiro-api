package com.lucas.controle_financeiro_api.exceptions;

import java.math.BigDecimal;

public class InsufficientGoalBalanceException extends RuntimeException {
    public InsufficientGoalBalanceException(Long goalId, BigDecimal balance) {
        super("Goal " + goalId + " has insufficient balance. Current: " + balance);
    }
}
