package com.lucas.controle_financeiro_api.dto.movement;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "DTO para criação de uma movimentação financeira")
public record CreateMovementDTO(

        @NotNull
        @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
        @Schema(description = "Valor da movimentação (sempre positivo)", example = "150.75")
        BigDecimal amount,

        @NotNull
        @JsonFormat(pattern = "dd/MM/yyyy")
        @Schema(description = "Data da movimentação", example = "23/12/2025")
        LocalDate date,

        @Schema(description = "Descrição opcional da movimentação", example = "Compra no supermercado")
        String description,

        @NotNull
        @Schema(description = "ID da categoria associada", example = "5")
        Long categoryId
) {}
