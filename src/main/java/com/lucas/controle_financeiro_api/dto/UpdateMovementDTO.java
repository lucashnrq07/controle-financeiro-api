package com.lucas.controle_financeiro_api.dto;

import com.lucas.controle_financeiro_api.domain.entities.Movement;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateMovementDTO(

        @NotNull
        @Positive
        BigDecimal amount,

        @NotNull
        LocalDate date,

        String description,

        @NotNull
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
