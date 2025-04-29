<h1 align="center" style="font-weight: bold;">Comunicação Síncrona com Composition Pattern e Rest-Client entre os Microsserviços de Usuário e Curso. 💻</h1>

<p align="center">
  <a href="#tecnologias">Tecnologias</a> • 
  <a href="#started">Introdução</a> • 
  <a href="#routes">API Endpoints</a> •
  <a href="#colab">Colaboradores</a> •
  <a href="#contribute">Contribuir</a>
</p>

<p align="center">
  <b>Este projeto foi desenvolvido utilizando o padrão de composição (Composition Pattern) para estruturar objetos complexos de forma flexível e reutilizável. A comunicação entre os microsserviços de Usuário e Curso é realizada de forma síncrona através de Rest Client, permitindo o gerenciamento de matrículas de usuários em cursos de maneira eficiente e organizada.</b>
</p>

<h3>🔍 O que é o Composition Pattern?</h3>
<p>O Composition Pattern (Padrão de Composição) é um padrão estrutural que permite compor objetos mais complexos a partir de objetos mais simples. Ele é uma alternativa ao padrão Inheritance (herança) para evitar problemas comuns como a rigidez de hierarquias e o alto acoplamento. No Composition Pattern, objetos podem ser compostos dinamicamente, mantendo um baixo nível de acoplamento e aumentando a flexibilidade da aplicação.</p>

<h3>🔍 O que é Comunicação Síncrona com Rest Client?</h3>
<p>Comunicação síncrona significa que a aplicação que está fazendo a requisição espera pela resposta antes de continuar a execução. Em um sistema distribuído, isso é comum quando você precisa integrar com APIs externas de forma imediata, como em uma consulta de dados em tempo real.</p>

<h2 id="tecnologias">💻 Tecnologias usadas</h2>

<ul>
  <li>Java 21</li>
  <li>IDE: IntelliJ</li>
  <li>Maven</li>
  <li>Configuração com arquivo YAML</li>
  <li>Spring Boot 3.3.5</li>
  <li>Spring MVC</li>
  <li>Arquitetura REST</li>
  <li>Composition Pattern</li>
  <li>Comunicação Síncrona com Rest Client</li>
  <li>Spring Security</li>
  <li>Token JWT com uso de ROLEs e criptografia de senhas na base de dados</li>
  <li>Spring Data JPA</li>
  <li>Hibernate</li>
  <li>ORM</li>
  <li>Uso de DTOs e visualização por campos com anotação @JsonView</li>
  <li>PostgreSQL</li>
  <li>Validações</li>
  <li>Specifications</li>
  <li>Filtros avançados e dinâmicos</li>
  <li>Tratamento global de exceções com ExceptionHandler</li>
  <li>Log4j2</li>
  <li>Lombok</li>
  <li>Swagger</li>
  <li>Configuração de CORS</li>
  <li>Configuração padrão de data (ISO 8601 UTC)</li>
  <li>Configuração de Paginação e serialização</li>
</ul>

<h2 id="started">🚀 Primeiros passos</h2>

<h3>Como executar o projeto localmente?</h3>

<h4>Pré-requisitos</h4>
<ul>
  <li>Java 21+</li>
  <li>Maven 3.3.5</li>
  <li>PostgreSQL</li>
</ul>

<h4>Passos para rodar o projeto</h4>
<p>1- Configure seu banco de dados no <code>application.yml</code>:</p>

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/seu_banco
    username: seu_usuario
    password: sua_senha
```

3- Execute a aplicação: No terminal, dentro do diretório do projeto, execute:
```cmd
 ./mvnw spring-boot:run
```
<h3 >✅ Pontos importantes para execução da aplicação:</h3>

1. Execute os dois microsserviços:
Ambos os microsserviços (Usuário e Curso) devem estar rodando simultaneamente para o correto funcionamento da comunicação síncrona.

2. Verifique as versões das dependências:
Confirme se as versões das bibliotecas e ferramentas utilizadas (Spring Boot, Java, etc.) são compatíveis com o projeto. Recomenda-se utilizar as mesmas versões descritas no pom.xml de cada módulo.

3. Acesse as documentações Swagger para testar as APIs:
Após subir os microsserviços, utilize as interfaces do Swagger para explorar e testar os endpoints:

4. Microsserviço de Usuário:
➔ http://localhost:8087/plataforma-ead-usuario/swagger-ui/swagger-ui/index.html#/

5. Microsserviço de Curso (Formação):
➔ http://localhost:8082/plataforma-ead-formacao/swagger-ui/swagger-ui/index.html#/

6. Banco de dados:
Certifique-se que o banco de dados (por exemplo, PostgreSQL ou outro configurado) está em execução e que as credenciais estão corretas no application.yml ou application.properties de cada microsserviço.

7. Portas configuradas:
Garanta que as portas 8087 e 8082 estão livres no seu ambiente local. Caso estejam ocupadas, altere a configuração nos arquivos de propriedades dos projetos.

9. Testes de Integração:
Após ambos os serviços estarem rodando, realize testes de criação de usuários, criação de cursos e matrícula de usuários em cursos para validar toda a comunicação síncrona.

10. Ambiente Java:
Este projeto foi desenvolvido com Java 17+. Certifique-se de ter a versão correta instalada no seu ambiente.

11. Observação sobre Rest Client:
A comunicação entre os serviços foi feita utilizando o novo RestClient do Spring 6.1+, em vez do RestTemplate (que está sendo descontinuado).


<h2 id="routes">📍 Endpoints da API de usuarios </h2>
​http://localhost:8087/plataforma-ead-usuario

| Rotas da aplicação              | Descrições                                          
|----------------------|-----------------------------------------------------
| <kbd>POST /autenticacao/login</kbd>     | Fazer login [response details](#post-usuarios-login)
| <kbd>POST /autenticacao/registro</kbd>     | Cadastra usuários na base de dados [response details](#post-usuarios)
| <kbd>GET /usuarios</kbd>     | Consulta geral de usuários [response details](#get-usuarios)
| <kbd>GET /usuarios/d7ad8dc3-642e-4f08-9764-c17343dc9e95 </kbd>     | Consulta os dados de um usuário especifico [response details](#get-usuarios-id)
| <kbd>DELETE /usuarios/5cb36bf1-31e5-49ee-be30-470f9091486e    | Deleta os dados do usuário presente na base de dados [response details](#delete-usuario-id)
| <kbd>PUT /usuarios/5cb36bf1-31e5-49ee-be30-470f9091486e/usuario</kbd>     | Atualiza dados gerais do usuário [response details](#put-usuario-id)
| <kbd>PUT /instrutor/inscricao</kbd>     | Tornar o usuario um instrutor [response details](#put-usuario-instrutor)
| <kbd>PATCH /usuarios/5cb36bf1-31e5-49ee-be30-470f9091486e/imagem</kbd>     | Atualiza a imagem do usuário [response details](#patch-imagem-id)
| <kbd>PATCH /usuarios/d7ad8dc3-642e-4f08-9764-c17343dc9e95/status</kbd>     | Atualiza o status do usuário [response details](#patch-status-id)
| <kbd>PATCH /usuarios/d7ad8dc3-642e-4f08-9764-c17343dc9e95/senha</kbd>     | Atualiza a senha do usuário [response details](#patch-senha-id)


<h3 id="post-usuarios-login">POST /autenticacao/login</h3>

**RESPONSE**
```json
{
  "nome": "Miguel",
  "senha": "@Miguel1234"
}
```

<h3 id="post-usuarios">POST /autenticacao/registro</h3>

**RESPONSE**
```json
{
"nome": "Miguel",
"email": "miguel@gmail.com",
"senha": "@Miguel1234",
"nomeCompleto":"Miguel Marques da Silva",
"telefone":"(81)9 98563458",
"imagemUrl":"09881837131"
}
```

<h3 id="get-usuarios">GET /usuarios</h3>

**REQUEST**
```json
{
    "content": [
        {
            "usuarioId": "6a6e04ae-a00b-43d4-b076-98f7a547e80f",
            "nome": "Miguel",
            "email": "miguel@gmail.com",
            "nomeCompleto": "Miguel Marques da Silva",
            "statusUsuario": "ATIVO",
            "tipoUsuario": "USUARIO",
            "dataCriacao": "“2025-04-28T12:42:05Z",
            "dataAtualizacao": "“2025-04-28T12:42:05Z",
            "links": [
                {
                    "rel": "self",
                    "href": "http://localhost:8087/plataforma-ead-usuario/usuarios/6a6e04ae-a00b-43d4-b076-98f7a547e80f"
                }
            ]
        }
}
```
<h3 id="get-usuarios-id">GET /usuarios/d7ad8dc3-642e-4f08-9764-c17343dc9e95</h3>

**REQUEST**
```json
{
    "usuarioId": "6a6e04ae-a00b-43d4-b076-98f7a547e80f",
    "nome": "Miguel",
    "email": "miguel@gmail.com",
    "nomeCompleto": "Miguel Marques da Silva",
    "statusUsuario": "ATIVO",
    "tipoUsuario": "USUARIO",
    "dataCriacao": "“2025-04-28T12:42:05Z",
    "dataAtualizacao": "“2025-04-28T12:42:05Z",
    "links": []
}
```

<h3 id="delete-usuario-id">DELETE /usuarios/d7ad8dc3-642e-4f08-9764-c17343dc9e95</h3>

**REQUEST**
```json
Usuário deletado com sucesso!
```

<h3 id="put-usuarios-id">PUT /usuarios/d7ad8dc3-642e-4f08-9764-c17343dc9e95</h3>

**REQUEST**
```json
{
    "nomeCompleto":"Ana Gabriela da Silva",
    "telefone":"8906296",
    "nome":"Gabriela"
}
```

<h3 id="patch-status-id">PUT /usuarios/d7ad8dc3-642e-4f08-9764-c17343dc9e95/status</h3>

**REQUEST**
```json
{
    "statusUsuario":"ATIVO"
}
```

<h3 id="patch-imagem-id">PUT /usuarios/d7ad8dc3-642e-4f08-9764-c17343dc9e95/imagem</h3>

**REQUEST**
```json
{
  "imagemUrl":"1234567899969967"  
}
```

<h3 id="patch-senha-id">PUT /usuarios/d7ad8dc3-642e-4f08-9764-c17343dc9e95/senha</h3>

**REQUEST**
```json
{
    "senhaAntiga":"@Ana1234",
    "senha":"@Ana123"
}
```

<h3 id="patch-senha-id">PUT /instrutor/inscricao</h3>

**REQUEST**
```json
{
    "usuarioId":"1dd6ba35-22ee-4aa9-a851-720fd841e231"
}
```

<h2 id="routes">📍 Endpoints da API de formacao (CURSOS) </h2>
​http://localhost:8082/plataforma-ead-formacao

| Rotas da aplicação              | Descrições                                          
|----------------------|-----------------------------------------------------
| <kbd>POST /cursos</kbd>     | Cadastrar cursos na base de dados [response details](#post-cursos)
| <kbd>GET /cursos</kbd>     | Consulta geral de cursos [response details](#get-cursos)
| <kbd>GET /cursos/c0bfe273-a48e-4214-84df-7b51355fb33b </kbd>     | Consulta os dados de um curso especifico [response details](#get-cursos-id)
| <kbd>DELETE /cursos/c0bfe273-a48e-4214-84df-7b51355fb33b  | Deleta os dados do curso presente na base de dados [response details](#delete-cursos-id)
| <kbd>PUT cursos/c3de620a-fa9d-4b41-9e85-41e06fbab36c</kbd>     | Atualiza dados gerais do curso [response details](#put-cursos-id)

<h3 id="post-cursos">POST /cursos</h3>

**REQUEST**
```json
{
    "nome": "Desenvolvimento Back-end.",
    "descricao": "Entenda os princípios de projetos Back-end com Java e Spring.",
    "cursoStatus": "CURSANDO",
    "duracaoCurso": "12 meses",
    "cursoNivel": "INTERMEDIARIO",
    "instrutor": "1dd6ba35-22ee-4aa9-a851-720fd841e231" // Instrutor é um tipo de usuário
}
```
<h3 id="get-cursos">GET /cursos</h3>

**REQUEST**
```json
{
    "content": [
        {
            "cursoId": "7d888076-9095-4074-bbbb-49d3c36492cc",
            "nome": "Desenvolvimento Back-end.",
            "descricao": "Entenda os princípios de projetos Back-end com Java e Spring.",
            "cursoStatus": "CURSANDO",
            "duracaoCurso": "12 meses",
            "cursoNivel": "INTERMEDIARIO",
            "instrutor": "1dd6ba35-22ee-4aa9-a851-720fd841e231" // Instrutor é um tipo de usuário
        }
}
```
<h3 id="get-cursos-id">GET /cursos/id</h3>

**REQUEST**
```json
{
    "cursoId": "7d888076-9095-4074-bbbb-49d3c36492cc",
    "nome": "Desenvolvimento Web",
    "duracaoCurso": "12 meses",
    "dataInicio": "28-04-2025 11:53:31",
    "dataAtualizacao": "“2025-04-28T11:53:31Z",
    "cursoStatus": "CURSANDO",
    "cursoNivel": "INTERMEDIARIO",
    "instrutor": "1dd6ba35-22ee-4aa9-a851-720fd841e231",
    "descricao": "Entenda os princípios de projetos Back-end."
}
```
<h3 id="delete-cursos-id">DELETE /cursos/id</h3>

**REQUEST**
```cmd
{
  CURSO DELETADO COM SUCESSO!
}
```
<h3 id="put-cursos-id">PUT /cursos/id</h3>

**REQUEST**
```cmd
{
    "nome": "Deso Back-End",
    "descricao": "O curso é voltado para a pratica do mercado",
    "cursoStatus":"CURSANDO",
    "duracaoCurso":"2 anos de duração",
    "cursoNivel":"AVANCADO",
    "instrutor":"ed15500b-aa72-4ea5-9d57-1e7c6c8310c3"
}
```

<h2 id="routes">📍 Endpoints da API de formacao (MODULOS) </h2>
​http://localhost:8082/plataforma-ead-formacao

| Rotas da aplicação              | Descrições                                          
|----------------------|-----------------------------------------------------
| <kbd>POST curso/49ae4c8b-1c71-4aa6-81af-317bea50796c/modulos</kbd>     | Cadastrar um modulo dentro de um curso especifico [response details](#post-modulo)
| <kbd>GET /curso/c3de620a-fa9d-4b41-9e85-41e06fbab36c/modulos</kbd>     | Consulta geral de modulos de um determinado curso [response details](#get-modulo)
| <kbd>GET /curso/c3de620a-fa9d-4b41-9e85-41e06fbab36c/modulos/0fa7fd32-d050-45b6-8608-d9b2f888b5b1</kbd>     | Consulta os dados de um modulo especifico [response details](#get-cursos-id)
| <kbd>DELETE /curso/c3de620a-fa9d-4b41-9e85-41e06fbab36c/modulo/0fa7fd32-d050-45b6-8608-d9b2f888b5b1  | Deleta os dados de um modulo especifico [response details](#delete-modulo-id)
| <kbd>PUT /curso/c3de620a-fa9d-4b41-9e85-41e06fbab36c/modulo/3feaead7-6070-48f4-aff9-4d1309c683e8</kbd>     | Atualiza dados gerais do modulo [response details](#put-modulo-id)

<h3 id="post-modulo">POST /cursos/id/modulo</h3>

**REQUEST**
```JSON
{
    "titulo": "Desenvolvimento Front-End com React",
    "descricao": "Neste módulo, os alunos aprenderão os conceitos fundamentais de React, incluindo componentes, hooks, gerenciamento de estado e integração com APIs, preparando-os para o desenvolvimento de aplicações web modernas e interativas."
}

```

<h3 id="get-modulo">GET /cursos/id/modulos</h3>

**REQUEST**
```JSON
{
    "content": [
        {
            "moduloId": "ce8dcb32-c68c-42a1-b105-a6147c5fcd47",
            "titulo": "Desenvolvimento Front-End com React",
            "descricao": "Neste módulo, os alunos aprenderão os conceitos fundamentais de React, incluindo componentes, hooks, gerenciamento de estado e integração com APIs, preparando-os para o desenvolvimento de aplicações web modernas e interativas.",
            "dataCriacao": "“2025-04-28T16:35:29Z"
        }
    ],
    "page": {
        "size": 10,
        "number": 0,
        "totalElements": 1,
        "totalPages": 1
    }
}

```

<h3 id="get-modulo-id">GET /cursos/id/modulos/id</h3>

**REQUEST**
```JSON
{
    "moduloId": "ce8dcb32-c68c-42a1-b105-a6147c5fcd47",
    "titulo": "Desenvolvimento Front-End com React",
    "descricao": "Neste módulo, os alunos aprenderão os conceitos fundamentais de React, incluindo componentes, hooks, gerenciamento de estado e integração com APIs, preparando-os para o desenvolvimento de aplicações web modernas e interativas.",
    "dataCriacao": "“2025-04-28T16:35:29Z"
}
```

<h3 id="delete-modulo-id">DELETE /cursos/id/modulos/id</h3>

**REQUEST**
```cmd
MÓDULO DELETADO COM SUCESSO!
```

<h3 id="put-modulo-id">PUT /cursos/id/modulo/id/id</h3>

**REQUEST**
```JSON
{
    "moduloId": "0926ab79-cbfb-45f8-a9de-9c4f1fb4c383",
    "titulo": "Dev Back-End",
    "descricao": "Testando",
    "dataCriacao": "“2025-04-28T16:47:52Z",
    "dataAtualizacao": "“2025-04-28T16:48:06Z"
}
```

<h2 id="routes">📍 Endpoints da API de formacao (LICOES) </h2>
​http://localhost:8082/plataforma-ead-formacao

| Rotas da aplicação              | Descrições                                          
|----------------------|-----------------------------------------------------
| <kbd>POST modulo/c2d20d48-ea06-4104-b0e2-22b8d4937104/licoes</kbd>     | Cadastrar lições dentro de um modulo especifico [response details](#post-licoes)
| <kbd>GET /modulo/3feaead7-6070-48f4-aff9-4d1309c683e8/licoes</kbd>     | Consulta geral de lições de um determinado modulo [response details](#get-licoes)
| <kbd>GET modulo/3feaead7-6070-48f4-aff9-4d1309c683e8/licoes/342903c6-5847-4fc6-be6f-2c64efda31b9</kbd>     | Consulta os dados de uma lição especifica [response details](#get-licoes-id)
| <kbd>DELETE modulo/3feaead7-6070-48f4-aff9-4d1309c683e8/licao/342903c6-5847-4fc6-be6f-2c64efda31b9 | Deleta uma lição especifica [response details](#delete-licoes-id)
| <kbd>PUT modulo/3feaead7-6070-48f4-aff9-4d1309c683e8/licao/342903c6-5847-4fc6-be6f-2c64efda31b9</kbd>     | Atualiza dados de uma lição [response details](#put-licoes-id)

<h3 id="post-licoes">POST /modulo/id/licoes</h3>

**REQUEST**
```JSON
{
    "titulo": "Introdução ao React",
    "descricao": "Neste módulo, você será introduzido ao React, compreendendo o conceito de componentes, JSX e como configurar um ambiente de desenvolvimento para criar sua primeira aplicação React.",
    "videoUrl": "https://www.exemplo.com/video-introducao-ao-react"
}
```
<h3 id="get-licoes">GET /modulo/id/licoes</h3>

**REQUEST**
```JSON
{
    "content": [
        {
            "licaoId": "56d4ba05-b187-490e-a879-630736eb2556",
            "titulo": "Introdução ao React",
            "descricao": "Neste módulo, você será introduzido ao React, compreendendo o conceito de componentes, JSX e como configurar um ambiente de desenvolvimento para criar sua primeira aplicação React.",
            "videoUrl": "https://www.exemplo.com/video-introducao-ao-react",
            "dataInicio": "“2025-04-28T17:06:36Z"
        }
    ],
    "page": {
        "size": 10,
        "number": 0,
        "totalElements": 1,
        "totalPages": 1
    }
}

```

<h3 id="get-licoes">GET /modulo/id/licao/id</h3>

**REQUEST**
```JSON
{
    "licaoId": "56d4ba05-b187-490e-a879-630736eb2556",
    "titulo": "Introdução ao React",
    "descricao": "Neste módulo, você será introduzido ao React, compreendendo o conceito de componentes, JSX e como configurar um ambiente de desenvolvimento para criar sua primeira aplicação React.",
    "videoUrl": "https://www.exemplo.com/video-introducao-ao-react",
    "dataInicio": "“2025-04-28T17:06:36Z"
}
```

<h3 id="delete-licoes-id">DELETE /modulo/id/licao/id</h3>

**REQUEST**
```cmd
LIÇÃO DELETADO COM SUCESSO!
```

<h3 id="put-licoes-id">PUT /modulo/id/licao/id</h3>

**REQUEST**
```JSON
{
    "titulo": "Introdução ao Desenvolvimento Web",
    "descricao": "Nesta lição, vamos abordar os conceitos básicos de HTML, CSS e JavaScript, fundamentais para a criação de páginas web.",
    "videoUrl": "https://www.exemplo.com/videos/introducao-desenvolvimento-web"
}

```
<h2 id="colab">🤝 Colaborador</h2>

<h3 align="center">👋 Olá! Eu sou o José Carlos</h3>

<p align="center">
  <a href="https://github.com/josecarlos09">
    <img src="https://github.com/josecarlos09.png" width="120" alt="José Carlos"/>
  </a>
</p>

<p align="center">
  <a href="https://github.com/josecarlos09">
    <b>GitHub: @josecarlos09</b>
  </a>
</p>

<p align="center">
  💻 Desenvolvedor Back-end | 🚀 Apaixonado por Java, Spring Boot, APIs RESTful e Microserviços
</p>



<h2 id="contribute">📫 Contribua para esté projeto</h2>

Gostaria de convidá-lo a fazer parte deste projeto e ajudar a torná-lo ainda melhor! Seu feedback, ideias e contribuições são extremamente valiosos.

<h2>Como você pode ajudar?</h2>

- **Corrigir bugs:** Se você encontrou algum erro ou comportamento inesperado, ajude a corrigir.

- **Adicionar novas funcionalidades:** Tem alguma ideia para uma nova funcionalidade ou melhoria? Abra um Pull Request!

- **Melhorar a documentação:** Se encontrar algo confuso ou que precisa de mais explicações, sinta-se à vontade para sugerir mudanças.

- **Revisar código:** Ajudar na revisão de código de outros desenvolvedores é sempre muito bem-vindo!

<h3>📝 Boas práticas para contribuir </h3>

1. Respeite o padrão de código já existente.

2. Adicione testes se for necessário para validar sua alteração.

3. Documente novos recursos ou atualizações importantes.
