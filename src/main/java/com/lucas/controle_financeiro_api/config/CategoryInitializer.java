package com.lucas.controle_financeiro_api.config;

import com.lucas.controle_financeiro_api.domain.entities.Category;
import com.lucas.controle_financeiro_api.domain.entities.User;
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

        User user = new User(1L, "Lucas", "lucashsilvaa8@gmail.com", "Senha@123");

        criarCategoriaSeNaoExistir("Salário", CategoryType.ENTRADA);
        criarCategoriaSeNaoExistir("Investimentos", CategoryType.ENTRADA);

        criarCategoriaSeNaoExistir("Aluguel", CategoryType.SAIDA);
        criarCategoriaSeNaoExistir("Mercado", CategoryType.SAIDA);
        criarCategoriaSeNaoExistir("Transporte", CategoryType.SAIDA);
        criarCategoriaSeNaoExistir("Lazer", CategoryType.SAIDA);
        criarCategoriaSeNaoExistir("Saúde", CategoryType.SAIDA);

        criarCategoriaSeNaoExistir("DEPÓSITO EM META", CategoryType.SAIDA);
        criarCategoriaSeNaoExistir("RETIRADA DE META", CategoryType.ENTRADA);
    }

    private void criarCategoriaSeNaoExistir(String name, CategoryType type) {
        if (!categoryRepository.existsByName(name)) {
            categoryRepository.save(new Category(null, name, type, null, true));
        }
    }
}