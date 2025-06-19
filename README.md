![Logo do Desafio](diagrams/logo.webp)

# 🚀 Desafio do Bootcamp NTT Data JV Backend para Iniciantes

## 💡 Desafio Proposto

![Imagem do Desafio](diagrams/desafio.webp)

## 🎯 Objetivo

Criar um simulador de pedidos e cadastro de produtos utilizando arquitetura de microsserviços com **Spring Boot** e **Spring Cloud**.

---

## 🧱 Descrição do Projeto

O projeto é composto por dois microsserviços acessíveis via **API Gateway**, nos seguintes endpoints:

- `/produtos`
- `/pedidos`

---

## 🗂️ Estrutura do Projeto

O sistema é dividido em módulos, cada um com uma função específica:

- **configserver**: Responsável por armazenar as configurações centralizadas dos demais serviços.
- **discovery**: Serviço de descoberta de microsserviços registrados (Eureka).
- **gateway**: API pública que permite o acesso externo aos serviços.
- **order**: Microsserviço responsável pela gestão de pedidos.
- **product**: Microsserviço responsável pela gestão de produtos.

---

## 🛠️ Tecnologias Utilizadas

- Java 24
- Spring Boot
- Spring Cloud
- Spring Cloud Config
- Spring Cloud Gateway
- Eureka Server
- OpenFeign
- Flyway
- Gradle 8.14
- Lombok
- H2 Database (em memória)
- Zipkin
- Keycloak

---

## 📡 API

### 📦 Produtos

| Método | Endpoint          | Descrição                                 |
|--------|-------------------|-------------------------------------------|
| POST   | `/produtos`       | Cadastra um novo produto                  |
| GET    | `/produtos`       | Retorna a lista de produtos cadastrados   |
| GET    | `/produtos/{id}`  | Retorna os detalhes de um produto         |
| PUT    | `/produtos/{id}`  | Atualiza as informações de um produto     |
| DELETE | `/produtos/{id}`  | Remove um produto do sistema              |

### 🧾 Pedidos

| Método | Endpoint         | Descrição                                  |
|--------|------------------|--------------------------------------------|
| POST   | `/pedidos/create`       | Realiza o cadastro de um novo pedido       |

---

## Autenticação e Autorização

Este projeto utiliza **OAuth2** para autenticação e autorização, configurado no **API Gateway** com Spring Security. A validação dos tokens é realizada por meio do **Keycloak**, que atua como servidor de autorização e resource server.

### Fluxo de Autenticação

1. O cliente realiza a autenticação no Keycloak e obtém um token JWT.
2. Todas as requisições ao API Gateway devem incluir o token no cabeçalho `Authorization: Bearer <token>`.
3. O API Gateway, configurado como **Resource Server**, valida o token JWT com o Keycloak, garantindo a autenticidade e permissões do usuário.
4. Após validação, o API Gateway encaminha a requisição para os microsserviços autorizados.

### Tecnologias e Configuração

- **Spring Security OAuth2 Resource Server**: Responsável por validar e interpretar os tokens JWT recebidos.
- **Keycloak**: Gerencia autenticação, emissão e revogação dos tokens, além do gerenciamento de usuários e roles.
- A integração permite centralizar o controle de acesso, simplificando a segurança e a escalabilidade do sistema.

### Benefícios

- Segurança centralizada via Keycloak.
- Validação eficiente dos tokens no API Gateway.
- Suporte a roles e permissões via claims JWT.
- Facilidade para escalabilidade e manutenção da arquitetura de microsserviços.

---


# Processo de Build e Deploy

O projeto conta com um script automatizado (`deploy.ps1`) para facilitar o processo de build e deploy dos microserviços. Abaixo estão os passos executados pelo script:

## Estrutura Esperada
Cada microserviço deve estar localizado na pasta `services/<nome-do-serviço>`, contendo um projeto Gradle com o arquivo `gradlew.bat`.

## Etapas do Processo

1. **Limpeza de Builds Anteriores**  
   O script inicia limpando os builds anteriores com o comando `clean`, garantindo que não haja arquivos obsoletos.

2. **Build dos Microserviços**  
   Para cada microserviço definido no array `$services`, o script:
   - Navega até o diretório do serviço
   - Executa o comando `./gradlew.bat clean build -x test` para compilar o projeto (ignorando os testes)
   - Aguarda a criação do arquivo JAR final (`build/libs/app.jar`)

3. **Verificação de Build**  
   Um mecanismo de espera (`Wait-ForFile`) é usado para garantir que o JAR foi corretamente gerado antes de seguir para o próximo serviço. Isso evita falhas de build silenciosas.

4. **Subida dos Containers**  
   Após a geração de todos os JARs, o script executa:

   ```bash
   docker-compose up --build -d
   ```

   Isso cria ou atualiza as imagens Docker dos serviços e inicia todos os containers em segundo plano.

5. **Verificação de Saúde dos Containers**  
   O script aguarda que todos os containers definidos fiquem com status `"running"` e `"healthy"` antes de concluir o processo. Containers verificados:

   - `ms-configserver`
   - `ms-discovery`
   - `ms-gateway`
   - `ms-pedidos`
   - `ms-produtos`
   - `keycloak-ms`

## Resultado Esperado

Ao final da execução, todos os serviços estarão:

- Buildados com sucesso
- Empacotados como imagens Docker
- Rodando em containers saudáveis e prontos para uso

## Requisitos

- PowerShell (Windows)
- Docker e Docker Compose instalados e configurados
- Java e Gradle compatíveis em cada microserviço
- Estrutura de pastas e nomes dos JARs conforme esperado pelo script

## Como executar o deploy

Abra o PowerShell na raiz do projeto e execute:

```powershell
.\deploy.ps1
```

O script irá:

- Buildar todos os microserviços
- Gerar as imagens Docker
- Subir os containers em modo detached
- Aguardar que todos os containers fiquem saudáveis

Se algum erro ocorrer durante o processo, o script exibirá uma mensagem e abortará a execução.

---
