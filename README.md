# Documentação do Projeto

## Introdução

Este projeto é um sistema de catálogo de livros que permite aos usuários gerenciar e navegar por livros. Ele inclui recursos como adicionar, atualizar e excluir livros. O sistema também fornece endpoints para recuperar livros, tanto paginados quanto ordenados por nome.

## Tecnologias Utilizadas

O projeto é construído usando Java e o framework Spring Boot. Os dados são armazenados em um banco de dados relacional acessado pelo Spring Data JPA. Os testes são escritos usando JUnit e Mockito.

## Executando a Aplicação

Para executar a aplicação, certifique-se de ter o Java e um banco de dados suportado (por exemplo, PostgreSQL, MySQL) instalados e configurados corretamente. Em seguida, siga estes passos:

1. Clone o repositório para o seu computador local.
2. Configure as propriedades de conexão do banco de dados no arquivo `application.properties`.
3. Execute a aplicação usando o plugin Maven fornecido pelo Spring Boot.

## Estrutura do Projeto

O projeto possui os seguintes componentes principais:

1. `com.bookCatalog.bookcatalog.entities`: Contém as classes de entidade que representam os livros.
2. `com.bookCatalog.bookcatalog.dto`: Contém os Objetos de Transferência de Dados (DTOs) usados para a troca de dados na API.
3. `com.bookCatalog.bookcatalog.repositories`: Contém os repositórios do Spring Data JPA para as entidades de livros.
4. `com.bookCatalog.bookcatalog.resources`: Contém os controladores REST que lidam com os endpoints da API.
5. `com.bookCatalog.bookcatalog.services`: Contém as classes de serviço que lidam com a lógica de negócios.
6. `com.bookCatalog.bookcatalog.tests`: Contém as classes de utilidade de teste usadas para criar dados de teste.
7. `com.bookCatalog.bookcatalog.exceptions`: Contém as classes de exceção personalizadas usadas na aplicação.

## Executando os Testes

Para executar os testes, execute o seguinte comando:

```bash
mvn test
```

## Cobertura de Testes

O projeto possui uma cobertura de testes abrangente, incluindo testes unitários para os repositórios, serviços e classes de recursos. Além disso, inclui testes de integração para testar a interação entre as camadas da aplicação.

## BookRepositoryTests

A classe `BookRepositoryTests` contém testes para a classe `BookRepository`. Ela utiliza a anotação `@DataJpaTest` para habilitar a configuração específica do JPA para testes. Os testes incluem:

1. `saveShouldPersistWithAutoincrementWhenIdIsNull`: Verifica se um livro pode ser salvo no banco de dados e se o ID é atribuído automaticamente.
2. `deleteShouldDeleteObjectWhenIdExists`: Verifica se um livro pode ser excluído com sucesso quando o ID existe.
3. `deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist`: Verifica se uma exceção é lançada ao tentar excluir um livro que não existe.

## BookResourceIT

A classe `BookResourceIT` contém testes de integração para a classe `BookResource`, que lida com os endpoints da API relacionados a livros. Ela utiliza as anotações `@SpringBootTest` e `@AutoConfigureMockMvc` para habilitar o contexto completo do Spring e configurar automaticamente a instância `MockMvc` para testes. Os testes incluem:

1. `findAllShouldReturnSortedPageWhenSortByName`: Verifica se os livros são retornados em ordem quando ordenados por nome.
2. `updateShouldReturnBookDTOWhenIdExists`: Verifica se um livro pode ser atualizado quando o ID existe.
3. `updateShouldReturnNotFoundWhenIdDoesNotExist`: Verifica se é retornado um status de não encontrado ao tentar atualizar um livro que não existe.

## BookResourceTests

A classe `BookResourceTests` contém testes unitários para a classe `BookResource`. Ela utiliza a anotação `@WebMvcTest` para habilitar apenas a configuração web relacionada ao Spring para testes e `@MockBean` para simular o `BookService`. Os testes incluem:

1. `deleteShouldReturnNoContentWhenIdExists`: Verifica se uma operação de exclusão bem-sucedida retorna um status de nenhum conteúdo.
2. `deleteShouldReturnNotFoundWhenIdDoesNotExist`: Verifica se é retornado um status de não encontrado ao tentar excluir um livro que não existe.
3. `insertShouldReturnBookDTOCreated`: Verifica se um livro pode ser criado com sucesso usando a API.
4. `updateShouldReturnBookDTOWhenIdExists`: Verifica se um livro pode ser atualizado usando a API quando o ID existe.
5. `updateShouldReturnNotFoundWhenIdDoesNotExist`: Verifica se é retornado um status de não encontrado ao tentar atualizar um livro que não existe.
6. `findAllShouldReturnPage`: Verifica se o endpoint da API para recuperar todos os livros retorna uma página de livros.
7. `findByIdShouldReturnBookWhenIdExists`: Verifica se o endpoint da API para recuperar um livro por ID retorna o livro correto.
8. `findByIdShouldReturnNotFoundWhenIdDoesNotExist`: Verifica se é retornado um status de não encontrado ao tentar recuperar um livro que não existe.

## BookServiceIT

A classe `BookServiceIT` contém testes de integração para a classe `BookService`, que lida com a lógica de negócios relacionada a livros. Ela utiliza a anotação `@SpringBootTest` para habilitar o contexto completo do Spring e interagir com o banco de dados real. Os testes incluem:

1. `deleteShouldDeleteResourceWhenIdExists`: Verifica se um livro pode ser excluído com sucesso quando o ID existe.
2. `deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist`: Verifica se uma exceção é lançada ao tentar excluir um livro que não existe.
3. `findAllPagedShouldReturnPage`: Verifica se o método de serviço para recuperar livros paginados retorna uma página válida.
4. `findAllPagedShouldReturnEmptyPageWhenPageDoesNotExist`: Verifica se é retornada uma página vazia quando a página solicitada não existe.
5. `findAllPagedShouldReturnSortedPageWhenSortByName`: Verifica se o método de serviço para recuperar livros paginados e ordenados retorna uma página ordenada.

## BookServiceTests

A classe `BookServiceTests` contém testes unitários para a classe `BookService`. Ela utiliza a anotação `@ExtendWith(SpringExtension.class)` juntamente com `@Mock` e `@InjectMocks` para simular

 as dependências e injetá-las no serviço. Os testes incluem:

1. `findAllPagedShouldReturnPage`: Verifica se o método de serviço para recuperar livros paginados retorna uma página válida.
2. `deleteShouldThrowDatabaseExceptionWhenDependentId`: Verifica se é lançada uma exceção de `DatabaseException` ao tentar excluir um livro com entidades dependentes.
3. `deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist`: Verifica se é lançada uma exceção de `ResourceNotFoundException` ao tentar excluir um livro que não existe.
4. `deleteShouldDoNothingWhenIdExists`: Verifica se um livro pode ser excluído com sucesso quando o ID existe.

## Conclusão

O projeto possui uma cobertura de testes completa, incluindo testes unitários e de integração para garantir a correção da aplicação. Com a suíte abrangente de testes, você pode fazer alterações e adições ao código com confiança, garantindo a estabilidade e confiabilidade da aplicação.
