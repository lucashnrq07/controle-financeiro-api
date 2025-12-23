package com.lucas.controle_financeiro_api.controllers;

import com.lucas.controle_financeiro_api.dto.movement.MovementDTO;
import com.lucas.controle_financeiro_api.service.MovementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/movements")
@RequiredArgsConstructor
@Tag(name = "Movement", description = "Gerenciamento de movimentações financeiras")
public class MovementController {

    private final MovementService movementService;

    // CREATE MOVEMENT
    @Operation(summary = "Criar uma nova movimentação")
    @PostMapping
    public ResponseEntity<MovementDTO> createMovement(@RequestBody MovementDTO dto) {
        MovementDTO createdMovement = movementService.createMovement(dto);
        return ResponseEntity.status(201).body(createdMovement);
    }

    // LIST MOVEMENTS BY USER
    @Operation(summary = "Listar todas as movimentações de um usuário")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MovementDTO>> listMovements(@PathVariable Long userId) {
        List<MovementDTO> movements = movementService.listMovements(userId);
        return ResponseEntity.ok(movements);
    }

    // UPDATE MOVEMENT
    @Operation(summary = "Atualizar uma movimentação")
    @PutMapping("/{movementId}")
    public ResponseEntity<MovementDTO> updateMovement(
            @PathVariable Long movementId,
            @RequestBody MovementDTO dto) {

        MovementDTO updatedMovement = movementService.updateMovement(movementId, dto);
        return ResponseEntity.ok(updatedMovement);
    }

    // CALCULATE USER BALANCE
    @Operation(summary = "Calcular o saldo do usuário")
    @GetMapping("/user/{userId}/balance")
    public ResponseEntity<BigDecimal> calculateBalance(@PathVariable Long userId) {
        BigDecimal balance = movementService.calculateUserBalance(userId);
        return ResponseEntity.ok(balance);
    }
}
