package com.lucas.controle_financeiro_api.dto.calendar;

import java.time.LocalDate;

public record ReminderDTO(
        Long id,
        String title,
        String description,
        LocalDate reminderDate
) {}
