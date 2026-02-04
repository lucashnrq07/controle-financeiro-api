package com.lucas.controle_financeiro_api.controllers;

import com.lucas.controle_financeiro_api.domain.entities.User;
import com.lucas.controle_financeiro_api.dto.calendar.CreateRecurringDTO;
import com.lucas.controle_financeiro_api.dto.calendar.RecurringResponseDTO;
import com.lucas.controle_financeiro_api.service.RecurringMovementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Movimentos Recorrentes", description = "Gerenciamento de transações automáticas (mensais ou semanais)")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/recurring")
@RequiredArgsConstructor
public class RecurringController {

    private final RecurringMovementService service;

    // CREATE
    @Operation(summary = "Criar movimentação recorrente",
            description = "Cria uma transação automática que será gerada semanal ou mensalmente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Movimentação recorrente criada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    @PostMapping
    public RecurringResponseDTO create(
            @RequestBody CreateRecurringDTO dto,
            @AuthenticationPrincipal User user) {

        return service.create(dto, user);
    }

    // LIST
    @Operation(summary = "Listar movimentações recorrentes",
            description = "Retorna todas as transações automáticas do usuário logado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    @GetMapping
    public List<RecurringResponseDTO> list(@AuthenticationPrincipal User user) {
        return service.list(user);
    }

    // TOGGLE
    @Operation(summary = "Ativar ou desativar movimentação recorrente",
            description = "Alterna o status (ativa/desativa) de uma transação automática.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Status alterado"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "404", description = "Movimentação não encontrada")
    })
    @PutMapping("/{id}/toggle")
    public void toggle(
            @Parameter(description = "ID da movimentação recorrente", example = "3")
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {

        service.toggle(id, user);
    }

    // DELETE
    @Operation(summary = "Excluir movimentação recorrente",
            description = "Remove uma transação automática definitivamente.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Movimentação removida"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "404", description = "Movimentação não encontrada")
    })
    @DeleteMapping("/{id}")
    public void delete(
            @Parameter(description = "ID da movimentação recorrente", example = "3")
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {

        service.delete(id, user);
    }
}
