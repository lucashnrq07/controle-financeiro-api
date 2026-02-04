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

    public ReminderResponseDTO create(CreateReminderDTO dto, User user) {

        Reminder r = new Reminder();
        r.setTitle(dto.title());
        r.setDescription(dto.description());
        r.setReminderDate(dto.reminderDate());
        r.setUser(user);

        repo.save(r);

        return ReminderResponseDTO.fromEntity(r);
    }

    public List<ReminderResponseDTO> list(User user) {
        return repo.findByUser(user).stream()
                .map(ReminderResponseDTO::fromEntity)
                .toList();
    }

    public void delete(Long id, User user) {
        Reminder reminder = repo.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Lembrete não encontrado"));

        repo.delete(reminder);
    }
}
