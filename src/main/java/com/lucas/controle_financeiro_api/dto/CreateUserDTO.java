package com.lucas.controle_financeiro_api.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateUserDTO(
   @NotBlank
   String name,

   @NotBlank
   String email,

   @NotBlank
   String password
) {}
