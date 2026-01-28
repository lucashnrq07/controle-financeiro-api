package com.lucas.controle_financeiro_api.repositories;

import com.lucas.controle_financeiro_api.domain.entities.Movement;
import com.lucas.controle_financeiro_api.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {

    List<Movement> findByUserId(Long userId);

    void deleteByGoalId(Long goalId);

    @Modifying
    @Query("UPDATE Movement m SET m.goal = null WHERE m.goal.id = :goalId")
    void detachGoal(@Param("goalId") Long goalId);

    List<Movement> findByUser(User user);
}
