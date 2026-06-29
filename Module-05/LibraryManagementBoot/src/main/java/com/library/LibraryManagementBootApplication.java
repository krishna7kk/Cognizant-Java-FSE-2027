package com.library;

import com.library.entity.Book;
import com.library.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Exercise 9: Spring Boot Application Main Class
 *
 * @SpringBootApplication combines:
 *   - @Configuration       : Marks this as a Spring configuration class
 *   - @EnableAutoConfiguration : Enables Spring Boot's auto-configuration
 *   - @ComponentScan       : Scans com.library package for components
 */
@SpringBootApplication
public class LibraryManagementBootApplication {

    public static void main(String[] args) {
        System.out.println("=======================================================");
        System.out.println("  Exercise 9: Spring Boot Library Management App       ");
        System.out.println("=======================================================");
        SpringApplication.run(LibraryManagementBootApplication.class, args);
    }

    /**
     * CommandLineRunner to seed initial data into H2 database on startup
     */
    @Bean
    public CommandLineRunner seedData(BookRepository bookRepository) {
        return args -> {
            System.out.println("\n--- Seeding initial book data into H2 database ---");

            bookRepository.save(new Book(
                "The Spring Framework in Action",
                "Craig Walls",
                "ISBN-978-1617294945"
            ));
            bookRepository.save(new Book(
                "Clean Code",
                "Robert C. Martin",
                "ISBN-978-0132350884"
            ));
            bookRepository.save(new Book(
                "Design Patterns",
                "Gang of Four",
                "ISBN-978-0201633610"
            ));
            bookRepository.save(new Book(
                "Effective Java",
                "Joshua Bloch",
                "ISBN-978-0134685991"
            ));

            System.out.println("Seeded " + bookRepository.count() + " books into the database.");
            System.out.println("\nApplication is running!");
            System.out.println("REST API: http://localhost:8080/api/books");
            System.out.println("H2 Console: http://localhost:8080/h2-console");
            System.out.println("  JDBC URL: jdbc:h2:mem:librarydb");
            System.out.println("  Username: sa  |  Password: (empty)");
            System.out.println("=======================================================\n");
        };
    }
}
