package com.lucas.controle_financeiro_api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lucas.controle_financeiro_api.domain.entities.Movement;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "DTO para atualização de movimentações financeiras")
public record UpdateMovementDTO(

        @Positive
        @Schema(
                description = "Valor da movimentação (sempre positivo)",
                example = "150.75"
        )
        BigDecimal amount,

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

        @Schema(
                description = "ID da categoria associada à movimentação",
                example = "3"
        )
        Long categoryId
) {

    public static UpdateMovementDTO fromEntity(Movement movement) {
        return new UpdateMovementDTO(
                movement.getAmount(),
                movement.getDate(),
                movement.getDescription(),
                movement.getCategory().getId()
        );
    }
}
