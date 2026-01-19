package com.lucas.controle_financeiro_api.service;

import com.lucas.controle_financeiro_api.domain.entities.Category;
import com.lucas.controle_financeiro_api.domain.entities.User;
import com.lucas.controle_financeiro_api.domain.enums.CategoryType;
import com.lucas.controle_financeiro_api.dto.CategoryDTO;
import com.lucas.controle_financeiro_api.exceptions.CategoryNotFoundException;
import com.lucas.controle_financeiro_api.exceptions.UserNotFoundException;
import com.lucas.controle_financeiro_api.repositories.CategoryRepository;
import com.lucas.controle_financeiro_api.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    private CategoryService service;

    @Mock
    private CategoryRepository repository;

    @Mock
    private UserRepository userRepository;

    // ========== CREATE CATEGORY ==========
    @Test
    @DisplayName("Deve criar categoria personalizada com sucesso")
    void createCategorySuccess() {
        Long userId = 1L;

        User user = new User();
        user.setId(userId);

        CategoryDTO dto = new CategoryDTO(
                null,
                "Alimentação",
                CategoryType.SAIDA
        );

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(repository.save(any(Category.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CategoryDTO result = service.createCategory(dto, userId);

        assertNotNull(result);
        assertEquals(dto.name(), result.name());
        assertEquals(dto.type(), result.type());

        verify(userRepository).findById(userId);
        verify(repository).save(any(Category.class));
    }

    @Test
    @DisplayName("Ao criar categoria, deve lançar exceção se usuário não existir")
    void createCategoryUserNotFound() {
        Long userId = 1L;

        CategoryDTO dto = new CategoryDTO(
                null,
                "Alimentação",
                CategoryType.SAIDA
        );

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> service.createCategory(dto, userId)
        );

        verify(userRepository).findById(userId);
        verify(repository, never()).save(any());
    }

    // ========== LIST CATEGORIES ==========
    @Test
    @DisplayName("Deve listar categorias padrão + do usuário")
    void listCategoriesSuccess() {
        Long userId = 1L;

        User user = new User();
        user.setId(userId);

        Category category1 = new Category(
                1L,
                "Alimentação",
                CategoryType.SAIDA,
                user,
                false
        );

        Category category2 = new Category(
                2L,
                "Salário",
                CategoryType.ENTRADA,
                null,
                true
        );

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(repository.findByUserIdOrIsDefaultTrue(userId))
                .thenReturn(List.of(category1, category2));

        List<CategoryDTO> result = service.listCategories(userId);

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(userRepository).findById(userId);
        verify(repository).findByUserIdOrIsDefaultTrue(userId);
    }

    @Test
    @DisplayName("Ao listar categorias, deve lançar exceção se usuário não existir")
    void listCategoriesUserNotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> service.listCategories(userId)
        );

        verify(userRepository).findById(userId);
        verify(repository, never()).findByUserIdOrIsDefaultTrue(anyLong());
    }

    // ========== FIND BY ID ==========
    @Test
    @DisplayName("Deve buscar categoria por ID com sucesso")
    void findByIdSuccess() {
        Long categoryId = 1L;

        Category category = new Category();
        category.setId(categoryId);

        when(repository.findById(categoryId))
                .thenReturn(Optional.of(category));

        CategoryDTO result = service.findById(categoryId);

        assertNotNull(result);
        verify(repository).findById(categoryId);
    }

    @Test
    @DisplayName("Ao buscar categoria por ID inexistente, deve lançar exceção")
    void findByIdNotFound() {
        Long categoryId = 1L;

        when(repository.findById(categoryId))
                .thenReturn(Optional.empty());

        assertThrows(
                CategoryNotFoundException.class,
                () -> service.findById(categoryId)
        );

        verify(repository).findById(categoryId);
    }
}
