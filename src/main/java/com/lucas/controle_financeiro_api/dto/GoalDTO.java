package com.lucas.controle_financeiro_api.dto;

import com.lucas.controle_financeiro_api.domain.entities.Goal;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record GoalDTO(

        @Schema(description = "ID da meta", example = "1")
        Long id,

        @NotBlank(message = "Nome da meta é obrigatório")
        @Schema(description = "Nome da meta", example = "Comprar um carro")
        String name,

        @NotNull
        @DecimalMin(value = "0.01", message = "Valor da meta deve ser maior que zero")
        @Schema(description = "Valor final desejado da meta", example = "5000.00")
        BigDecimal desiredAmount,

        @Schema(
                description = "Valor atual acumulado na meta",
                accessMode = Schema.AccessMode.READ_ONLY,
                example = "1500.00"
        )
        BigDecimal currentAmount,

        @NotNull
        @Schema(description = "ID do usuário dono da meta", example = "3")
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
