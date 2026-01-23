package com.lucas.controle_financeiro_api.repositories;

import com.lucas.controle_financeiro_api.domain.entities.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findAllByUserId(Long userId);

    Optional<Goal> findByIdAndUserId(Long goalId, Long userId);
}
