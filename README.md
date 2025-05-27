# ControleEstoque - Sistema de Controle de Estoque e Vendas

Um sistema para gerenciamento de inventário que integra backend em Spring Boot com frontend em Vue.js.

## Visão Geral

ControleEstoque é uma aplicação full-stack destinada à gestão de inventários, oferecendo uma interface moderna e responsiva para controlar produtos, fornecedores, movimentações de estoque e relatórios.

## Tecnologias Utilizadas

### Backend
- Java 21
- Spring Boot
- PostgreSQL 16
- Hibernate/JPA
- Gradle

### Frontend
- Vue.js 3
- Vite
- TypeScript/JavaScript
- TailwindCSS
- ApexCharts
- FullCalendar
- FontAwesome
- Axios

### DevOps
- Docker & Docker Compose
- PgAdmin 4

### Banco de Dados
- PostgreSQL 16

## Estrutura do Projeto

```
raiz/
├── backend/             # Aplicação Spring Boot
├── frontend-vue/        # Aplicação Vue.js
└── docker-compose.yml   # Configuração para orquestração dos serviços
```

## Instalação e Execução

### Pré-requisitos
- Docker e Docker Compose instalados

### Passos para execução

1. Clone o repositório:
   ```bash
   git clone [url-do-repositorio]
   cd [diretorio-do-projeto]
   ```

2. Execute o sistema completo com Docker Compose:
   ```bash
   docker-compose up
   ```

3. Aguarde todos os serviços iniciarem. O processo está concluído quando você visualizar mensagens confirmando que o backend e frontend estão operacionais.

## Acesso aos Serviços

Após a inicialização, os serviços estarão disponíveis nos seguintes endereços:

- **Frontend**: [http://localhost:5173](http://localhost:5173)
- **Backend API**: [http://localhost:8080](http://localhost:8080)
- **PgAdmin (Gerenciamento do Banco)**: [http://localhost:8081](http://localhost:8081)

## Credenciais Padrão

### Banco de Dados PostgreSQL
- **Usuário**: admin
- **Senha**: admin123
- **Banco de dados**: postgres

### PgAdmin
- **Email**: admin@estoque.dev.br
- **Senha**: admin123

Para conectar ao banco de dados via PgAdmin, utilize as seguintes informações:
- **Host**: estoque-db
- **Porta**: 5432
- **Usuário**: admin
- **Senha**: admin123

## Desenvolvimento

Para desenvolvimento local fora do Docker, configure seu ambiente com:
- Java 21
- Node.js 22
- PostgreSQL 16

## Notas

Este projeto implementa os princípios de Clean Architecture (Arquitetura Limpa) no backend, utilizando Java e Spring Boot - tecnologias nas quais possuo experiência após anos de prática profissional.

O frontend, desenvolvido com Vue.js, representa minha primeira experiência com este framework. A transição de outros frameworks frontend para o Vue.js foi um processo desafiador e extremamente enriquecedor. O ecossistema Vue proporcionou uma curva de aprendizado interessante, especialmente nos aspectos de:

- Implementação do sistema de componentes reativos
- Utilização do Composition API
- Integração com Vite para um desenvolvimento mais ágil
- Aplicação de TypeScript em conjunto com Vue.js

Este contraste entre trabalhar com tecnologias consolidadas no backend e explorar novas possibilidades no frontend criou uma experiência de desenvolvimento balanceada entre confiança e descoberta, resultando em um sistema robusto e moderno.
