package com.lucas.controle_financeiro_api.controllers;

import com.lucas.controle_financeiro_api.domain.entities.User;
import com.lucas.controle_financeiro_api.dto.calendar.RecurringDTO;
import com.lucas.controle_financeiro_api.service.RecurringMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recurring")
@RequiredArgsConstructor
public class RecurringController {

    private final RecurringMovementService service;

    @PostMapping
    public RecurringDTO create(@RequestBody RecurringDTO dto, @AuthenticationPrincipal User user) {
        return service.create(dto);
    }

    @GetMapping
    public List<RecurringDTO> list(@AuthenticationPrincipal User user) {
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
