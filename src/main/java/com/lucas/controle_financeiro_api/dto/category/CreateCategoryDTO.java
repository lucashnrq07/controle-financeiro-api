package com.lucas.controle_financeiro_api.dto.category;

import com.lucas.controle_financeiro_api.domain.enums.CategoryType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "DTO para criação de categoria")
public record CreateCategoryDTO(

        @NotBlank(message = "Nome da categoria é obrigatório")
        @Schema(description = "Nome da categoria", example = "Alimentação")
        String name,

        @NotNull(message = "Tipo da categoria é obrigatório")
        @Schema(description = "Tipo da categoria (ENTRADA ou SAIDA)", example = "SAIDA")
        CategoryType type
) {}
