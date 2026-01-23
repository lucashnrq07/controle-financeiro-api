package com.lucas.controle_financeiro_api.dto.category;

import com.lucas.controle_financeiro_api.domain.entities.Category;
import com.lucas.controle_financeiro_api.domain.enums.CategoryType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "DTO para criação e retorno de categorias financeiras")
public record CategoryDTO(
        @Schema(
                description = "Id da categoria",
                example = "1"
        )
        Long id,

        @NotNull
        @Schema(
                description = "Nome da categoria",
                example = "Alimentação"
        )
        String name,

        @NotNull
        @Schema(
                description = "Tipo da categoria (ENTRADA ou SAIDA)",
                example = "ENTRADA"
        )
        CategoryType type
) {

    public static CategoryDTO fromEntity(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getType()
        );
    }
}
