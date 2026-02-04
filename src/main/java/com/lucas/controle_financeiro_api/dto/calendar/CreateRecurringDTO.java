package com.lucas.controle_financeiro_api.dto.calendar;

import com.lucas.controle_financeiro_api.domain.enums.Frequency;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Schema(name = "CreateRecurringDTO", description = "DTO para criação de movimentação automática (recorrente)")
public record CreateRecurringDTO(

        @NotBlank(message = "Descrição é obrigatória")
        @Size(max = 120, message = "Descrição deve ter no máximo 120 caracteres")
        @Schema(description = "Descrição da movimentação automática", example = "Salário")
        String description,

        @NotNull(message = "Valor é obrigatório")
        @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
        @Digits(integer = 10, fraction = 2, message = "Valor inválido")
        @Schema(description = "Valor da movimentação (sempre positivo)", example = "3500.00")
        BigDecimal amount,

        @NotNull(message = "Categoria é obrigatória")
        @Positive(message = "ID da categoria inválido")
        @Schema(description = "ID da categoria existente no sistema", example = "2")
        Long categoryId,

        @NotNull(message = "Frequência é obrigatória")
        @Schema(description = "Frequência da recorrência", example = "MONTHLY", allowableValues = {"MONTHLY", "WEEKLY"})
        Frequency frequency,

        @Min(value = 1, message = "Dia do mês deve ser entre 1 e 31")
        @Max(value = 31, message = "Dia do mês deve ser entre 1 e 31")
        @Schema(description = "Dia do mês (obrigatório se MONTHLY)", example = "5", nullable = true)
        Integer dayOfMonth,

        @Min(value = 1, message = "Dia da semana deve ser entre 1 (Seg) e 7 (Dom)")
        @Max(value = 7, message = "Dia da semana deve ser entre 1 (Seg) e 7 (Dom)")
        @Schema(description = "Dia da semana (1=Segunda, 7=Domingo) se WEEKLY", example = "1", nullable = true)
        Integer dayOfWeek
) {}
