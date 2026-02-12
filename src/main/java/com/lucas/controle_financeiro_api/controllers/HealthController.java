package com.lucas.controle_financeiro_api.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
@Tag(name = "Robô", description = "Endpoint para receber requisições automáticas")
public class HealthController {

    @GetMapping
    public String health() {
        return "OK";
    }
}
