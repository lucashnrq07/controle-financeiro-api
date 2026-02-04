package com.lucas.controle_financeiro_api.service;

import com.lucas.controle_financeiro_api.domain.entities.Reminder;
import com.lucas.controle_financeiro_api.domain.entities.User;
import com.lucas.controle_financeiro_api.dto.calendar.CreateReminderDTO;
import com.lucas.controle_financeiro_api.dto.calendar.ReminderResponseDTO;
import com.lucas.controle_financeiro_api.repositories.ReminderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReminderService {

    private final ReminderRepository repo;
    private final UserService userService;

    public ReminderResponseDTO create(CreateReminderDTO dto) {

        User user = userService.getAuthenticatedUser();

        Reminder r = new Reminder();
        r.setTitle(dto.title());
        r.setDescription(dto.description());
        r.setReminderDate(dto.reminderDate());
        r.setUser(user);

        repo.save(r);

        return ReminderResponseDTO.fromEntity(r);
    }

    public List<CreateReminderDTO> list() {
        User user = userService.getAuthenticatedUser();

        return repo.findByUser(user).stream()
                .map(r -> new CreateReminderDTO( r.getTitle(), r.getDescription(), r.getReminderDate()))
                .toList();
    }

    public void delete(User user) {
        repo.deleteById(user.getId());
    }
}

