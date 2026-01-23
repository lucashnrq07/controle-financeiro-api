package com.lucas.controle_financeiro_api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lucas.controle_financeiro_api.domain.entities.Movement;
import com.lucas.controle_financeiro_api.domain.enums.CategoryType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "DTO de resposta de movimentação financeira")
public record MovementResponseDTO(

        @Schema(description = "ID da movimentação", example = "10")
        Long id,

        @Schema(description = "Valor da movimentação", example = "150.75")
        BigDecimal amount,

        @JsonFormat(pattern = "dd/MM/yyyy")
        @Schema(description = "Data da movimentação", example = "23/12/2025")
        LocalDate date,

        @Schema(description = "Descrição da movimentação", example = "Compra no supermercado")
        String description,

        @Schema(description = "ID da categoria", example = "5")
        Long categoryId,

        @Schema(description = "Nome da categoria", example = "Alimentação")
        String categoryName,

        @Schema(description = "Tipo da categoria", example = "EXPENSE")
        CategoryType type,

        @Schema(description = "ID do usuário dono da movimentação", example = "1")
        Long userId
) {
    public static MovementResponseDTO fromEntity(Movement movement) {
        return new MovementResponseDTO(
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
