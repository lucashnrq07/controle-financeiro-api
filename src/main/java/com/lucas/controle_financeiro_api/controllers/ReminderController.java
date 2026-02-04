package com.lucas.controle_financeiro_api.controllers;

import com.lucas.controle_financeiro_api.domain.entities.User;
import com.lucas.controle_financeiro_api.dto.calendar.ReminderDTO;
import com.lucas.controle_financeiro_api.service.ReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reminders")
@RequiredArgsConstructor
public class ReminderController {

    private final ReminderService service;

    @PostMapping
    public ReminderDTO create(@RequestBody ReminderDTO dto, @AuthenticationPrincipal User user) {
        return service.create(dto);
    }

    @GetMapping
    public List<ReminderDTO> list(@AuthenticationPrincipal User user) {
        return service.list();
    }

    @DeleteMapping("/{id}")
    public void delete(@AuthenticationPrincipal User user) {
        service.delete(user);
    }
}
