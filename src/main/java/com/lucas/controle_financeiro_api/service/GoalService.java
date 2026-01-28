package com.lucas.controle_financeiro_api.service;

import com.lucas.controle_financeiro_api.domain.entities.Category;
import com.lucas.controle_financeiro_api.domain.entities.Goal;
import com.lucas.controle_financeiro_api.domain.entities.Movement;
import com.lucas.controle_financeiro_api.domain.entities.User;
import com.lucas.controle_financeiro_api.dto.goal.CreateGoalDTO;
import com.lucas.controle_financeiro_api.dto.goal.GoalResponseDTO;
import com.lucas.controle_financeiro_api.dto.goal.UpdateGoalDTO;
import com.lucas.controle_financeiro_api.exceptions.application.*;
import com.lucas.controle_financeiro_api.exceptions.login.UserNotFoundException;
import com.lucas.controle_financeiro_api.repositories.CategoryRepository;
import com.lucas.controle_financeiro_api.repositories.GoalRepository;
import com.lucas.controle_financeiro_api.repositories.MovementRepository;
import com.lucas.controle_financeiro_api.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class GoalService {

    @Autowired private UserRepository userRepository;
    @Autowired private GoalRepository repository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private MovementRepository movementRepository;

    // CREATE GOAL
    public GoalResponseDTO createGoal(Long userId, CreateGoalDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Goal goal = repository.save(
                new Goal(null, dto.name(), dto.desiredAmount(), BigDecimal.ZERO, user)
        );

        return GoalResponseDTO.fromEntity(goal);
    }

    // UPDATE GOAL
    public GoalResponseDTO updateGoal(Long goalId, Long userId, UpdateGoalDTO dto) {
        Goal goal = repository.findByIdAndUserId(goalId, userId)
                .orElseThrow(() -> new GoalNotFoundException(goalId));

        goal.setName(dto.name());
        goal.setDesiredAmount(dto.desiredAmount());

        repository.save(goal);
        return GoalResponseDTO.fromEntity(goal);
    }

    // DELETE GOAL
    @Transactional
    public void deleteGoal(Long goalId, User user) {
        Goal goal = repository.findByIdAndUserId(goalId, user.getId())
                .orElseThrow(() -> new GoalNotFoundException(goalId));

        if (goal.getCurrentAmount().compareTo(BigDecimal.ZERO) > 0) {
            withdrawFromGoal(goalId, goal.getCurrentAmount(), user);
        }

        movementRepository.detachGoal(goalId);
        repository.delete(goal);
    }

    // DEPOSIT
    @Transactional
    public Movement depositIntoGoal(Long goalId, BigDecimal amount, User user) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException(amount);
        }

        Goal goal = repository.findByIdAndUserId(goalId, user.getId())
                .orElseThrow(() -> new GoalNotFoundException(goalId));

        Category category = categoryRepository.findByName("DEPÓSITO EM META")
                .orElseThrow(() -> new CategoryNotFoundException("DEPÓSITO EM META"));

        Movement mov = new Movement();
        mov.setAmount(amount);
        mov.setDate(LocalDate.now());
        mov.setDescription("Depósito na meta: " + goal.getName());
        mov.setUser(user);
        mov.setCategory(category);
        mov.setGoal(goal);

        movementRepository.save(mov);

        goal.setCurrentAmount(goal.getCurrentAmount().add(amount));
        repository.save(goal);

        return mov;
    }

    // WITHDRAW
    @Transactional
    public Movement withdrawFromGoal(Long goalId, BigDecimal amount, User user) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException(amount);
        }

        Goal goal = repository.findByIdAndUserId(goalId, user.getId())
                .orElseThrow(() -> new GoalNotFoundException(goalId));

        if (goal.getCurrentAmount().compareTo(amount) < 0) {
            throw new InsufficientGoalBalanceException(goalId, amount);
        }

        Category category = categoryRepository.findByName("RETIRADA DE META")
                .orElseThrow(() -> new CategoryNotFoundException("RETIRADA DE META"));

        Movement mov = new Movement();
        mov.setAmount(amount);
        mov.setDate(LocalDate.now());
        mov.setDescription("Retirada da meta: " + goal.getName());
        mov.setUser(user);
        mov.setCategory(category);
        mov.setGoal(goal);

        movementRepository.save(mov);

        goal.setCurrentAmount(goal.getCurrentAmount().subtract(amount));
        repository.save(goal);

        return mov;
    }

    // LIST GOALS
    public List<GoalResponseDTO> listGoals(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return repository.findAllByUserId(userId)
                .stream()
                .map(GoalResponseDTO::fromEntity)
                .toList();
    }

    // FIND ONE
    public GoalResponseDTO findById(Long goalId, Long userId) {
        Goal goal = repository.findByIdAndUserId(goalId, userId)
                .orElseThrow(() -> new GoalNotFoundException(goalId));

        return GoalResponseDTO.fromEntity(goal);
    }
}

