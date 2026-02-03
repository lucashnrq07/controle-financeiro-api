package com.lucas.controle_financeiro_api.repositories;

import com.lucas.controle_financeiro_api.domain.entities.RecurringMovement;
import com.lucas.controle_financeiro_api.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecurringMovementRepository extends JpaRepository<RecurringMovement, Long> {
    List<RecurringMovement> findByUser(User user);
    List<RecurringMovement> findByActiveTrue();
}
