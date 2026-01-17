package com.lucas.controle_financeiro_api.repositories;

import com.lucas.controle_financeiro_api.domain.entities.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {

    List<Movement> findByUserId(Long userId);

    List<Movement> findByGoalId(Long goalId);
}
