package com.lucas.controle_financeiro_api.controllers;

import com.lucas.controle_financeiro_api.dto.MovementDTO;
import com.lucas.controle_financeiro_api.dto.UpdateMovementDTO;
import com.lucas.controle_financeiro_api.service.MovementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/movements")
@Tag(name = "Movement", description = "Gerenciamento de movimentações financeiras")
public class MovementController {

    @Autowired
    private MovementService movementService;


    // CREATE MOVEMENT
    @Operation(summary = "Criar uma nova movimentação")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Movimentação criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos (ex: valor negativo)"),
            @ApiResponse(responseCode = "404", description = "Usuário ou categoria não encontrados")
    })
    @PostMapping
    public ResponseEntity<MovementDTO> createMovement(@RequestBody @Valid MovementDTO dto) {
        MovementDTO createdMovement = movementService.createMovement(dto);
        return ResponseEntity.status(201).body(createdMovement);
    }


    // LIST MOVEMENTS BY USER
    @Operation(summary = "Listar todas as movimentações de um usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de movimentações retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MovementDTO>> listMovements(@PathVariable Long userId) {
        List<MovementDTO> movements = movementService.listMovements(userId);
        return ResponseEntity.ok(movements);
    }


    // UPDATE MOVEMENT
    @Operation(summary = "Atualizar uma movimentação")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Movimentação atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou categoria inexistente"),
            @ApiResponse(responseCode = "404", description = "Movimentação não encontrada")
    })
    @PutMapping("/{movementId}")
    public ResponseEntity<MovementDTO> updateMovement(@PathVariable Long movementId, @RequestBody UpdateMovementDTO dto) {
        MovementDTO updatedMovement = movementService.updateMovement(movementId, dto);
        return ResponseEntity.ok(updatedMovement);
    }


    // CALCULATE USER BALANCE
    @Operation(summary = "Calcular o saldo do usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Saldo calculado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/user/{userId}/balance")
    public ResponseEntity<BigDecimal> calculateBalance(@PathVariable Long userId) {
        BigDecimal balance = movementService.calculateUserBalance(userId);
        return ResponseEntity.ok(balance);
    }

}
