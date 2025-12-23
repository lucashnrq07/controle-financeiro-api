package com.lucas.controle_financeiro_api.service;

import com.lucas.controle_financeiro_api.domain.entities.Category;
import com.lucas.controle_financeiro_api.domain.entities.User;
import com.lucas.controle_financeiro_api.dto.category.CategoryDTO;
import com.lucas.controle_financeiro_api.repositories.CategoryRepository;
import com.lucas.controle_financeiro_api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;
    private final UserRepository userRepository;

    // CREATE PERSONALIZED CATEGORY
    public CategoryDTO createCategory(CategoryDTO data, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Category category = new Category(
                null,
                data.name(),
                data.type(),
                user,
                false // isDefault
        );

        repository.save(category);
        return CategoryDTO.fromEntity(category);
    }

    // LIST CATEGORIES (DEFAULT + USER)
    public List<CategoryDTO> listCategories(Long userId) {

        List<Category> categories = repository.findByUserIdOrIsDefaultTrue(userId);

        return categories.stream()
                .map(CategoryDTO::fromEntity)
                .toList();
    }

    // FIND CATEGORY BY ID
    public CategoryDTO findById(Long id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        return CategoryDTO.fromEntity(category);
    }
}

