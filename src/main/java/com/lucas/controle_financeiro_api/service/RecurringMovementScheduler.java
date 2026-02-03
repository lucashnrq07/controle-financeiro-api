package com.lucas.controle_financeiro_api.service;

import com.lucas.controle_financeiro_api.domain.entities.RecurringMovement;
import com.lucas.controle_financeiro_api.domain.enums.Frequency;
import com.lucas.controle_financeiro_api.repositories.RecurringMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecurringMovementScheduler {

    private final RecurringMovementRepository repo;
    private final MovementService movementService;

    @Scheduled(cron = "0 0 2 * * ?")
    public void processRecurringMovements() {

        LocalDate today = LocalDate.now();
        int dayOfMonth = today.getDayOfMonth();
        int dayOfWeek = today.getDayOfWeek().getValue();

        List<RecurringMovement> list = repo.findByActiveTrue();

        for (RecurringMovement r : list) {

            boolean shouldGenerate = false;

            if (r.getFrequency() == Frequency.MONTHLY && r.getDayOfMonth() == dayOfMonth)
                shouldGenerate = true;

            if (r.getFrequency() == Frequency.WEEKLY && r.getDayOfWeek() == dayOfWeek)
                shouldGenerate = true;

            if (shouldGenerate && (r.getLastGenerated() == null || !r.getLastGenerated().isEqual(today))) {

                movementService.createAutomaticMovement(r);
                r.setLastGenerated(today);
                repo.save(r);
            }
        }
    }
}
