package com.lucas.controle_financeiro_api.controller;

import com.lucas.controle_financeiro_api.domain.entities.Category;
import com.lucas.controle_financeiro_api.dto.category.CategoryDTO;
import com.lucas.controle_financeiro_api.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Tag(name = "Category", description = "Gerenciamento de categorias de movimentações")
public class CategoryController {

    private final CategoryService categoryService;

    // CREATE CATEGORY
    @Operation(summary = "Criar uma nova categoria")
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO dto) {
        CategoryDTO createdCategory = categoryService.createCategory(dto);
        return ResponseEntity.status(201).body(createdCategory);
    }

    // LIST ALL CATEGORIES
    @Operation(summary = "Listar todas as categorias")
    @GetMapping
    public ResponseEntity<List<Category>> listAllCategories() {
        List<Category> categories = categoryService.listAllCategories();
        return ResponseEntity.ok(categories);
    }

    // FIND CATEGORY BY ID
    @Operation(summary = "Buscar categoria pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<Category> findCategoryById(@PathVariable Long id) {
        Category category = categoryService.findCategoryById(id);
        return ResponseEntity.ok(category);
    }
}
