/**
 * Esta classe contém os casos de teste para a classe BookResource, que lida
 * com as requisições HTTP relacionadas aos livros na aplicação Book Catalog.
 * Testes de unidade
 */
package com.bookCatalog.bookcatalog.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.bookCatalog.bookcatalog.dto.BookDTO;
import com.bookCatalog.bookcatalog.resources.BookResource;
import com.bookCatalog.bookcatalog.services.BookService;
import com.bookCatalog.bookcatalog.services.exceptions.DatabaseException;
import com.bookCatalog.bookcatalog.services.exceptions.ResourceNotFoundException;
import com.bookCatalog.bookcatalog.tests.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(BookResource.class)
public class BookResourceTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService service;

    @Autowired
    private ObjectMapper objectMapper;

    private Long existingId;
    private Long nonExistingId;
    private Long dependentId;
    private BookDTO bookDTO;
    private PageImpl<BookDTO> page;

    @BeforeEach
    void setUp() throws Exception {

        // Inicializa dados de teste
        existingId = 1L;
        nonExistingId = 2L;
        dependentId = 3L;

        bookDTO = Factory.createBookDTO();
        page = new PageImpl<>(List.of(bookDTO));

        // Configura o comportamento simulado do serviço para cada caso de teste
        when(service.findAllPaged(any())).thenReturn(page);
        when(service.findById(existingId)).thenReturn(bookDTO);
        when(service.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);
        when(service.insert(any())).thenReturn(bookDTO);
        when(service.update(eq(existingId), any())).thenReturn(bookDTO);
        when(service.update(eq(nonExistingId), any())).thenThrow(ResourceNotFoundException.class);
        doNothing().when(service).delete(existingId);
        doThrow(ResourceNotFoundException.class).when(service).delete(nonExistingId);
        doThrow(DatabaseException.class).when(service).delete(dependentId);
    }

    /**
     * Caso de teste para excluir um livro quando o ID existe no banco de dados.
     *
     * @throws Exception se ocorrer um erro durante o teste.
     */
    @Test
    public void deleteShouldReturnNoContentWhenIdExists() throws Exception {

        ResultActions result =
                mockMvc.perform(delete("/books/{id}", existingId)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNoContent());
    }

    /**
     * Caso de teste para excluir um livro quando o ID não existe no banco de dados.
     *
     * @throws Exception se ocorrer um erro durante o teste.
     */
    @Test
    public void deleteShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {

        ResultActions result =
                mockMvc.perform(delete("/books/{id}", nonExistingId)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    /**
     * Caso de teste para inserir um novo livro e esperar uma resposta de criação bem-sucedida.
     *
     * @throws Exception se ocorrer um erro durante o teste.
     */
    @Test
    public void insertShouldReturnBookDTOCreated() throws Exception {

        String jsonBody = objectMapper.writeValueAsString(bookDTO);

        ResultActions result =
                mockMvc.perform(post("/books")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.description").exists());
    }

    /**
     * Caso de teste para atualizar um livro quando o ID existe no banco de dados e esperar uma resposta de atualização bem-sucedida.
     *
     * @throws Exception se ocorrer um erro durante o teste.
     */
    @Test
    public void updateShouldReturnBookDTOWhenIdExists() throws Exception {

        String jsonBody = objectMapper.writeValueAsString(bookDTO);

        ResultActions result =
                mockMvc.perform(put("/books/{id}", existingId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.description").exists());
    }

    /**
     * Caso de teste para atualizar um livro quando o ID não existe no banco de dados e esperar uma resposta de não encontrado.
     *
     * @throws Exception se ocorrer um erro durante o teste.
     */
    @Test
    public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {

        String jsonBody = objectMapper.writeValueAsString(bookDTO);

        ResultActions result =
                mockMvc.perform(put("/books/{id}", nonExistingId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    /**
     * Caso de teste para recuperar todos os livros e esperar uma resposta bem-sucedida com uma lista de livros (paginada).
     *
     * @throws Exception se ocorrer um erro durante o teste.
     */
    @Test
    public void findAllShouldReturnPage() throws Exception {

        ResultActions result =
                mockMvc.perform(get("/books")
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

    /**
     * Caso de teste para recuperar um livro pelo ID quando ele existe no banco de dados e esperar uma resposta bem-sucedida.
     *
     * @throws Exception se ocorrer um erro durante o teste.
     */
    @Test
    public void findByIdShouldReturnBookWhenIdExists() throws Exception {

        ResultActions result =
                mockMvc.perform(get("/books/{id}", existingId)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.description").exists());
    }

    /**
     * Caso de teste para recuperar um livro pelo ID quando ele não existe no banco de dados e esperar uma resposta de não encontrado.
     *
     * @throws Exception se ocorrer um erro durante o teste.
     */
    @Test
    public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {

        ResultActions result =
                mockMvc.perform(get("/books/{id}", nonExistingId)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }
}
