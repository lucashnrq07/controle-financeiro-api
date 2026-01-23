package com.lucas.controle_financeiro_api.controllers;

import com.lucas.controle_financeiro_api.dto.category.CreateCategoryDTO;
import com.lucas.controle_financeiro_api.dto.category.CategoryResponseDTO;
import com.lucas.controle_financeiro_api.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categories")
@Tag(name = "Category", description = "Gerenciamento de categorias financeiras")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // CREATE PERSONALIZED CATEGORY
    @Operation(summary = "Criar categoria personalizada do usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PostMapping("/user/{userId}")
    public ResponseEntity<CategoryResponseDTO> createCategory(
            @PathVariable Long userId,
            @RequestBody @Valid CreateCategoryDTO dto
    ) {
        CategoryResponseDTO created = categoryService.createCategory(dto, userId);

        URI location = URI.create("/categories/" + created.id());
        return ResponseEntity.created(location).body(created);
    }

    // LIST CATEGORIES (DEFAULT + USER)
    @Operation(summary = "Listar categorias disponíveis para o usuário (padrão + personalizadas)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de categorias retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CategoryResponseDTO>> listarCategoriasVisiveis(@PathVariable Long userId) {
        return ResponseEntity.ok(categoryService.listCategories(userId));
    }


    // FIND CATEGORY BY ID
    @Operation(summary = "Buscar categoria pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    // DELETE CATEGORY
    @Operation(summary = "Deletar uma categoria pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Categoria deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
