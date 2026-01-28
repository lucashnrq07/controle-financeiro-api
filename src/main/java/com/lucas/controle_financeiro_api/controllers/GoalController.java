package com.lucas.controle_financeiro_api.controllers;

import com.lucas.controle_financeiro_api.domain.entities.User;
import com.lucas.controle_financeiro_api.dto.goal.CreateGoalDTO;
import com.lucas.controle_financeiro_api.dto.goal.GoalMovementDTO;
import com.lucas.controle_financeiro_api.dto.goal.GoalResponseDTO;
import com.lucas.controle_financeiro_api.dto.goal.UpdateGoalDTO;
import com.lucas.controle_financeiro_api.dto.movement.MovementResponseDTO;
import com.lucas.controle_financeiro_api.service.GoalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
@RestController
@RequestMapping("/goals")
@Tag(name = "Goals", description = "Operações relacionadas às metas financeiras do usuário")
public class GoalController {

    private final GoalService service;

    // CREATE GOAL
    @Operation(summary = "Criar uma nova meta para o usuário")
    @ApiResponse(responseCode = "201", description = "Meta criada com sucesso")
    @PostMapping
    public ResponseEntity<GoalResponseDTO> createGoal(
            @Parameter(description = "ID do usuário", example = "3")
            @AuthenticationPrincipal User user,
            @RequestBody @Valid CreateGoalDTO dto
    ) {
        GoalResponseDTO goal = service.createGoal(user.getId(), dto);
        return ResponseEntity.created(URI.create("/goals/" + goal.id())).body(goal);
    }

    // LIST GOALS
    @Operation(summary = "Listar todas as metas do usuário")
    @ApiResponse(responseCode = "200", description = "Lista de metas retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<GoalResponseDTO>> listGoals(
            @Parameter(description = "ID do usuário", example = "3")
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(service.listGoals(user.getId()));
    }

    // UPDATE GOAL
    @Operation(summary = "Atualizar uma meta")
    @ApiResponse(responseCode = "200", description = "Meta atualizada com sucesso")
    @PutMapping("/{goalId}")
    public ResponseEntity<GoalResponseDTO> updateGoal(
            @Parameter(description = "ID do usuário", example = "3")
            @AuthenticationPrincipal User user,
            @Parameter(description = "ID da meta", example = "1")
            @PathVariable Long goalId,
            @RequestBody @Valid UpdateGoalDTO dto
    ) {
        return ResponseEntity.ok(service.updateGoal(goalId, user.getId(), dto));
    }

    // DELETE GOAL
    @Operation(summary = "Remover uma meta")
    @ApiResponse(responseCode = "204", description = "Meta removida com sucesso")
    @DeleteMapping("/{goalId}")
    public ResponseEntity<Void> deleteGoal(
            @Parameter(description = "ID do usuário", example = "3")
            @AuthenticationPrincipal User user,
            @Parameter(description = "ID da meta", example = "1")
            @PathVariable Long goalId
    ) {
        service.deleteGoal(goalId, user.getId());
        return ResponseEntity.noContent().build();
    }

    // DEPOSIT
    @Operation(summary = "Depositar valor na meta")
    @ApiResponse(responseCode = "200", description = "Depósito realizado com sucesso")
    @PostMapping("/{goalId}/deposit")
    public ResponseEntity<MovementResponseDTO> deposit(
            @Parameter(description = "ID do usuário", example = "3")
            @AuthenticationPrincipal User user,
            @Parameter(description = "ID da meta", example = "1")
            @PathVariable Long goalId,
            @RequestBody @Valid GoalMovementDTO dto
    ) {
        return ResponseEntity.ok(
                MovementResponseDTO.fromEntity(
                        service.depositIntoGoal(goalId, dto.amount(), user.getId())
                )
        );
    }

    // WITHDRAW
    @Operation(summary = "Retirar valor da meta")
    @ApiResponse(responseCode = "200", description = "Retirada realizada com sucesso")
    @PostMapping("/{goalId}/withdraw")
    public ResponseEntity<MovementResponseDTO> withdraw(
            @Parameter(description = "ID do usuário", example = "3")
            @AuthenticationPrincipal User user,
            @Parameter(description = "ID da meta", example = "1")
            @PathVariable Long goalId,
            @RequestBody @Valid GoalMovementDTO dto
    ) {
        return ResponseEntity.ok(
                MovementResponseDTO.fromEntity(
                        service.withdrawFromGoal(goalId, dto.amount(), user.getId())
                )
        );
    }
}
