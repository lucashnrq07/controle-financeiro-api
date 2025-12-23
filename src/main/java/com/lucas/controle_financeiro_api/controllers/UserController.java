package com.lucas.controle_financeiro_api.controllers;

import com.lucas.controle_financeiro_api.dto.UserDTO;
import com.lucas.controle_financeiro_api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(name = "/users")
@Tag(name = "User", description = "API de gerenciamento de usuários")
public class UserController {

    @Autowired
    private UserService userService;

    // CREATE USER
    @Operation(summary = "Criar um novo usuário")
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO dto) {
        UserDTO createdUser = userService.createUser(dto);
        return ResponseEntity.status(201).body(createdUser);
    }
}
