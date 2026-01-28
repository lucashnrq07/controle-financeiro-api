package com.lucas.controle_financeiro_api.service;

import com.lucas.controle_financeiro_api.domain.entities.Category;
import com.lucas.controle_financeiro_api.domain.entities.Movement;
import com.lucas.controle_financeiro_api.domain.entities.User;
import com.lucas.controle_financeiro_api.domain.enums.CategoryType;
import com.lucas.controle_financeiro_api.dto.movement.CreateMovementDTO;
import com.lucas.controle_financeiro_api.dto.movement.MovementResponseDTO;
import com.lucas.controle_financeiro_api.dto.movement.UpdateMovementDTO;
import com.lucas.controle_financeiro_api.exceptions.application.CategoryNotFoundException;
import com.lucas.controle_financeiro_api.exceptions.application.MovementNotFoundException;
import com.lucas.controle_financeiro_api.exceptions.login.UserNotFoundException;
import com.lucas.controle_financeiro_api.repositories.CategoryRepository;
import com.lucas.controle_financeiro_api.repositories.MovementRepository;
import com.lucas.controle_financeiro_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MovementService {

    @Autowired
    private MovementRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    // CREATE MOVEMENT
    public MovementResponseDTO createMovement(CreateMovementDTO data, User user) {

        Category category = categoryRepository.findById(data.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException(data.categoryId()));

        Movement movement = new Movement();
        movement.setAmount(data.amount());
        movement.setDate(data.date());
        movement.setDescription(data.description());
        movement.setCategory(category);
        movement.setUser(user);

        repository.save(movement);

        return MovementResponseDTO.fromEntity(movement);
    }

    // LIST MOVEMENTS
    public List<MovementResponseDTO> listMovements(User user) {
        return repository.findByUser(user)
                .stream()
                .map(MovementResponseDTO::fromEntity)
                .toList();
    }

    // UPDATE
    public MovementResponseDTO updateMovement(Long id, UpdateMovementDTO dto, User user) {
        Movement movement = repository.findById(id)
                .orElseThrow(() -> new MovementNotFoundException(id));

        if (!movement.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException(dto.categoryId()));

        movement.setAmount(dto.amount());
        movement.setDate(dto.date());
        movement.setDescription(dto.description());
        movement.setCategory(category);

        return MovementResponseDTO.fromEntity(repository.save(movement));
    }

    // CALCULATE USER BALANCE
    public BigDecimal calculateUserBalance(Long userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        List<Movement> movements = this.repository.findByUserId(userId);

        BigDecimal balance = BigDecimal.ZERO;

        for (Movement m : movements) {
            if (m.getCategory().getType() == CategoryType.ENTRADA) {
                balance = balance.add(m.getAmount());
            } else if (m.getCategory().getType() == CategoryType.SAIDA) {
                balance = balance.subtract(m.getAmount());
            }
        }

        return balance;
    }

    // DELETE MOVEMENT BY ID
    public void delete(Long id, User user) {
        Movement movement = repository.findById(id)
                .orElseThrow(() -> new MovementNotFoundException(id));

        if (!movement.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

        repository.delete(movement);
    }

    // FIND MOVEMENT BY ID
    public Movement findById(Long id, User user) {
        Movement movement = repository.findById(id)
                .orElseThrow(() -> new MovementNotFoundException(id));

        if (!movement.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

        return movement;
    }
}
