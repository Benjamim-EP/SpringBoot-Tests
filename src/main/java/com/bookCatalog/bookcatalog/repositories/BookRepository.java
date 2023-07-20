package com.bookCatalog.bookcatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookCatalog.bookcatalog.entities.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
