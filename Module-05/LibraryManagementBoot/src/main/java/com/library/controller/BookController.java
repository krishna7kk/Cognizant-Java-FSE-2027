package com.library.controller;

import com.library.entity.Book;
import com.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Exercise 9: BookController - REST Controller for CRUD operations on Book entity
 *
 * Endpoints:
 *  GET    /api/books           - Get all books
 *  GET    /api/books/{id}      - Get book by ID
 *  POST   /api/books           - Create a new book
 *  PUT    /api/books/{id}      - Update a book
 *  DELETE /api/books/{id}      - Delete a book
 *  GET    /api/books/search?keyword=xxx - Search books by title
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    /**
     * GET /api/books - Retrieve all books
     */
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        System.out.println("BookController: GET /api/books - Returning " + books.size() + " books.");
        return ResponseEntity.ok(books);
    }

    /**
     * GET /api/books/{id} - Retrieve a specific book by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            System.out.println("BookController: GET /api/books/" + id + " - Found: " + book.get().getTitle());
            return ResponseEntity.ok(book.get());
        } else {
            System.out.println("BookController: GET /api/books/" + id + " - Not Found.");
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * POST /api/books - Create a new book
     */
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book savedBook = bookRepository.save(book);
        System.out.println("BookController: POST /api/books - Created: " + savedBook.getTitle());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    /**
     * PUT /api/books/{id} - Update an existing book
     */
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setTitle(bookDetails.getTitle());
            book.setAuthor(bookDetails.getAuthor());
            book.setIsbn(bookDetails.getIsbn());
            book.setAvailable(bookDetails.isAvailable());
            Book updatedBook = bookRepository.save(book);
            System.out.println("BookController: PUT /api/books/" + id + " - Updated: " + updatedBook.getTitle());
            return ResponseEntity.ok(updatedBook);
        } else {
            System.out.println("BookController: PUT /api/books/" + id + " - Not Found.");
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE /api/books/{id} - Delete a book
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            System.out.println("BookController: DELETE /api/books/" + id + " - Deleted.");
            return ResponseEntity.noContent().build();
        } else {
            System.out.println("BookController: DELETE /api/books/" + id + " - Not Found.");
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET /api/books/search?keyword=xxx - Search books by title keyword
     */
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam String keyword) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase(keyword);
        System.out.println("BookController: GET /api/books/search?keyword=" + keyword
                + " - Found " + books.size() + " results.");
        return ResponseEntity.ok(books);
    }
}
