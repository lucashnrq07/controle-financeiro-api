package com.lucas.controle_financeiro_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Schema(description = "DTO para atualização de meta")
public record UpdateGoalDTO(

        @NotBlank(message = "Nome da meta é obrigatório")
        @Schema(description = "Nome da meta", example = "Comprar moto")
        String name,

        @NotNull
        @DecimalMin(value = "0.01", message = "Valor da meta deve ser maior que zero")
        @Schema(description = "Novo valor desejado", example = "6000.00")
        BigDecimal desiredAmount
) {}
