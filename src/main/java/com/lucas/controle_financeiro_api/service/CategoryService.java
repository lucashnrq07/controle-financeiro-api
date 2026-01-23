package com.lucas.controle_financeiro_api.service;

import com.lucas.controle_financeiro_api.domain.entities.Category;
import com.lucas.controle_financeiro_api.domain.entities.User;
import com.lucas.controle_financeiro_api.dto.category.CategoryResponseDTO;
import com.lucas.controle_financeiro_api.dto.category.CreateCategoryDTO;
import com.lucas.controle_financeiro_api.exceptions.CategoryNotFoundException;
import com.lucas.controle_financeiro_api.exceptions.UserNotFoundException;
import com.lucas.controle_financeiro_api.repositories.CategoryRepository;
import com.lucas.controle_financeiro_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private UserRepository userRepository;

    // CREATE CATEGORY
    public CategoryResponseDTO createCategory(CreateCategoryDTO data, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Category category = new Category(
                null,
                data.name(),
                data.type(),
                user,
                false,
                false
        );

        repository.save(category);

        return CategoryResponseDTO.fromEntity(category);
    }

    // LIST CATEGORIES (DEFAULT + USER)
    public List<CategoryResponseDTO> listCategories(Long userId) {

        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        List<Category> categories = repository.findVisibleCategories(userId);

        return categories.stream()
                .map(CategoryResponseDTO::fromEntity)
                .toList();
    }

    // FIND CATEGORY BY ID
    public CategoryResponseDTO findById(Long id) {

        Category category = repository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        return CategoryResponseDTO.fromEntity(category);
    }

    // DELETE CATEGORY
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new CategoryNotFoundException(id);
        }
    }
}

