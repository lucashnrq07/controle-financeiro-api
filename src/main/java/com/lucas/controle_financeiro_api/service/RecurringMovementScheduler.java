package com.lucas.controle_financeiro_api.service;

import com.lucas.controle_financeiro_api.domain.entities.RecurringMovement;
import com.lucas.controle_financeiro_api.domain.enums.Frequency;
import com.lucas.controle_financeiro_api.repositories.RecurringMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecurringMovementScheduler {

    private final RecurringMovementRepository repository;
    private final MovementService movementService;

    @Scheduled(cron = "0 0 2 * * ?", zone = "America/Sao_Paulo")
    public void processRecurringMovements() {

        LocalDate today = LocalDate.now(ZoneId.of("America/Sao_Paulo"));
        int todayDayOfMonth = today.getDayOfMonth();
        int todayDayOfWeek = today.getDayOfWeek().getValue(); // 1=Mon ... 7=Sun

        List<RecurringMovement> recurrences = repository.findAllByActiveTrue();

        for (RecurringMovement r : recurrences) {

            boolean shouldRun = false;

            if (r.getFrequency() == Frequency.MONTHLY &&
                    r.getDayOfMonth() != null &&
                    r.getDayOfMonth() == todayDayOfMonth) {
                shouldRun = true;
            }

            if (r.getFrequency() == Frequency.WEEKLY &&
                    r.getDayOfWeek() != null &&
                    r.getDayOfWeek() == todayDayOfWeek) {
                shouldRun = true;
            }

            if (shouldRun) {
                movementService.createAutomaticMovement(r);
            }
        }
    }
}
