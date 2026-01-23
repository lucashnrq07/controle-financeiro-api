package com.lucas.controle_financeiro_api.service;

import com.lucas.controle_financeiro_api.domain.entities.Category;
import com.lucas.controle_financeiro_api.domain.entities.Movement;
import com.lucas.controle_financeiro_api.domain.entities.User;
import com.lucas.controle_financeiro_api.domain.enums.CategoryType;
import com.lucas.controle_financeiro_api.dto.movement.CreateMovementDTO;
import com.lucas.controle_financeiro_api.dto.movement.MovementResponseDTO;
import com.lucas.controle_financeiro_api.dto.movement.UpdateMovementDTO;
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

@Service
public class MovementService {

    @Autowired
    private MovementRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    // CREATE MOVEMENT
    public MovementResponseDTO createMovement(CreateMovementDTO data) {

        Category category = categoryRepository.findById(data.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException(data.categoryId()));

        User user = userRepository.findById(data.userId())
                .orElseThrow(() -> new UserNotFoundException(data.userId()));

        Movement movement = repository.save(
                new Movement(null, data.amount(), data.date(), data.description(), category, null, user)
        );

        return MovementResponseDTO.fromEntity(movement);
    }

    // LIST MOVEMENTS
    public List<MovementResponseDTO> listMovements(Long userId) {

        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return repository.findByUserId(userId)
                .stream()
                .map(MovementResponseDTO::fromEntity)
                .toList();
    }

    // UPDATE
    public MovementResponseDTO updateMovement(Long movementId, UpdateMovementDTO dto) {

        Movement movement = repository.findById(movementId)
                .orElseThrow(() -> new MovementNotFoundException(movementId));

        if (dto.amount() != null) movement.setAmount(dto.amount());
        if (dto.date() != null) movement.setDate(dto.date());
        if (dto.description() != null) movement.setDescription(dto.description());

        if (dto.categoryId() != null) {
            Category category = categoryRepository.findById(dto.categoryId())
                    .orElseThrow(() -> new CategoryNotFoundException(dto.categoryId()));
            movement.setCategory(category);
        }

        repository.save(movement);
        return MovementResponseDTO.fromEntity(movement);
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
        if (!repository.existsById(id)) {
            throw new MovementNotFoundException(id);
        }
        repository.deleteById(id);
    }

    public Movement findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new MovementNotFoundException(id));
    }
}
