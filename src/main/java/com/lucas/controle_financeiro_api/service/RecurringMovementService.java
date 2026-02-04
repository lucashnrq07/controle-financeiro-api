package com.lucas.controle_financeiro_api.service;

import com.lucas.controle_financeiro_api.domain.entities.RecurringMovement;
import com.lucas.controle_financeiro_api.domain.entities.User;
import com.lucas.controle_financeiro_api.dto.calendar.RecurringDTO;
import com.lucas.controle_financeiro_api.repositories.RecurringMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecurringMovementService {

    private final RecurringMovementRepository repo;
    private final UserService userService;

    public RecurringDTO create(RecurringDTO dto) {
        User user = userService.getAuthenticatedUser();

        RecurringMovement r = new RecurringMovement();
        r.setDescription(dto.description());
        r.setAmount(dto.amount());
        r.setCategory(dto.category());
        r.setFrequency(dto.frequency());
        r.setDayOfMonth(dto.dayOfMonth());
        r.setDayOfWeek(dto.dayOfWeek());
        r.setUser(user);

        repo.save(r);

        return dto;
    }

    public List<RecurringDTO> list() {
        User user = userService.getAuthenticatedUser();

        return repo.findByUser(user).stream()
                .map(r -> new RecurringDTO(
                        r.getId(), r.getDescription(), r.getAmount(), r.getCategory(),
                        r.getFrequency(), r.getDayOfMonth(), r.getDayOfWeek(), r.getActive()))
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
