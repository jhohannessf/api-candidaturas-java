<!-- adicionar banner/thumbnail aqui -->

# 💼 Candidaturas API

![Java](https://img.shields.io/badge/Java-26-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1-brightgreen?logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-database-blue?logo=postgresql)
![JWT](https://img.shields.io/badge/Auth-JWT-black?logo=jsonwebtokens)
![License](https://img.shields.io/badge/license-MIT-lightgrey)

API REST para gerenciamento de candidaturas a vagas de emprego, com autenticação via JWT e análise de compatibilidade entre perfil profissional e vaga usando IA (Google Gemini).

Projeto de portfólio desenvolvido para consolidar conhecimentos em Spring Boot, Spring Security, JPA/Hibernate e integração com APIs de IA.

## 🔨 Objetivos do projeto

- Construir uma API REST completa em Spring Boot seguindo arquitetura em camadas (controller, service, repository);
- Implementar autenticação e autorização stateless com JWT, incluindo controle de acesso a nível de método;
- Modelar relacionamentos JPA/Hibernate entre usuários, vagas, candidaturas e perfil profissional;
- Integrar a API a um modelo de IA (Google Gemini via Spring AI) para gerar análise de compatibilidade entre currículo e vaga;
- Aplicar boas práticas de segurança: variáveis de ambiente para segredos, senhas criptografadas, tratamento centralizado de exceções;
- Documentar a API de forma interativa com Swagger/OpenAPI.

## 📎 Sumário

- [Tecnologias utilizadas](#-tecnologias-utilizadas)
- [Arquitetura e modelagem](#-arquitetura-e-modelagem)
- [Autenticação e autorização](#-autenticação-e-autorização)
- [Como executar o projeto](#-como-executar-o-projeto)
- [Variáveis de ambiente](#-variáveis-de-ambiente)
- [Endpoints da API](#-endpoints-da-api)
- [Documentação interativa (Swagger)](#-documentação-interativa-swagger)
- [Observações e melhorias futuras](#-observações-e-melhorias-futuras)

## 🚀 Tecnologias utilizadas

- **Java 26**
- **Spring Boot 4.1** (Web MVC)
- **Spring Security** — autenticação stateless com JWT
- **Spring Data JPA** + **Hibernate** — persistência
- **PostgreSQL** — banco de dados relacional
- **Spring AI** (`spring-ai-starter-model-google-genai`) — integração com Google Gemini
- **JJWT** (`jjwt-api`, `jjwt-impl`, `jjwt-jackson`) — geração e validação de tokens JWT
- **Bean Validation** (`spring-boot-starter-validation`) — validação de DTOs de entrada
- **springdoc-openapi** — documentação interativa (Swagger UI)
- **Lombok** — redução de boilerplate
- **Maven** — gerenciador de dependências e build
- **Docker Compose** (opcional) — subida do PostgreSQL em container

## 🏗️ Arquitetura e modelagem

O projeto segue a separação clássica em camadas:

```
controller  → recebe requisições HTTP e delega para a camada de serviço
service     → regras de negócio
repository  → acesso a dados (Spring Data JPA)
model       → entidades JPA
dto         → objetos de transporte de dados (entrada/saída da API)
config      → configuração de segurança, JWT
handler     → tratamento centralizado de exceções
exception   → exceções customizadas
```

### Entidades principais

- **UsuarioEntity** — implementa `UserDetails` do Spring Security, permitindo que o próprio usuário seja usado como principal de autenticação. Possui um conjunto de `roles` (carregadas em modo `EAGER`, pois são necessárias para autorização em toda requisição autenticada).
- **RolesEntity** — implementa `GrantedAuthority`; representa os papéis do usuário (`ROLE_USUARIO`, `ROLE_ADMIN`).
- **VagaEntity** — representa uma vaga de emprego cadastrada por um administrador.
- **CandidaturaEntity** — representa a inscrição de um usuário em uma vaga, com `status` (enum `StatusCandidaturaEnum`) e `dataAplicacao`.
- **PerfilProfissionalEntity** — relação `1:1` com o usuário, guarda resumo de currículo e habilidades, usados na análise de compatibilidade via IA.

### Relacionamentos

- `UsuarioEntity` 1:N `VagaEntity` (um usuário/admin pode cadastrar várias vagas)
- `UsuarioEntity` 1:N `CandidaturaEntity` (um usuário pode ter várias candidaturas)
- `VagaEntity` 1:N `CandidaturaEntity` (uma vaga pode ter várias candidaturas)
- `UsuarioEntity` 1:1 `PerfilProfissionalEntity`
- `UsuarioEntity` N:N `RolesEntity`

## 🔐 Autenticação e autorização

A API usa **JWT stateless** — nenhuma sessão é mantida no servidor (`SessionCreationPolicy.STATELESS`).

Fluxo:

1. Usuário se registra em `POST /api/v1/auth/register`.
2. Usuário faz login em `POST /api/v1/auth/login` e recebe um token JWT.
3. Esse token deve ser enviado no header `Authorization: Bearer <token>` em todas as requisições a endpoints protegidos.
4. Um filtro (`JwtAuthenticationFilter`) intercepta cada requisição, valida o token e popula o contexto de segurança do Spring com o usuário autenticado.

Controle de acesso:

- Rotas de autenticação (`/api/v1/auth/**`) e documentação (`/swagger-ui/**`, `/v3/api-docs/**`) são públicas.
- Cadastro de vagas (`POST /api/v1/vagas/cadastrar`) exige papel `ADMIN`.
- Demais rotas exigem autenticação.
- Endpoints que expõem dados de um usuário específico usam `@PreAuthorize` para garantir que o solicitante seja o próprio usuário ou um administrador.

## ▶️ Como executar o projeto

### Pré-requisitos

- Java 26 instalado
- Maven (ou usar o wrapper `mvnw` incluso no projeto)
- PostgreSQL em execução (local, nativo, ou via Docker)
- Uma API key do Google Gemini ([Google AI Studio](https://aistudio.google.com/))

### Passo a passo

1. Clone o repositório:
   ```bash
   git clone https://github.com/<seu-usuario>/candidaturas.git
   cd candidaturas
   ```

2. Crie um banco de dados PostgreSQL e anote as credenciais.

3. Configure as variáveis de ambiente (veja a seção abaixo) em um arquivo `.env` ou diretamente no seu sistema/IDE.

4. Execute a aplicação:
   ```bash
   ./mvnw spring-boot:run
   ```

A aplicação sobe por padrão na porta `8080`.

> 💡 O projeto inclui um `compose.yaml` para subir o PostgreSQL via Docker automaticamente (Spring Boot Docker Compose Support). Esse comportamento está desabilitado por padrão (`spring.docker.compose.enabled: false`) — reative removendo essa propriedade caso queira usar o container em vez de uma instância nativa do Postgres.

## 🔑 Variáveis de ambiente

| Variável | Descrição |
|---|---|
| `DB_HOST` | Host e porta do PostgreSQL (ex: `localhost:5432`) |
| `DB_NAME` | Nome do banco de dados |
| `DB_USER` | Usuário do banco |
| `DB_PASSWORD` | Senha do banco |
| `JWT_KEY` | Chave secreta usada para assinar os tokens JWT (recomendado: string aleatória longa, ≥256 bits) |
| `JWT_EXPIRATION` | Tempo de expiração do token em milissegundos (padrão: `900000`, 15 minutos) |
| `GEMINI_API_KEY` | Chave de API do Google Gemini, usada na análise de compatibilidade |

> ⚠️ Nunca versione o arquivo `.env` ou qualquer valor real dessas variáveis no Git.

## 📡 Endpoints da API

### Autenticação (`/api/v1/auth`) — públicos

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/register` | Cadastra um novo usuário |
| `POST` | `/login` | Autentica e retorna um token JWT |

### Vagas (`/api/v1/vagas`)

| Método | Rota | Acesso | Descrição |
|---|---|---|---|
| `POST` | `/cadastrar` | ADMIN | Cadastra uma nova vaga |
| `GET` | `/` | Autenticado | Lista todas as vagas |
| `GET` | `/{id}` | Autenticado | Busca uma vaga por ID |

### Usuários (`/api/v1/usuarios`)

| Método | Rota | Acesso | Descrição |
|---|---|---|---|
| `GET` | `/{usuarioId}/candidaturas` | Próprio usuário ou ADMIN | Lista as candidaturas de um usuário |
| `POST` | `/inscrever` | Autenticado | Inscreve o usuário logado em uma vaga |

### Candidaturas (`/api/v1/candidaturas`)

| Método | Rota | Acesso | Descrição |
|---|---|---|---|
| `GET` | `/` | Autenticado | Lista as candidaturas do usuário logado (identificado via token) |

## 📖 Documentação interativa (Swagger)

Com a aplicação em execução, acesse:

```
http://localhost:8080/swagger-ui/index.html
```

A especificação OpenAPI em JSON fica disponível em `/v3/api-docs`.

## 🧭 Observações e melhorias futuras

Este projeto está em desenvolvimento ativo como parte de um portfólio de estudos. Alguns pontos conhecidos e próximos passos:

- Os campos `status` e `dataAplicacao` existem tanto em `VagaEntity` quanto em `CandidaturaEntity` — semanticamente pertencem apenas à candidatura (a relação entre usuário e vaga), e a duplicação em `VagaEntity` deve ser removida.
- Padronizar o uso de exceções customizadas (`NotFoundException`) em todos os pontos onde hoje se usa `RuntimeException` genérica, garantindo que o `GlobalExceptionHandler` retorne o status HTTP correto (404 em vez de 500) para recursos não encontrados.
- Adicionar testes automatizados de integração para os principais fluxos (registro, login, inscrição em vaga).
- Documentar exemplos de request/response para cada endpoint diretamente nas anotações do springdoc.

## 👤 Autor

Desenvolvido por **Jhohannes Freitas** como parte de um portfólio de transição para desenvolvimento backend Java.

[LinkedIn](#) · [GitHub](#)
