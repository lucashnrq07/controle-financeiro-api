# 💰 Sistema de Controle Financeiro – Backend API

API REST desenvolvida em **Java 21 com Spring Boot** para gerenciamento de finanças pessoais.

O sistema permite o controle de **usuários, categorias e movimentações financeiras (receitas e despesas)**, aplicando regras de negócio e boas práticas de arquitetura back-end.

---

## 🌐 API em Produção

A aplicação está publicada e pode ser acessada em:

🔗 **Base URL:**  
https://controle-financeiro-frontend-cz3j.onrender.com/

📖 **Documentação Swagger:**  
https://controle-financeiro-api-vqun.onrender.com/swagger-ui/index.html

---

## 🚀 Objetivo

Organizar e gerir a vida financeira dos usuários utilizando:

- Arquitetura em camadas
- Boas práticas de desenvolvimento
- Separação de responsabilidades
- Validações robustas
- Testes automatizados
- Documentação de API
- Deploy em ambiente cloud

---

## 📌 Funcionalidades

- Cadastro de usuários
- Cadastro de categorias (RECEITA / DESPESA)
- Registro de movimentações financeiras
- Acompanhamento de metas pessoais
- Movimentações automáticas mensais ou semanais
- Associação entre usuário e categoria
- Validação de dados com Bean Validation
- Tratamento global de exceções com `@RestControllerAdvice`
- Documentação automática com Swagger (SpringDoc)
- Testes unitários na camada de serviço

---

## 🏗 Arquitetura

O projeto segue arquitetura em camadas:

Controller → Service → Repository → Database

### Estrutura

- **Controller** → Endpoints REST
- **Service** → Regras de negócio
- **Repository** → Persistência com JPA
- **DTOs** → Comunicação entre camadas
- **Entities** → Modelo de domínio
- **Exception Handler** → Tratamento global de erros

---

## ☁️ Deploy

A aplicação está hospedada em:

- Plataforma: Render
- Banco de dados: PostgreSQL (prod), MySQL (local)

---

## 🛠 Tecnologias Utilizadas

- Java 21
- Spring Boot
  - Spring Web
  - Spring Data JPA
  - Spring Security
- MySQL
- SpringDoc OpenAPI
- JUnit 5
- Mockito
- Maven

---

## 🧪 Testes

Os testes unitários estão concentrados na camada de serviço e validam as principais regras de negócio.

---

## 👨‍💻 Autor

Desenvolvido por **Lucas Henrique**
