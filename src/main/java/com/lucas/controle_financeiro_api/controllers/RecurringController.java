package com.lucas.controle_financeiro_api.controllers;

import com.lucas.controle_financeiro_api.domain.entities.User;
import com.lucas.controle_financeiro_api.dto.calendar.CreateRecurringDTO;
import com.lucas.controle_financeiro_api.dto.calendar.RecurringResponseDTO;
import com.lucas.controle_financeiro_api.service.RecurringMovementService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/recurring")
@RequiredArgsConstructor
public class RecurringController {

    private final RecurringMovementService service;

    @PostMapping
    public RecurringResponseDTO create(@RequestBody CreateRecurringDTO dto, @AuthenticationPrincipal User user) {
        return service.create(dto);
    }

    @GetMapping
    public List<CreateRecurringDTO> list(@AuthenticationPrincipal User user) {
        return service.list();
    }

    @PutMapping("/{id}/toggle")
    public void toggle(@AuthenticationPrincipal User user) {
        service.toggle(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@AuthenticationPrincipal User user) {
        service.delete(user);
    }
}
