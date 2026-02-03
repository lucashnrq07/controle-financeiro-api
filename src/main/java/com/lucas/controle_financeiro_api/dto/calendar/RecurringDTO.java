package com.lucas.controle_financeiro_api.dto.calendar;

import com.lucas.controle_financeiro_api.domain.entities.Category;
import com.lucas.controle_financeiro_api.domain.enums.Frequency;

import java.math.BigDecimal;

public record RecurringDTO(
        Long id,
        String description,
        BigDecimal amount,
        Category category,
        Frequency frequency,
        Integer dayOfMonth,
        Integer dayOfWeek,
        Boolean active
) {}
