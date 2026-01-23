package com.lucas.controle_financeiro_api.controllers;

import com.lucas.controle_financeiro_api.dto.GoalDTO;
import com.lucas.controle_financeiro_api.dto.GoalMovementDTO;
import com.lucas.controle_financeiro_api.dto.CreateMovementDTO;
import com.lucas.controle_financeiro_api.dto.MovementResponseDTO;
import com.lucas.controle_financeiro_api.service.GoalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/goals")
@Tag(name = "Goals", description = "Operações relacionadas às metas financeiras do usuário")
public class GoalController {

    private final GoalService service;

    public GoalController(GoalService service) {
        this.service = service;
    }

    // CREATE NEW GOAL
    @Operation(summary = "Criar uma nova meta")
    @ApiResponse(responseCode = "201", description = "Meta criada com sucesso")
    @PostMapping
    public ResponseEntity<GoalDTO> createGoal(@RequestBody @Valid GoalDTO dto) {
        GoalDTO createdGoal = service.createGoal(dto);

        URI location = URI.create("/goals/" + createdGoal.id());
        return ResponseEntity.created(location).body(createdGoal);
    }

    // LIST ALL GOALS
    @Operation(summary = "Listar metas do usuário")
    @ApiResponse(responseCode = "200", description = "Lista de metas retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<GoalDTO>> listGoals(
            @Parameter(description = "ID do usuário", example = "1")
            @RequestParam Long userId
    ) {
        return ResponseEntity.ok(service.goals(userId));
    }

    // UPDATE GOAL
    @Operation(summary = "Atualizar uma meta")
    @ApiResponse(responseCode = "200", description = "Meta atualizada com sucesso")
    @PutMapping("/{goalId}")
    public ResponseEntity<GoalDTO> updateGoal(
            @Parameter(description = "ID da meta", example = "1")
            @PathVariable Long goalId,
            @RequestBody @Valid GoalDTO dto
    ) {
        return ResponseEntity.ok(service.updateGoal(goalId, dto));
    }

    // DELETE GOAL
    @Operation(summary = "Deletar uma meta")
    @ApiResponse(responseCode = "204", description = "Meta removida com sucesso")
    @DeleteMapping("/{goalId}")
    public ResponseEntity<Void> deleteGoal(
            @Parameter(description = "ID da meta", example = "1")
            @PathVariable Long goalId,
            @Parameter(description = "ID do usuário", example = "3")
            @RequestParam Long userId
    ) {
        service.deleteGoal(goalId, userId);
        return ResponseEntity.noContent().build();
    }

    // DEPOSIT INTO A GOAL
    @Operation(summary = "Depositar dinheiro na meta")
    @ApiResponse(responseCode = "200", description = "Depósito realizado com sucesso")
    @PostMapping("/{goalId}/deposit")
    public ResponseEntity<MovementResponseDTO> deposit(
            @Parameter(description = "ID da meta", example = "1")
            @PathVariable Long goalId,
            @RequestBody @Valid GoalMovementDTO dto
    ) {
        return ResponseEntity.ok(
                MovementResponseDTO.fromEntity(
                        service.depositIntoGoal(goalId, dto.amount(), dto.userId())
                )
        );
    }

    // WITHDRAW OF A GOAL
    @Operation(summary = "Retirar dinheiro da meta")
    @ApiResponse(responseCode = "200", description = "Retirada realizada com sucesso")
    @PostMapping("/{goalId}/withdraw")
    public ResponseEntity<MovementResponseDTO> withdraw(
            @Parameter(description = "ID da meta", example = "1")
            @PathVariable Long goalId,
            @RequestBody @Valid GoalMovementDTO dto
    ) {
        return ResponseEntity.ok(
                MovementResponseDTO.fromEntity(
                        service.withdrawFromGoal(goalId, dto.amount(), dto.userId())
                )
        );
    }
}
