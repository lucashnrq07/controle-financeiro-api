package com.lucas.controle_financeiro_api.service;

import com.lucas.controle_financeiro_api.domain.entities.Category;
import com.lucas.controle_financeiro_api.domain.entities.RecurringMovement;
import com.lucas.controle_financeiro_api.domain.entities.User;
import com.lucas.controle_financeiro_api.dto.calendar.CreateRecurringDTO;
import com.lucas.controle_financeiro_api.dto.calendar.RecurringResponseDTO;
import com.lucas.controle_financeiro_api.exceptions.application.CategoryNotFoundException;
import com.lucas.controle_financeiro_api.repositories.CategoryRepository;
import com.lucas.controle_financeiro_api.repositories.RecurringMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecurringMovementService {

    private final RecurringMovementRepository repo;
    private final CategoryRepository categoryRepository;

    // CREATE
    public RecurringResponseDTO create(CreateRecurringDTO dto, User user) {

        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException(dto.categoryId()));

        RecurringMovement r = new RecurringMovement();
        r.setDescription(dto.description());
        r.setAmount(dto.amount());
        r.setCategory(category);
        r.setFrequency(dto.frequency());
        r.setDayOfMonth(dto.dayOfMonth());
        r.setDayOfWeek(dto.dayOfWeek());
        r.setUser(user);
        r.setActive(true);

        repo.save(r);

        return RecurringResponseDTO.fromEntity(r);
    }

    // LIST
    public List<RecurringResponseDTO> list(User user) {
        return repo.findByUser(user)
                .stream()
                .map(RecurringResponseDTO::fromEntity)
                .toList();
    }

    // TOGGLE
    public void toggle(Long recurringId, User user) {

        RecurringMovement r = repo.findById(recurringId)
                .orElseThrow(() -> new RuntimeException("Movimentação recorrente não encontrada"));

        if (!r.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Acesso negado");
        }

        r.setActive(!r.getActive());
        repo.save(r);
    }

    // DELETE
    public void delete(Long recurringId, User user) {

        RecurringMovement r = repo.findById(recurringId)
                .orElseThrow(() -> new RuntimeException("Movimentação recorrente não encontrada"));

        if (!r.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Acesso negado");
        }

        repo.delete(r);
    }
}
