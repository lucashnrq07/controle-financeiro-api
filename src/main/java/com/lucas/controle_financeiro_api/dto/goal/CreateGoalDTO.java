package com.lucas.controle_financeiro_api.dto.goal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Schema(description = "DTO para criação de uma meta")
public record CreateGoalDTO(

        @NotBlank(message = "Nome da meta é obrigatório")
        @Schema(description = "Nome da meta", example = "Comprar um carro")
        String name,

        @NotNull
        @DecimalMin(value = "0.01", message = "Valor da meta deve ser maior que zero")
        @Schema(description = "Valor final desejado da meta", example = "5000.00")
        BigDecimal desiredAmount
) {}

