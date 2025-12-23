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
                CategoryType.RECEITA,
                null,
                true
        ));

        categoryRepository.save(new Category(
                null,
                "Freelance",
                CategoryType.RECEITA,
                null,
                true
        ));

        categoryRepository.save(new Category(
                null,
                "Investimentos",
                CategoryType.RECEITA,
                null,
                true
        ));

        // DESPESAS
        categoryRepository.save(new Category(
                null,
                "Aluguel",
                CategoryType.DESPESA,
                null,
                true
        ));

        categoryRepository.save(new Category(
                null,
                "Mercado",
                CategoryType.DESPESA,
                null,
                true
        ));

        categoryRepository.save(new Category(
                null,
                "Transporte",
                CategoryType.DESPESA,
                null,
                true
        ));

        categoryRepository.save(new Category(
                null,
                "Lazer",
                CategoryType.DESPESA,
                null,
                true
        ));

        categoryRepository.save(new Category(
                null,
                "Saúde",
                CategoryType.DESPESA,
                null,
                true
        ));
    }
}