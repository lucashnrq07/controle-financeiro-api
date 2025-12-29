package com.lucas.controle_financeiro_api.service;

import com.lucas.controle_financeiro_api.domain.entities.User;
import com.lucas.controle_financeiro_api.dto.UserDTO;
import com.lucas.controle_financeiro_api.exceptions.UserNotFoundException;
import com.lucas.controle_financeiro_api.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    // ========== CREATE USER ==========
    @Test
    @DisplayName("Usuário criado com sucesso")
    void createUserSuccess() {
        UserDTO dto = new UserDTO("Lucas", "lucas@gmail.com", "12345");
        User newUser = this.repository.save(new User(null, dto.name(), dto.email(), dto.password()));
        verify(repository, times(1)).save(any(User.class));
    }


    // ========== FIND USER BY ID ==========
    @Test
    @DisplayName("Busca de usuário através do ID realizada com sucesso")
    void findUserByIdSuccess() {
        User user = new User(1L, "Lucas", "lucas@gmail.com", "1234");

        when(repository.findById(1L))
                .thenReturn(Optional.of(user));

        User result = service.findUserById(1L);

        assertNotNull(result);
        assertEquals("lucas@gmail.com", result.getEmail());

        verify(repository).findById(1L);
    }

    @Test
    @DisplayName("Busca de usuário por ID inexistente deve lançar UserNotFoundException")
    void findUserByIdException() {

        Long id = 1L;

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> service.findUserById(id)
        );

        assertTrue(exception.getMessage().contains(id.toString()));
        verify(repository, times(1)).findById(id);
    }
}