package com.bookCatalog.bookcatalog.tests;

import java.time.Instant;

import com.bookCatalog.bookcatalog.dto.BookDTO;
import com.bookCatalog.bookcatalog.entities.Book;
import com.bookCatalog.bookcatalog.entities.Category;

public class Factory {
	
	public static Book createBook() {
		Book book = new Book(1L, "Phone", "Good Phone", 800.0, "https://img.com/img.png", Instant.parse("2020-10-20T03:00:00Z"));
		book.getCategories().add(new Category(1L, "Electronics"));
		return book;		
	}
	
	public static BookDTO createBookDTO() {
		Book book = createBook();
		return new BookDTO(book, book.getCategories());
	}
}
