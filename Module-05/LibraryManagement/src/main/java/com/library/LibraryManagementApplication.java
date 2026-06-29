package com.library;

import com.library.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Exercise 1, 2, 3, 5, 6, 7, 8:
 * Main class to load the Spring context and test all configurations.
 *
 * This single application demonstrates:
 *  - Exercise 1 : Basic Spring Application Context loading
 *  - Exercise 2 : Dependency Injection (BookRepository into BookService)
 *  - Exercise 3 : AOP Logging with execution times (Around advice)
 *  - Exercise 5 : IoC Container Configuration
 *  - Exercise 6 : Annotation-based bean configuration (@Service, @Repository)
 *  - Exercise 7 : Constructor and Setter Injection
 *  - Exercise 8 : Before, After, AfterReturning AOP advices
 */
public class LibraryManagementApplication {

    public static void main(String[] args) {

        System.out.println("==========================================================");
        System.out.println("  Library Management Application - Spring Core Exercises  ");
        System.out.println("==========================================================\n");

        // Exercise 1 & 5: Load the Spring Application Context
        System.out.println("--- Loading Spring Application Context ---");
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        System.out.println("Spring Application Context loaded successfully.\n");

        // Exercise 2 & 7: Retrieve the BookService bean (DI already done by Spring)
        System.out.println("--- Retrieving BookService bean from context ---");
        BookService bookService = context.getBean("bookService", BookService.class);
        System.out.println("BookService bean retrieved successfully.\n");

        // Exercise 3 & 8: AOP in action - logging triggered on every method call
        System.out.println("--- Exercise 3 & 8: AOP Logging Demo ---");

        System.out.println("\n[Test 1] Fetching all books:");
        List<String> books = bookService.getAllBooks();
        System.out.println("Books retrieved: " + books);

        System.out.println("\n[Test 2] Adding a new book:");
        bookService.addBook("Effective Java by Joshua Bloch");

        System.out.println("\n[Test 3] Fetching all books after addition:");
        List<String> updatedBooks = bookService.getAllBooks();
        System.out.println("Updated book list: " + updatedBooks);

        System.out.println("\n[Test 4] Searching for a specific book:");
        String foundBook = bookService.findBook("Clean Code by Robert C. Martin");
        System.out.println("Search result: " + foundBook);

        System.out.println("\n==========================================================");
        System.out.println("  All Exercises Completed Successfully!                  ");
        System.out.println("==========================================================");

        // Close the context
        ((ClassPathXmlApplicationContext) context).close();
    }
}
