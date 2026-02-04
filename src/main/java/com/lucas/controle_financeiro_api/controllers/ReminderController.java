package com.lucas.controle_financeiro_api.controllers;

import com.lucas.controle_financeiro_api.domain.entities.User;
import com.lucas.controle_financeiro_api.dto.calendar.CreateReminderDTO;
import com.lucas.controle_financeiro_api.dto.calendar.ReminderResponseDTO;
import com.lucas.controle_financeiro_api.service.ReminderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Lembretes", description = "Gerenciamento de lembretes do calendário financeiro")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/reminders")
@RequiredArgsConstructor
public class ReminderController {

    private final ReminderService service;

    @Operation(summary = "Criar lembrete")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lembrete criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
    })
    @PostMapping
    public ReminderResponseDTO create(
            @RequestBody CreateReminderDTO dto,
            @AuthenticationPrincipal User user) {

        return service.create(dto, user);
    }

    @Operation(summary = "Listar lembretes do usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de lembretes retornada"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado")
    })
    @GetMapping
    public List<ReminderResponseDTO> list(@AuthenticationPrincipal User user) {
        return service.list(user);
    }

    @Operation(summary = "Deletar lembrete")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Lembrete removido"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "404", description = "Lembrete não encontrado")
    })
    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {

        service.delete(id, user);
    }
}
