package com.lucas.controle_financeiro_api.dto.category;

import com.lucas.controle_financeiro_api.domain.entities.Category;
import com.lucas.controle_financeiro_api.domain.entities.User;
import com.lucas.controle_financeiro_api.domain.enums.CategoryType;
import jakarta.validation.constraints.NotNull;

public record CategoryDTO(

   @NotNull
   String name,

   @NotNull
   CategoryType type,

   @NotNull
   User user
) {

    public static CategoryDTO fromEntity(Category category) {
        CategoryDTO dto = new CategoryDTO(
                category.getName(),
                category.getType(),
                category.getUser()
        );
        return dto;
    }
}
