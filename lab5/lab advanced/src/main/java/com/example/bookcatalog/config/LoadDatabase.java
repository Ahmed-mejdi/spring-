package com.example.bookcatalog.config;

import com.example.bookcatalog.model.Book;
import com.example.bookcatalog.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.Arrays;

@Configuration
@Profile("dev")
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(BookRepository repository) {
        return args -> {
            // Only load sample data if the repository is empty
            if (repository.count() == 0) {
                repository.saveAll(Arrays.asList(
                        new Book(null, "Spring Boot in Action", "Craig Walls",
                                "978-1617292545", LocalDate.of(2015, 12, 28), 39.99,
                                "Learn Spring Boot through real-world applications"),
                        new Book(null, "Pro Spring Boot 2", "Felipe Gutierrez",
                                "978-1484236758", LocalDate.of(2018, 8, 10), 44.99,
                                "A comprehensive guide to Spring Boot 2"),
                        new Book(null, "Clean Code", "Robert C. Martin",
                                "978-0132350884", LocalDate.of(2008, 8, 11), 49.99,
                                "A handbook of agile software craftsmanship"),
                        new Book(null, "Effective Java", "Joshua Bloch",
                                "978-0134685991", LocalDate.of(2018, 1, 6), 54.99,
                                "Best practices for the Java platform"),
                        new Book(null, "Design Patterns", "Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides",
                                "978-0201633610", LocalDate.of(1994, 11, 10), 59.99,
                                "Elements of Reusable Object-Oriented Software")
                ));
            }
        };
    }
}