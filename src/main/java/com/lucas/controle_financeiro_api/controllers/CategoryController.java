package com.lucas.controle_financeiro_api.controllers;

import com.lucas.controle_financeiro_api.dto.category.CategoryDTO;
import com.lucas.controle_financeiro_api.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@Tag(name = "Category", description = "Gerenciamento de categorias financeiras")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    // CREATE PERSONALIZED CATEGORY
    @Operation(summary = "Criar categoria personalizada do usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PostMapping("/user/{userId}")
    public ResponseEntity<CategoryDTO> createCategory(@PathVariable Long userId, @RequestBody @Valid CategoryDTO dto) {
        CategoryDTO created = categoryService.createCategory(dto, userId);
        return ResponseEntity.status(201).body(created);
    }


    // LIST CATEGORIES (DEFAULT + USER)
    @Operation(summary = "Listar categorias disponíveis para o usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de categorias retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CategoryDTO>> listCategories(@PathVariable Long userId) {
        return ResponseEntity.ok(categoryService.listCategories(userId));
    }


    // FIND CATEGORY BY ID
    @Operation(summary = "Buscar categoria por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    // DELETE CATEGORY BY ID
    @Operation(summary = "Deletar uma categoria pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Categoria deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

