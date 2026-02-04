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
    private final UserService userService;
    private final CategoryRepository categoryRepository;

    public RecurringResponseDTO create(CreateRecurringDTO dto) {

        User user = userService.getAuthenticatedUser();

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

    public List<CreateRecurringDTO> list() {
        User user = userService.getAuthenticatedUser();

        return repo.findByUser(user).stream()
                .map(r -> new CreateRecurringDTO(
                        r.getDescription(), r.getAmount(), r.getCategory().getId(),
                        r.getFrequency(), r.getDayOfMonth(), r.getDayOfWeek()))
                .toList();
    }

    public void toggle(User user) {
        RecurringMovement r = repo.findById(user.getId()).orElseThrow();
        r.setActive(!r.getActive());
        repo.save(r);
    }

    public void delete(User user) {
        repo.deleteById(user.getId());
    }
}
