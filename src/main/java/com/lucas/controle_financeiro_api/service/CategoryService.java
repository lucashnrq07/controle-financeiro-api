package com.lucas.controle_financeiro_api.service;

import com.lucas.controle_financeiro_api.domain.entities.Category;
import com.lucas.controle_financeiro_api.domain.entities.User;
import com.lucas.controle_financeiro_api.dto.CategoryDTO;
import com.lucas.controle_financeiro_api.exceptions.CategoryNotFoundException;
import com.lucas.controle_financeiro_api.exceptions.UserNotFoundException;
import com.lucas.controle_financeiro_api.repositories.CategoryRepository;
import com.lucas.controle_financeiro_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private UserRepository userRepository;

    // CREATE PERSONALIZED CATEGORY
    public CategoryDTO createCategory(CategoryDTO data, Long userId) {

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Category category = new Category(
                null,
                data.name(),
                data.type(),
                user,
                false // isDefault
        );

        this.repository.save(category);
        return CategoryDTO.fromEntity(category);
    }

    // LIST CATEGORIES (DEFAULT + USER)
    public List<CategoryDTO> listCategories(Long userId) {

        List<Category> categories = this.repository.findByUserIdOrIsDefaultTrue(userId);

        return categories.stream()
                .map(CategoryDTO::fromEntity)
                .toList();
    }

    // FIND CATEGORY BY ID
    public CategoryDTO findById(Long id) {
        Category category = this.repository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        return CategoryDTO.fromEntity(category);
    }
}

