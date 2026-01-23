package com.lucas.controle_financeiro_api.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO para retorno da criação de usuários")
public record UserResponseDTO(
   @NotBlank
   String name,

   @NotBlank
   @Email
   String email
) {}
