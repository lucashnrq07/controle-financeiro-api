package com.lucas.controle_financeiro_api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lucas.controle_financeiro_api.domain.entities.Movement;
import com.lucas.controle_financeiro_api.domain.enums.CategoryType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "DTO para criação e retorno de movimentações financeiras")
public record MovementDTO(

        @Schema(
                description = "ID da movimentação"
        )
        Long id,

        @NotNull
        @Positive
        @Schema(
                description = "Valor da movimentação (sempre positivo)",
                example = "150.75"
        )
        BigDecimal amount,

        @NotNull
        @JsonFormat(pattern = "dd/MM/yyyy")
        @Schema(
                description = "Data da movimentação",
                example = "23/12/2025"
        )
        LocalDate date,

        @Schema(
                description = "Descrição opcional da movimentação",
                example = "Compra no supermercado"
        )
        String description,

        @NotNull
        @Schema(
                description = "ID da categoria associada à movimentação",
                example = "5"
        )
        Long categoryId,

        @Schema(
                description = "Nome da categoria",
                example = "Salário"
        )
        String categoryName,

        @Schema(
                description = "Tipo da categoria",
                example = "Entrada"
        )
        CategoryType type,

        @NotNull
        @Schema(
                description = "ID do usuário dono da movimentação",
                example = "1"
        )
        Long userId
) {

    public static MovementDTO fromEntity(Movement movement) {
        return new MovementDTO(
                movement.getId(),
                movement.getAmount(),
                movement.getDate(),
                movement.getDescription(),
                movement.getCategory().getId(),
                movement.getCategory().getName(),
                movement.getCategory().getType(),
                movement.getUser().getId()
        );
    }
}
