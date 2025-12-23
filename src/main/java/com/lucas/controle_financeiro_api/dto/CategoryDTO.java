package com.lucas.controle_financeiro_api.dto;

import com.lucas.controle_financeiro_api.domain.entities.Category;
import com.lucas.controle_financeiro_api.domain.enums.CategoryType;
import jakarta.validation.constraints.NotNull;

public record CategoryDTO(

   @NotNull
   String name,

   @NotNull
   CategoryType type
) {

    public static CategoryDTO fromEntity(Category category) {
        CategoryDTO dto = new CategoryDTO(
                category.getName(),
                category.getType()
        );
        return dto;
    }
}
