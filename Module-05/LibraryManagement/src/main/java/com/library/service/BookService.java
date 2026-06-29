package com.library.service;

import com.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Exercise 1 & 2: BookService class in com.library.service package
 * Exercise 6: Annotated with @Service for component scanning
 * Exercise 7: Supports both Constructor Injection and Setter Injection
 */
@Service
public class BookService {

    private BookRepository bookRepository;

    /**
     * Exercise 7: Constructor Injection
     * Spring uses this constructor when configured via <constructor-arg> in XML
     */
    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("BookService: Initialized via Constructor Injection.");
    }

    /**
     * Exercise 2 & 7: Setter Injection
     * Spring calls this setter when configured via <property> in applicationContext.xml
     */
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("BookService: BookRepository set via Setter Injection.");
    }

    /**
     * Get all books from the repository.
     */
    public List<String> getAllBooks() {
        System.out.println("BookService: Calling getAllBooks().");
        return bookRepository.findAllBooks();
    }

    /**
     * Add a new book.
     */
    public void addBook(String title) {
        System.out.println("BookService: Calling addBook() with title: " + title);
        bookRepository.addBook(title);
    }

    /**
     * Find a book by title.
     */
    public String findBook(String title) {
        System.out.println("BookService: Calling findBook() with title: " + title);
        return bookRepository.findBookByTitle(title);
    }
}
