package com.lucas.controle_financeiro_api.controllers;

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
@Tag(name = "Category", description = "Gerenciamento de categorias financeiras")
public class CategoryController {

    private final CategoryService categoryService;

    // CREATE PERSONALIZED CATEGORY
    @Operation(summary = "Criar categoria personalizada do usuário")
    @PostMapping("/user/{userId}")
    public ResponseEntity<CategoryDTO> createCategory(
            @PathVariable Long userId,
            @RequestBody CategoryDTO dto
    ) {
        CategoryDTO created = categoryService.createCategory(dto, userId);
        return ResponseEntity.status(201).body(created);
    }

    // LIST CATEGORIES (DEFAULT + USER)
    @Operation(summary = "Listar categorias disponíveis para o usuário")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CategoryDTO>> listCategories(@PathVariable Long userId) {
        return ResponseEntity.ok(categoryService.listCategories(userId));
    }

    // FIND CATEGORY BY ID
    @Operation(summary = "Buscar categoria por ID")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }
}

