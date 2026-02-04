package com.lucas.controle_financeiro_api.dto.calendar;

import com.lucas.controle_financeiro_api.domain.enums.Frequency;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CreateRecurringDTO", description = "DTO para criação de movimentação automática (recorrente)")
public record CreateRecurringDTO(

        @Schema(description = "Descrição da movimentação automática", example = "Salário")
        String description,

        @Schema(description = "Valor da movimentação (sempre positivo)", example = "3500.00")
        BigDecimal amount,

        @Schema(description = "ID da categoria existente no sistema", example = "2")
        Long categoryId,

        @Schema(description = "Frequência da recorrência", example = "MONTHLY", allowableValues = {"MONTHLY", "WEEKLY"})
        Frequency frequency,

        @Schema(description = "Dia do mês (obrigatório se MONTHLY)", example = "5", nullable = true)
        Integer dayOfMonth,

        @Schema(description = "Dia da semana (1=Segunda, 7=Domingo) se WEEKLY", example = "1", nullable = true)
        Integer dayOfWeek
) {}
