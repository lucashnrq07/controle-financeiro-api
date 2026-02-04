package com.lucas.controle_financeiro_api.service;

import com.lucas.controle_financeiro_api.domain.entities.Category;
import com.lucas.controle_financeiro_api.domain.entities.Movement;
import com.lucas.controle_financeiro_api.domain.entities.RecurringMovement;
import com.lucas.controle_financeiro_api.domain.enums.CategoryType;
import com.lucas.controle_financeiro_api.exceptions.application.CategoryNotFoundException;
import com.lucas.controle_financeiro_api.repositories.CategoryRepository;
import com.lucas.controle_financeiro_api.repositories.MovementRepository;
import com.lucas.controle_financeiro_api.repositories.RecurringMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RecurringMovementScheduler {

    private final RecurringMovementRepository repository;
    private final MovementService movementService;
    private final CategoryRepository categoryRepository;
    private final MovementRepository movementRepository;

    @Scheduled(cron = "0 0 2 * * ?")
    public void createAutomaticMovement(RecurringMovement r) {

        if (!r.getActive()) return;

        String categoryName;

        if (r.getCategory().equals(CategoryType.ENTRADA)) {
            categoryName = "DEPÓSITO AUTOMÁTICO";
        } else {
            categoryName = "RETIRADA AUTOMÁTICA";
        }

        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new CategoryNotFoundException(categoryName));

        Movement movement = new Movement();
        movement.setDescription(r.getDescription());
        movement.setAmount(r.getAmount());
        movement.setDate(LocalDate.now());
        movement.setCategory(category);
        movement.setUser(r.getUser());

        movementRepository.save(movement);
    }
}
