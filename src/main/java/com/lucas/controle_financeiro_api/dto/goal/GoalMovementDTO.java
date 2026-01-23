package com.lucas.controle_financeiro_api.dto.goal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record GoalMovementDTO(

        @NotNull
        @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
        @Schema(description = "Valor a ser depositado ou retirado da meta", example = "200.00")
        BigDecimal amount,

        @NotNull
        @Schema(description = "ID do usuário que está realizando a operação", example = "3")
        Long userId
) {}
