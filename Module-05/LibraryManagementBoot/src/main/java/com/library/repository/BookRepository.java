package com.library.repository;

import com.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Exercise 9: BookRepository interface extending JpaRepository
 * Spring Data JPA auto-generates CRUD implementations
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Find books by author name (Spring Data derived query)
     */
    List<Book> findByAuthor(String author);

    /**
     * Find book by ISBN
     */
    Optional<Book> findByIsbn(String isbn);

    /**
     * Find all available books
     */
    List<Book> findByAvailable(boolean available);

    /**
     * Find books by title containing a keyword (case insensitive)
     */
    List<Book> findByTitleContainingIgnoreCase(String keyword);
}
