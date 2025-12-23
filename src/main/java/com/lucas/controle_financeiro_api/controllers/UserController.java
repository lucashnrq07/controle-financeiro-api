package com.lucas.controle_financeiro_api.controllers;

import com.lucas.controle_financeiro_api.dto.UserDTO;
import com.lucas.controle_financeiro_api.dto.UserResponseDTO;
import com.lucas.controle_financeiro_api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Tag(name = "User", description = "API de gerenciamento de usuários")
public class UserController {

    @Autowired
    private UserService userService;

    // CREATE USER
    @Operation(summary = "Criar um novo usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "Email já cadastrado")
    })
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserDTO dto) {
        UserResponseDTO createdUser = userService.createUser(dto);
        return ResponseEntity.status(201).body(createdUser);
    }
}
