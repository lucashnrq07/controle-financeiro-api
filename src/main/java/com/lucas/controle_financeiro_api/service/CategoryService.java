package com.lucas.controle_financeiro_api.service;

import com.lucas.controle_financeiro_api.domain.entities.Category;
import com.lucas.controle_financeiro_api.dto.category.CategoryDTO;
import com.lucas.controle_financeiro_api.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    // CREATE NEW CATEGORY
    public CategoryDTO createCategory(CategoryDTO data) {
        Category newCategory = this.repository.save(new Category(null, data.name(), data.type()));
        return new CategoryDTO(data.name(), data.type());
    }

    // LIST ALL CATEGORIES
    public List<Category> listAllCategories() {
        List<Category> list = new ArrayList<>(this.repository.findAll());
        return list;
    }

    // FIND CATEGORY BY ID
    public Category findCategoryById(Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
    }
}
