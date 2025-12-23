package com.lucas.controle_financeiro_api.dto.movement;

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

    public static MovementDTO fromEntity(Movement movement) {
        MovementDTO dto = new MovementDTO(movement.getAmount(),
                movement.getDate(),
                movement.getDescription(),
                movement.getCategory().getId(),
                movement.getUser().getId()
        );
        return dto;
    }
}
