package com.lucas.controle_financeiro_api.service;

import com.lucas.controle_financeiro_api.domain.entities.Category;
import com.lucas.controle_financeiro_api.domain.entities.Goal;
import com.lucas.controle_financeiro_api.domain.entities.Movement;
import com.lucas.controle_financeiro_api.domain.entities.User;
import com.lucas.controle_financeiro_api.dto.GoalDTO;
import com.lucas.controle_financeiro_api.exceptions.*;
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
import java.util.stream.Collectors;

@Service
public class GoalService {

    @Autowired
    private MovementService movementService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GoalRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MovementRepository movementRepository;

    // CREATE GOAL
    public GoalDTO createGoal(GoalDTO dto) {
        User user = this.userRepository.findById(dto.userId())
                .orElseThrow(() -> new UserNotFoundException(dto.userId()));

        Goal goal = this.repository.save(
                new Goal(null, dto.name(), dto.desiredAmount(), BigDecimal.ZERO, user)
        );

        return GoalDTO.fromEntity(goal);
    }

    // UPDATE GOAL
    public GoalDTO updateGoal(Long goalId, GoalDTO dto){
        Goal goal = repository.findByIdAndUserId(goalId, dto.userId())
                .orElseThrow(() -> new GoalNotFoundException(goalId));

        // update name
        if (dto.name() != null) {
            goal.setName(dto.name());
        }

        // update desired amount
        if (dto.desiredAmount() != null) {
            goal.setDesiredAmount(dto.desiredAmount());
        }

        this.repository.save(goal);
        return GoalDTO.fromEntity(goal);
    }

    // DELETE GOAL
    @Transactional
    public void deleteGoal(Long goalId, Long userId) {

        Goal goal = repository.findByIdAndUserId(goalId, userId)
                .orElseThrow(() -> new GoalNotFoundException(goalId));

        BigDecimal current = goal.getCurrentAmount();

        if (current.compareTo(BigDecimal.ZERO) > 0) {
            withdrawFromGoal(goalId, current, userId);
        }

        movementRepository.detachGoal(goalId);

        repository.delete(goal);
    }

    // DEPOSIT INTO GOAL
    @Transactional
    public Movement depositIntoGoal(Long goalId, BigDecimal amount, Long userId) {
        Goal goal = repository.findByIdAndUserId(goalId, userId)
                .orElseThrow(() -> new GoalNotFoundException(goalId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        // Categoria específica para depósitos em metas
        Category category = categoryRepository.findByName("DEPÓSITO EM META")
                .orElseThrow(() -> new CategoryNotFoundException("DEPÓSITO EM META"));

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException(amount);
        }

        // Criando o movement
        Movement mov = new Movement();
        mov.setAmount(amount);
        mov.setDate(LocalDate.now());
        mov.setDescription("Depósito na meta: " + goal.getName());
        mov.setUser(user);
        mov.setCategory(category);
        mov.setGoal(goal);

        movementRepository.save(mov);

        // Atualiza o valor atual da meta
        goal.setCurrentAmount(goal.getCurrentAmount().add(amount));
        repository.save(goal);

        return mov;
    }

    // WITHDRAW FROM GOAL
    @Transactional
    public Movement withdrawFromGoal(Long goalId, BigDecimal amount, Long userId) {
        Goal goal = repository.findByIdAndUserId(goalId, userId)
                .orElseThrow(() -> new GoalNotFoundException(goalId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Category category = categoryRepository.findByName("RETIRADA DE META")
                .orElseThrow(() -> new CategoryNotFoundException("RETIRADA DE META"));

        // Verificar se há dinheiro suficiente
        if (goal.getCurrentAmount().compareTo(amount) < 0) {
            throw new InsufficientGoalBalanceException(goalId, amount);
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException(amount);
        }

        // Movement de ENTRADA do valor retirado
        Movement mov = new Movement();
        mov.setAmount(amount);   // positivo, ENTRADA
        mov.setDate(LocalDate.now());
        mov.setDescription("Retirada da meta: " + goal.getName());
        mov.setUser(user);
        mov.setCategory(category);
        mov.setGoal(null);

        movementRepository.save(mov);

        // Atualiza a meta
        goal.setCurrentAmount(goal.getCurrentAmount().subtract(amount));
        repository.save(goal);

        return mov;
    }

    // LIST GOALS
    public List<GoalDTO> goals(Long userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        List<Goal> goals = this.repository.findAllByUserId(userId);

        return goals.stream()
                .map(GoalDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // FIND BY ID
    public Goal findById(Long goalId, Long userId) {
        return repository.findByIdAndUserId(goalId, userId)
                .orElseThrow(() -> new GoalNotFoundException(goalId));
    }
}
