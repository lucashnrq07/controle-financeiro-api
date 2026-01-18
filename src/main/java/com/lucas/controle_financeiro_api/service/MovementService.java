package com.lucas.controle_financeiro_api.service;

import com.lucas.controle_financeiro_api.domain.entities.Category;
import com.lucas.controle_financeiro_api.domain.entities.Movement;
import com.lucas.controle_financeiro_api.domain.entities.User;
import com.lucas.controle_financeiro_api.domain.enums.CategoryType;
import com.lucas.controle_financeiro_api.dto.MovementDTO;
import com.lucas.controle_financeiro_api.dto.UpdateMovementDTO;
import com.lucas.controle_financeiro_api.exceptions.CategoryNotFoundException;
import com.lucas.controle_financeiro_api.exceptions.MovementNotFoundException;
import com.lucas.controle_financeiro_api.exceptions.UserNotFoundException;
import com.lucas.controle_financeiro_api.repositories.CategoryRepository;
import com.lucas.controle_financeiro_api.repositories.MovementRepository;
import com.lucas.controle_financeiro_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovementService {

    @Autowired
    private MovementRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    // CREATE NEW MOVEMENT
    public MovementDTO createMovement(MovementDTO data) {
        Category category = this.categoryRepository.findById(data.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException(data.categoryId()));
        User user = this.userRepository.findById(data.userId())
                .orElseThrow(() -> new UserNotFoundException(data.userId()));

        Movement movement = this.repository.save(
                new Movement(null, data.amount(), data.date(), data.description(), category, null, user)
        );

        return MovementDTO.fromEntity(movement);
    }

    // LIST ALL MOVEMENTS OF A USER
    public List<MovementDTO> listMovements(Long userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        List<Movement> movements = this.repository.findByUserId(userId);

        return movements.stream()
                .map(MovementDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // UPDATE MOVEMENT
    public MovementDTO updateMovement(Long movementId, UpdateMovementDTO dto) {
        Movement movement = this.repository.findById(movementId)
                .orElseThrow(() -> new MovementNotFoundException(movementId));

        // update amount
        if (dto.amount() != null) {
            movement.setAmount(dto.amount());
        }

        // update date
        if (dto.date() != null) {
            movement.setDate(dto.date());
        }

        // update description
        if (dto.description() != null) {
            movement.setDescription(dto.description());
        }

        // update category
        if (dto.categoryId() != null) {
            Category category = this.categoryRepository.findById(dto.categoryId())
                    .orElseThrow(() -> new CategoryNotFoundException(movementId));
            movement.setCategory(category);
        }

        this.repository.save(movement);
        return MovementDTO.fromEntity(movement);
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
    public void delete(Long id) {
        try {
            this.repository.deleteById(id);
        } catch (MovementNotFoundException e) {
            throw new MovementNotFoundException(id);
        }
    }

    public Movement findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new MovementNotFoundException(id));
    }
}
