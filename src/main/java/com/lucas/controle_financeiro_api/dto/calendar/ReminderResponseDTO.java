package com.lucas.controle_financeiro_api.dto.calendar;

import com.lucas.controle_financeiro_api.domain.entities.Reminder;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(name = "ReminderResponseDTO", description = "Resposta de um lembrete criado no calendário do usuário")
public record ReminderResponseDTO(

        @Schema(description = "ID do lembrete", example = "3")
        Long id,

        @Schema(description = "Título do lembrete", example = "Pagar internet")
        String title,

        @Schema(description = "Descrição detalhada do lembrete", example = "Plano fibra 600mb")
        String description,

        @Schema(description = "Data em que o lembrete deve aparecer no calendário", example = "2026-02-10")
        LocalDate reminderDate

) {
    public static ReminderResponseDTO fromEntity(Reminder r) {
        return new ReminderResponseDTO(
                r.getId(),
                r.getTitle(),
                r.getDescription(),
                r.getReminderDate()
        );
    }
}
