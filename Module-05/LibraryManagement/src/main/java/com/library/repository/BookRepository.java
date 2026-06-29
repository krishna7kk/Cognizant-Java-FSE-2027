package com.library.repository;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

/**
 * Exercise 1: BookRepository class in com.library.repository package
 * Exercise 6: Annotated with @Repository for component scanning
 */
@Repository
public class BookRepository {

    private List<String> books = new ArrayList<>();

    public BookRepository() {
        // Initialize with sample data
        books.add("The Spring Framework in Action");
        books.add("Clean Code by Robert C. Martin");
        books.add("Design Patterns by Gang of Four");
        System.out.println("BookRepository: Initialized with " + books.size() + " books.");
    }

    /**
     * Retrieve all books from the repository.
     */
    public List<String> findAllBooks() {
        System.out.println("BookRepository: Fetching all books from repository.");
        return books;
    }

    /**
     * Add a book to the repository.
     */
    public void addBook(String bookTitle) {
        books.add(bookTitle);
        System.out.println("BookRepository: Added book - " + bookTitle);
    }

    /**
     * Find a book by title.
     */
    public String findBookByTitle(String title) {
        return books.stream()
                .filter(b -> b.equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }
}
