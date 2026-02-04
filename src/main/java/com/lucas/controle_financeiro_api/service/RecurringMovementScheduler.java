package com.lucas.controle_financeiro_api.service;

import com.lucas.controle_financeiro_api.domain.entities.RecurringMovement;
import com.lucas.controle_financeiro_api.repositories.RecurringMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecurringMovementScheduler {

    private final RecurringMovementRepository repository;
    private final MovementService movementService;

    @Scheduled(cron = "0 0 2 * * ?")
    public void processRecurringMovements() {

        List<RecurringMovement> recurrences = repository.findAllByActiveTrue();

        for (RecurringMovement r : recurrences) {
            movementService.createAutomaticMovement(r);
        }
    }
}
