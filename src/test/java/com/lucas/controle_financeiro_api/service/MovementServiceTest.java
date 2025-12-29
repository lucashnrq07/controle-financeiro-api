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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovementServiceTest {

    @Mock
    private MovementRepository repository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MovementService service;

    // ========== CREATE MOVEMENT ==========
    @Test
    @DisplayName("Deve criar movimentação com sucesso")
    void createMovementSuccess() {

        User user = new User(1L, "Lucas", "lucas@gmail.com", "1234");
        Category category = new Category(1L, "Salário", CategoryType.ENTRADA, user, false);

        MovementDTO dto = new MovementDTO(
                new BigDecimal("1000.00"),
                LocalDate.now(),
                "Pagamento",
                category.getId(),
                user.getId()
        );

        when(categoryRepository.findById(category.getId()))
                .thenReturn(Optional.of(category));

        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));

        when(repository.save(any(Movement.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        MovementDTO result = service.createMovement(dto);

        assertNotNull(result);
        assertEquals(dto.amount(), result.amount());

        verify(categoryRepository).findById(category.getId());
        verify(userRepository).findById(user.getId());
        verify(repository).save(any(Movement.class));
    }

    @Test
    @DisplayName("Ao criar movimentação, deve lançar exceção se categoria não existir")
    void createMovementCategoryNotFound() {

        MovementDTO dto = new MovementDTO(
                new BigDecimal("100"),
                LocalDate.now(),
                "Teste",
                1L,
                1L
        );

        when(categoryRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                CategoryNotFoundException.class,
                () -> service.createMovement(dto)
        );

        verify(categoryRepository).findById(1L);
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Ao criar movimentação, deve lançar exceção se usuário não existir")
    void createMovementUserNotFound() {

        Category category = new Category(1L, "Teste", CategoryType.ENTRADA, null, false);

        MovementDTO dto = new MovementDTO(
                new BigDecimal("100"),
                LocalDate.now(),
                "Teste",
                1L,
                1L
        );

        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(category));

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> service.createMovement(dto)
        );

        verify(userRepository).findById(1L);
        verify(repository, never()).save(any());
    }

    // ========== LIST MOVEMENTS ==========
    @Test
    @DisplayName("Deve listar todas as movimentações do usuário")
    void listMovementsSuccess() {

        User user = new User(1L, "Lucas", "lucas@gmail.com", "1234");
        Category entrada = new Category(1L, "Salário", CategoryType.ENTRADA, user, false);

        Movement m1 = new Movement(
                1L, new BigDecimal("1000"), LocalDate.now(), "Salário", entrada, user
        );

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(repository.findByUserId(1L))
                .thenReturn(List.of(m1));

        List<MovementDTO> result = service.listMovements(1L);

        assertEquals(1, result.size());
        verify(repository).findByUserId(1L);
    }

    @Test
    @DisplayName("Ao listar movimentações, deve lançar exceção se usuário não existir")
    void listMovementsUserNotFound() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> service.listMovements(1L)
        );

        verify(repository, never()).findByUserId(anyLong());
    }

    // ========== UPDATE MOVEMENT ===========
    @Test
    @DisplayName("Deve atualizar movimentação com sucesso")
    void updateMovementSuccess() {

        User user = new User(1L, "Lucas", "lucas@gmail.com", "1234");
        Category oldCategory = new Category(1L, "Antiga", CategoryType.SAIDA, user, false);
        Category newCategory = new Category(2L, "Nova", CategoryType.ENTRADA, user, false);

        Movement movement = new Movement(
                1L,
                new BigDecimal("100"),
                LocalDate.now(),
                "Desc",
                oldCategory,
                user
        );

        UpdateMovementDTO dto = new UpdateMovementDTO(
                new BigDecimal("200"),
                LocalDate.now(),
                "Atualizado",
                newCategory.getId()
        );

        when(repository.findById(1L))
                .thenReturn(Optional.of(movement));

        when(categoryRepository.findById(2L))
                .thenReturn(Optional.of(newCategory));

        MovementDTO result = service.updateMovement(1L, dto);

        assertEquals(new BigDecimal("200"), result.amount());
        verify(repository).save(movement);
    }

    @Test
    @DisplayName("Ao atualizar movimentação inexistente, deve lançar exceção")
    void updateMovementNotFound() {

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                MovementNotFoundException.class,
                () -> service.updateMovement(1L, new UpdateMovementDTO(null, null, null, null))
        );
    }

    // ========== CALCULATE BALANCE ==========
    @Test
    @DisplayName("Deve calcular corretamente o saldo do usuário")
    void calculateUserBalanceSuccess() {

        User user = new User(1L, "Lucas", "lucas@gmail.com", "1234");

        Category entrada = new Category(1L, "Salário", CategoryType.ENTRADA, user, false);
        Category saida = new Category(2L, "Aluguel", CategoryType.SAIDA, user, false);

        Movement m1 = new Movement(1L, new BigDecimal("1000"), LocalDate.now(), "Salário", entrada, user);
        Movement m2 = new Movement(2L, new BigDecimal("400"), LocalDate.now(), "Aluguel", saida, user);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(repository.findByUserId(1L))
                .thenReturn(List.of(m1, m2));

        BigDecimal balance = service.calculateUserBalance(1L);

        assertEquals(new BigDecimal("600"), balance);
    }

    @Test
    @DisplayName("Ao calcular saldo, deve lançar exceção se usuário não existir")
    void calculateUserBalanceUserNotFound() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> service.calculateUserBalance(1L)
        );
    }
}
