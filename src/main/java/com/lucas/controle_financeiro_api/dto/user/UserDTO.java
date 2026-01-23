package com.lucas.controle_financeiro_api.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO para criação de usuários")
public record UserDTO(
   @NotBlank
   @Schema(description = "Nome do usuário", example = "Lucas")
   String name,

   @NotBlank
   @Email
   @Schema(description = "Email do usuário", example = "lucas@gmail.com")
   String email,

   @NotBlank
   @Schema(description = "Senha do usuário", example = "senha123")
   String password
) {}
