# Sistema de Controle Financeiro Pessoal

API REST desenvolvida em **Java com Spring Boot** para controle de receitas e despesas pessoais, com foco em **organização financeira**, **boas práticas de back-end**, **regras de negócio** e **qualidade de código**.

O projeto simula um cenário real de mercado, permitindo o gerenciamento de usuários, categorias e movimentações financeiras, com validações, persistência de dados e testes automatizados.

---

## Objetivo do Projeto

* Consolidar conhecimentos em desenvolvimento back-end com Java e Spring Boot
* Aplicar regras de negócio comuns a sistemas financeiros
* Desenvolver uma API REST organizada, testável e escalável
* Simular um projeto próximo da realidade profissional

---

## Funcionalidades

* Cadastro de usuários
* Cadastro de categorias (entrada e saída)
* Registro de movimentações financeiras
* Regras de negócio para receitas e despesas
* Associação entre usuários, categorias e movimentações
* Validação de dados de entrada
* Tratamento de exceções
* Documentação da API com Swagger
* Testes unitários na camada de serviço

---

## Arquitetura

O projeto segue uma **arquitetura em camadas**, separando responsabilidades e facilitando manutenção e testes:

* **Controller** – Exposição dos endpoints REST
* **Service** – Regras de negócio e validações
* **Repository** – Persistência de dados com JPA
* **DTOs** – Transferência de dados entre camadas
* **Entities** – Modelo de domínio
* **Exceptions** – Exceções personalizadas
* **Handler** – Tratamento global de erros

---

## O que eu aprendi com este projeto

### Modelagem de Domínio

* Representação de usuários, categorias e movimentações financeiras
* Relacionamentos entre entidades

### Regras de Negócio

* Diferença entre receitas e despesas
* Validações financeiras e consistência dos dados

### Arquitetura em Camadas

* Separação clara de responsabilidades
* Controllers enxutos e lógica concentrada no service

### APIs REST

* Criação de endpoints REST seguindo boas práticas
* Uso correto dos métodos HTTP
* Documentação com Swagger / SpringDoc

### Tratamento de Exceções

* Criação de exceções personalizadas
* Tratamento global de erros com @RestControllerAdvice

### Testes Unitários

* Testes da camada de serviço
* Uso de JUnit 5 e Mockito

### Boas Práticas de Desenvolvimento

* Código limpo e organizado
* Versionamento com Git
* Estrutura preparada para evolução do sistema

---

## Tecnologias Utilizadas

* Java 21
* Spring Boot 4

  * Spring Web
  * Spring Data JPA
  * Spring Validation
* MySQL
* Swagger / SpringDoc
* JUnit 5
* Mockito
* Maven
* Git e GitHub

---

## Documentação da API

A documentação da API é gerada automaticamente com **Swagger**.

Após iniciar a aplicação, acesse:

```
http://localhost:8080/swagger-ui.html
```

---

Projeto desenvolvido por **Lucas Henrique** 👨‍💻
