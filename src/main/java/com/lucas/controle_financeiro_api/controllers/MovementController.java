package com.lucas.controle_financeiro_api.controllers;

import com.lucas.controle_financeiro_api.domain.entities.User;
import com.lucas.controle_financeiro_api.dto.movement.CreateMovementDTO;
import com.lucas.controle_financeiro_api.dto.movement.MovementResponseDTO;
import com.lucas.controle_financeiro_api.dto.movement.UpdateMovementDTO;
import com.lucas.controle_financeiro_api.service.MovementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
@RestController
@RequestMapping("/movements")
@Tag(name = "Movement", description = "Gerenciamento de movimentações financeiras")
public class MovementController {

    private final MovementService movementService;

    // CREATE
    @Operation(summary = "Criar uma nova movimentação financeira")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Movimentação criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário ou categoria não encontrados")
    })
    @PostMapping
    public ResponseEntity<MovementResponseDTO> createMovement(
            @RequestBody @Valid CreateMovementDTO dto,
            @AuthenticationPrincipal User user) {

        return ResponseEntity.ok(movementService.createMovement(dto, user));
    }


    // LIST BY USER
    @Operation(summary = "Listar todas as movimentações de um usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping
    public ResponseEntity<List<MovementResponseDTO>> listMovements(
            @Parameter(description = "ID do usuário", example = "1")
            @AuthenticationPrincipal User user) {

        return ResponseEntity.ok(movementService.listMovements(user));
    }

    // UPDATE
    @Operation(summary = "Atualizar uma movimentação existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Movimentação atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Movimentação ou categoria não encontradas")
    })
    @PutMapping("/{movementId}")
    public ResponseEntity<MovementResponseDTO> updateMovement(
            @Parameter(description = "ID da movimentação", example = "10")
            @PathVariable Long movementId,
            @RequestBody UpdateMovementDTO dto,
            @AuthenticationPrincipal User user) {

        return ResponseEntity.ok(movementService.updateMovement(movementId, dto, user));
    }

    // DELETE
    @Operation(summary = "Deletar uma movimentação pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Movimentação deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Movimentação não encontrada")
    })
    @DeleteMapping("/{movementId}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID da movimentação", example = "10")
            @PathVariable Long movementId,
            @AuthenticationPrincipal User user) {

        movementService.delete(movementId, user);
        return ResponseEntity.noContent().build();
    }

    // FIND BY ID
    @Operation(summary = "Buscar uma movimentação pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Movimentação encontrada"),
            @ApiResponse(responseCode = "404", description = "Movimentação não encontrada")
    })
    @GetMapping("/{movementId}")
    public ResponseEntity<MovementResponseDTO> findById(
            @Parameter(description = "ID da movimentação", example = "10")
            @PathVariable Long movementId,
            @AuthenticationPrincipal User user) {

        return ResponseEntity.ok(
                MovementResponseDTO.fromEntity(movementService.findById(movementId, user))
        );
    }
}
