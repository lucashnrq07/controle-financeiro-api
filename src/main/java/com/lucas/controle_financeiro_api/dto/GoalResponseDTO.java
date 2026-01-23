package com.lucas.controle_financeiro_api.dto;

import com.lucas.controle_financeiro_api.domain.entities.Goal;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "DTO de resposta de uma meta")
public record GoalResponseDTO(

        @Schema(description = "ID da meta", example = "1")
        Long id,

        @Schema(description = "Nome da meta", example = "Comprar um carro")
        String name,

        @Schema(description = "Valor final desejado", example = "5000.00")
        BigDecimal desiredAmount,

        @Schema(description = "Valor atual acumulado", example = "1500.00")
        BigDecimal currentAmount
) {
    public static GoalResponseDTO fromEntity(Goal goal) {
        return new GoalResponseDTO(
                goal.getId(),
                goal.getName(),
                goal.getDesiredAmount(),
                goal.getCurrentAmount()
        );
    }
}
