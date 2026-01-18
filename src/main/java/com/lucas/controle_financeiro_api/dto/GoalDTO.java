package com.lucas.controle_financeiro_api.dto;

import com.lucas.controle_financeiro_api.domain.entities.Goal;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record GoalDTO(
        @Schema(
                description = "ID da movimentação"
        )
        Long id,

        @NotNull
        @Schema(
                description = "Nome da meta"
        )
        String name,

        @NotNull
        @Schema(
                description = "Valor final desejado da meta"
        )
        BigDecimal desiredAmount,

        @Schema(
                description = "Valor atual da meta"
        )
        BigDecimal currentAmount,

        @NotNull
        @Schema(
                description = "Id do usuário dono da meta"
        )
        Long userId
) {

    public static GoalDTO fromEntity(Goal goal) {
        return new GoalDTO(
                goal.getId(),
                goal.getName(),
                goal.getDesiredAmount(),
                goal.getCurrentAmount(),
                goal.getUser().getId()
        );
    }
}
