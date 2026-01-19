package com.lucas.controle_financeiro_api.controllers;

import com.lucas.controle_financeiro_api.dto.GoalDTO;
import com.lucas.controle_financeiro_api.dto.GoalMovementDTO;
import com.lucas.controle_financeiro_api.dto.MovementDTO;
import com.lucas.controle_financeiro_api.service.GoalService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goals")
@Tag(name = "Goals", description = "Gerenciamento de metas")
public class GoalController {

    @Autowired
    private GoalService service;

    // CREATE GOAL
    @PostMapping
    public ResponseEntity<GoalDTO> createGoal(@RequestBody @Valid GoalDTO dto) {
        GoalDTO createdGoal = service.createGoal(dto);
        return ResponseEntity.status(201).body(createdGoal);
    }

    // LIST GOALS
    @GetMapping
    public ResponseEntity<List<GoalDTO>> listGoals(Long userId) {
        List<GoalDTO> goals = service.goals(userId);
        return ResponseEntity.ok(goals);
    }

    @PutMapping("/{goalId}")
    public ResponseEntity<GoalDTO> updateGoal(@PathVariable Long goalId, @RequestBody GoalDTO dto) {
        GoalDTO updatedGoal = service.updateGoal(goalId, dto);
        return ResponseEntity.ok(updatedGoal);
    }

    @DeleteMapping("/{goalId}")
    public ResponseEntity<Void> deleteGoal(@PathVariable Long goalId) {
        this.service.delete(goalId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{goalId}/deposit")
    public ResponseEntity<MovementDTO> deposit(@PathVariable Long goalId, @RequestBody GoalMovementDTO dto) {
        MovementDTO updated = MovementDTO.fromEntity(service.depositIntoGoal(goalId, dto.amount(), dto.user().getId()));
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{goalId}/withdraw")
    public ResponseEntity<MovementDTO> withdraw(@PathVariable Long goalId, @RequestBody GoalMovementDTO dto) {
        MovementDTO updated = MovementDTO.fromEntity(service.withdrawFromGoal(goalId, dto.amount(), dto.user().getId()));
        return ResponseEntity.ok(updated);
    }
}
