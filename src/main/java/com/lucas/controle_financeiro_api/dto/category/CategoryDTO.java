package com.lucas.controle_financeiro_api.dto.category;

import com.lucas.controle_financeiro_api.domain.enums.CategoryName;
import com.lucas.controle_financeiro_api.domain.enums.CategoryType;
import jakarta.validation.constraints.NotNull;

public record CategoryDTO(

   @NotNull
   CategoryName name,

   @NotNull
   CategoryType type
) {}
