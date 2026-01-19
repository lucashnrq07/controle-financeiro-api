package com.lucas.controle_financeiro_api.dto;

import com.lucas.controle_financeiro_api.domain.entities.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record GoalMovementDTO(
        @NotNull
        @Positive
        BigDecimal amount,

        @NotNull
        User user
) {}

