# Plataforma EAD - Serviço de Usuários
  Esta API REST permite o gerenciamento de usuários, oferecendo funcionalidades de cadastro, autenticação e controle de 
permissões através de tokens JWT. O sistema diferencia os usuários em diferentes níveis de acesso, incluindo:

• Usuário Comum: Acesso limitado a funcionalidades básicas.

• Administrador: Acesso a funcionalidades administrativas, como gestão de usuários. 
A API segue as melhores práticas do estilo arquitetural RESTful, garantindo segurança, escalabilidade e facilidade de uso.

• Instrutor: Acesso às funcionalidades destinadas à gestão e acompanhamento de usuários.

## 🔧 Tecnologias e Técnicas Utilizadas
- Java 21
- IDE: IntelliJ
- Maven
- Configuração com arquivo yaml
- Spring Boot 3.3.5
- Spring MVC
- Arquitetura REST
- Composition Pattern 
- Comunicação Síncrona com Rest Client
- Spring Security 
- Token JWT com uso de ROLEs e criptografia de senhas na base de dados 
- Spring Data JPA
- Hibernate
- ORM
- Uso de DTOs e visualização por campos com anotação @JsonView
- PostgreSQL
- Validations
- Specifications
- Filtros avançados e dinâmicos
- Tratamento global de exceções com ExceptionHandler
- Log4j2
- Lombok
- Swagger
- Configuração de CORS
- Configuração padrão de data (ISO 8601 UTC)
- Configuração de Paginação e serialização

## Funcionalidades Implementadas
- Cadastro de usuários
- Atualização de usuários
- Exclusão de usuários
- Listagem e busca por usuários
- Verificações e validações customizadas
- Autenticação de usuários com geração de Token JWT
- Comunicação segura entre serviços (REST Client)
- Respostas padronizadas de erro e sucesso
- Implementação de filtros por campos

## Como Executar Localmente
1- Pré-requisitos
- Java 21+
- Maven 3.9+
- PostgreSQL

2- Configure seu banco de dados no application.yml:
  spring:
    datasource:
      url: jdbc:postgresql://localhost:5432/seu_banco
      username: seu_usuario
      password: sua_senha

3- Execute a aplicação: No terminal, dentro do diretório do projeto, execute:
./mvnw spring-boot:run


## Documentação da API com seus endpoints:
Após subir o serviço, acesse o link abaixo para consultar a documentação completa dos endpoints 
disponíveis na aplicação:
http://localhost:8087/plataforma-ead-usuario/swagger-ui/swagger-ui/index.html#/

## Através dessa documentação, você poderá:

1. Explorar os endpoints da API.
2. Realizar testes interativos diretamente pela interface Swagger.
3. Verificar detalhes sobre os métodos HTTP utilizados, parâmetros, e exemplos de respostas.

A documentação está estruturada de forma clara, facilitando a navegação e interação com os recursos da aplicação.

## 💡 ATENÇÃO
1. Certifique-se de configurar o banco de dados corretamente no arquivo application.yml.
2. Verifique se as versões de Java e Maven são compatíveis.

