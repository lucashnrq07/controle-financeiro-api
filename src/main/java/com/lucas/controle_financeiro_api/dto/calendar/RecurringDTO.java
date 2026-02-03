package com.lucas.controle_financeiro_api.dto.calendar;

import com.lucas.controle_financeiro_api.domain.enums.CategoryType;
import com.lucas.controle_financeiro_api.domain.enums.Frequency;

import java.math.BigDecimal;

public record RecurringDTO(
        Long id,
        String description,
        BigDecimal amount,
        CategoryType type,
        Frequency frequency,
        Integer dayOfMonth,
        Integer dayOfWeek,
        Boolean active
) {}
