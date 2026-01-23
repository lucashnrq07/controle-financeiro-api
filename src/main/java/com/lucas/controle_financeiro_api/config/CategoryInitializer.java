package com.lucas.controle_financeiro_api.config;

import com.lucas.controle_financeiro_api.domain.entities.Category;
import com.lucas.controle_financeiro_api.domain.enums.CategoryType;
import com.lucas.controle_financeiro_api.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) {

        // ===== CATEGORIAS PADRÃO DO USUÁRIO =====
        criarCategoriaSeNaoExistir("Salário", CategoryType.ENTRADA, false);
        criarCategoriaSeNaoExistir("Investimentos", CategoryType.ENTRADA, false);

        criarCategoriaSeNaoExistir("Aluguel", CategoryType.SAIDA, false);
        criarCategoriaSeNaoExistir("Mercado", CategoryType.SAIDA, false);
        criarCategoriaSeNaoExistir("Transporte", CategoryType.SAIDA, false);
        criarCategoriaSeNaoExistir("Lazer", CategoryType.SAIDA, false);
        criarCategoriaSeNaoExistir("Saúde", CategoryType.SAIDA, false);

        // ===== CATEGORIAS INTERNAS DO SISTEMA =====
        criarCategoriaSeNaoExistir("DEPÓSITO EM META", CategoryType.SAIDA, true);
        criarCategoriaSeNaoExistir("RETIRADA DE META", CategoryType.ENTRADA, true);
    }

    private void criarCategoriaSeNaoExistir(String name, CategoryType type, boolean system) {

        categoryRepository.findByName(name).ifPresentOrElse(
                existing -> {
                    existing.setSystem(system);
                    existing.setType(type);
                    categoryRepository.save(existing);
                },
                () -> {
                    Category category = new Category(
                            null,
                            name,
                            type,
                            null,
                            true,
                            system
                    );
                    categoryRepository.save(category);
                }
        );
    }
}
