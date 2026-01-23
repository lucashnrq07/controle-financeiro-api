package com.lucas.controle_financeiro_api.dto.category;

import com.lucas.controle_financeiro_api.domain.entities.Category;
import com.lucas.controle_financeiro_api.domain.enums.CategoryType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO de resposta de categoria")
public record CategoryResponseDTO(

        @Schema(description = "Id da categoria", example = "1")
        Long id,

        @Schema(description = "Nome da categoria", example = "Alimentação")
        String name,

        @Schema(description = "Tipo da categoria (ENTRADA ou SAIDA)", example = "SAIDA")
        CategoryType type
) {
    public static CategoryResponseDTO fromEntity(Category category) {
        return new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getType()
        );
    }
}
