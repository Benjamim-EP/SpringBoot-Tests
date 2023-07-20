package com.bookCatalog.bookcatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.bookCatalog.bookcatalog.tests.Factory;
import com.devsuperior.bookcatalog.entities.Book;
import com.devsuperior.bookcatalog.repositories.BookRepository;

@DataJpaTest
public class BookRepositoryTests {

	@Autowired
	private BookRepository repository;
	
	private long existingId;
	private long nonExistingId;
	private long countTotalBooks;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		countTotalBooks = 25L;
	}
	
	@Test
	public void saveShouldPersistWithAutoincrementWhenIdIsNull() {

		Book book = Factory.createBook();
		book.setId(null);
		
		book = repository.save(book);
		Optional<Book> result = repository.findById(book.getId());
		
		Assertions.assertNotNull(book.getId());
		Assertions.assertEquals(countTotalBooks + 1L, book.getId());
		Assertions.assertTrue(result.isPresent());
		Assertions.assertSame(result.get(), book);
	}
	
	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		
		repository.deleteById(existingId);

		Optional<Book> result = repository.findById(existingId);
		
		Assertions.assertFalse(result.isPresent());
	}
	
	@Test
	public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {

		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(nonExistingId);			
		});
	}
}
