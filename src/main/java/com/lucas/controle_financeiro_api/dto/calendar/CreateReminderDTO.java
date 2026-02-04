package com.lucas.controle_financeiro_api.dto.calendar;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Schema(name = "CreateReminderDTO", description = "DTO para criação de lembrete no calendário")
public record CreateReminderDTO(

        @NotBlank(message = "Título é obrigatório")
        @Size(max = 100, message = "Título deve ter no máximo 100 caracteres")
        @Schema(description = "Título do lembrete", example = "Pagar internet")
        String title,

        @Size(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
        @Schema(description = "Descrição opcional do lembrete", example = "Plano fibra 600mb")
        String description,

        @NotNull(message = "Data do lembrete é obrigatória")
        @FutureOrPresent(message = "Data do lembrete não pode estar no passado")
        @Schema(description = "Data do lembrete", example = "2026-02-10")
        LocalDate reminderDate
) {}
