package com.lucas.controle_financeiro_api.repositories;

import com.lucas.controle_financeiro_api.domain.entities.Reminder;
import com.lucas.controle_financeiro_api.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    List<Reminder> findByUser(User user);

    Optional<Reminder> findByIdAndUser(Long id, User user);
}
