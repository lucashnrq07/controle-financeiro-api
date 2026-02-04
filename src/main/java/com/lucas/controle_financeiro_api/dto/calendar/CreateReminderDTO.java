package com.lucas.controle_financeiro_api.dto.calendar;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(name = "CreateReminderDTO", description = "DTO para criação de lembrete no calendário")
public record CreateReminderDTO(

        @Schema(description = "Título do lembrete", example = "Pagar internet")
        String title,

        @Schema(description = "Descrição opcional do lembrete", example = "Plano fibra 600mb")
        String description,

        @Schema(description = "Data do lembrete", example = "2026-02-10")
        LocalDate reminderDate
) {}
