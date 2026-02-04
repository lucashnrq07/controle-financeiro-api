package com.lucas.controle_financeiro_api.dto.calendar;

import com.lucas.controle_financeiro_api.domain.entities.RecurringMovement;
import com.lucas.controle_financeiro_api.domain.enums.CategoryType;
import com.lucas.controle_financeiro_api.domain.enums.Frequency;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(name = "RecurringResponseDTO", description = "Resposta de uma movimentação automática cadastrada no sistema")
public record RecurringResponseDTO(

        @Schema(description = "ID da movimentação recorrente", example = "12")
        Long id,

        @Schema(description = "Descrição definida pelo usuário", example = "Salário mensal")
        String description,

        @Schema(description = "Valor da movimentação que será gerada automaticamente", example = "3500.00")
        BigDecimal amount,

        @Schema(description = "Nome da categoria usada quando a transação automática é criada", example = "DEPÓSITO AUTOMÁTICO")
        String categoryName,

        @Schema(description = "Tipo da categoria (define se é entrada ou saída de dinheiro)", example = "ENTRADA", allowableValues = {"ENTRADA", "SAIDA"})
        CategoryType categoryType,

        @Schema(description = "Frequência da execução automática", example = "MONTHLY", allowableValues = {"MONTHLY", "WEEKLY"})
        Frequency frequency,

        @Schema(description = "Dia do mês em que a transação ocorre (usado quando frequência = MONTHLY)", example = "5", nullable = true)
        Integer dayOfMonth,

        @Schema(description = "Dia da semana da execução (1=Segunda, 7=Domingo) quando frequência = WEEKLY", example = "1", nullable = true)
        Integer dayOfWeek,

        @Schema(description = "Indica se a recorrência está ativa", example = "true")
        Boolean active

) {
    public static RecurringResponseDTO fromEntity(RecurringMovement r) {
        return new RecurringResponseDTO(
                r.getId(),
                r.getDescription(),
                r.getAmount(),
                r.getCategory().getName(),
                r.getCategory().getType(),
                r.getFrequency(),
                r.getDayOfMonth(),
                r.getDayOfWeek(),
                r.getActive()
        );
    }
}
