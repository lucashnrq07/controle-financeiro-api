package com.lucas.controle_financeiro_api.config;

import com.lucas.controle_financeiro_api.domain.entities.Category;
import com.lucas.controle_financeiro_api.domain.enums.CategoryType;
import com.lucas.controle_financeiro_api.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryInitializer implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public void run(String... args) {

        if (categoryRepository.count() > 0) {
            return;
        }

        // RECEITAS
        categoryRepository.save(new Category(
                null,
                "Salário",
                CategoryType.ENTRADA,
                null,
                true
        ));

        categoryRepository.save(new Category(
                null,
                "Investimentos",
                CategoryType.ENTRADA,
                null,
                true
        ));

        // DESPESAS
        categoryRepository.save(new Category(
                null,
                "Aluguel",
                CategoryType.SAIDA,
                null,
                true
        ));

        categoryRepository.save(new Category(
                null,
                "Mercado",
                CategoryType.SAIDA,
                null,
                true
        ));

        categoryRepository.save(new Category(
                null,
                "Transporte",
                CategoryType.SAIDA,
                null,
                true
        ));

        categoryRepository.save(new Category(
                null,
                "Lazer",
                CategoryType.SAIDA,
                null,
                true
        ));

        categoryRepository.save(new Category(
                null,
                "Saúde",
                CategoryType.SAIDA,
                null,
                true
        ));
    }
}