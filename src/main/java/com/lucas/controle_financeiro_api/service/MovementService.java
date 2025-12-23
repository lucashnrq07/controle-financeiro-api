package com.lucas.controle_financeiro_api.service;

import com.lucas.controle_financeiro_api.domain.entities.Category;
import com.lucas.controle_financeiro_api.domain.entities.Movement;
import com.lucas.controle_financeiro_api.domain.entities.User;
import com.lucas.controle_financeiro_api.domain.enums.CategoryType;
import com.lucas.controle_financeiro_api.dto.movement.MovementDTO;
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

    @Autowired
    private UserService userService;

    // CREATE NEW MOVEMENT
    public MovementDTO createMovement(MovementDTO data) {
        Category category = this.categoryRepository.findById(data.categoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        User user = this.userRepository.findById(data.categoryId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Movement newMovement = this.repository.save(new Movement(null, data.amount(), data.date(), data.description(), category, user));
        return new MovementDTO(data.amount(), data.date(), data.description(), data.categoryId(), data.userId());
    }

    // LIST ALL MOVEMENTS OF A USER
    public List<MovementDTO> listMovements(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Movement> movements = repository.findByUserId(userId);

        return movements.stream()
                .map(MovementDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // UPDATE MOVEMENT
    public MovementDTO updateMovement(Long movementId, MovementDTO dto) {
        Movement movement = repository.findById(movementId)
                .orElseThrow(() -> new RuntimeException("Movement not found"));

        // update amount
        if (dto.amount() != null) {
            if (dto.amount().compareTo(BigDecimal.ZERO) <= 0) {
                throw new RuntimeException("The value must be greater than zero");
            }
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
            Category category = categoryRepository.findById(dto.categoryId())
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
            movement.setCategory(category);
        }

        repository.save(movement);
        return MovementDTO.fromEntity(movement);
    }

    // CALCULATE USER BALANCE
    public BigDecimal calculateUserBalance(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Movement> movements = (List<Movement>) userService.findUserById(userId);

        BigDecimal balance = BigDecimal.ZERO;

        for (Movement m : movements) {
            if (m.getCategory().getType() == CategoryType.RECEITA) {
                balance = balance.add(m.getAmount());
            } else if (m.getCategory().getType() == CategoryType.DESPESA) {
                balance = balance.subtract(m.getAmount());
            }
        }

        return balance;
    }
}
