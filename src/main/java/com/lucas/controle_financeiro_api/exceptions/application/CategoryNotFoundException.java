package com.lucas.controle_financeiro_api.exceptions.application;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(String name) {
        super("Category not found with name: " + name);
    }

    public CategoryNotFoundException(Long id) {
        super("Category not found with id: " + id);
    }
}
