package com.example.bookcatalog.controller;

import com.example.bookcatalog.model.Book;
import com.example.bookcatalog.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Book Controller", description = "API for managing books in the catalog")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @Operation(summary = "Get all books", description = "Returns a list of all books in the catalog")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/paged")
    @Operation(summary = "Get paged books", description = "Returns a paginated list of books with optional sorting")
    public ResponseEntity<Page<Book>> getPagedBooks(Pageable pageable) {
        return ResponseEntity.ok(bookService.getBooks(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by ID", description = "Returns a book by its ID")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("/title/{title}")
    @Operation(summary = "Get books by title", description = "Returns books containing the given title (case-insensitive)")
    public ResponseEntity<List<Book>> getBooksByTitle(@PathVariable String title) {
        return ResponseEntity.ok(bookService.getBooksByTitle(title));
    }

    @GetMapping("/author/{author}")
    @Operation(summary = "Get books by author", description = "Returns books by the given author (case-insensitive)")
    public ResponseEntity<List<Book>> getBooksByAuthor(@PathVariable String author) {
        return ResponseEntity.ok(bookService.getBooksByAuthor(author));
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Get books by category", description = "Returns books in the given category (case-insensitive)")
    public ResponseEntity<List<Book>> getBooksByCategory(@PathVariable String category) {
        return ResponseEntity.ok(bookService.getBooksByCategory(category));
    }

    @PostMapping
    @Operation(summary = "Create a book", description = "Creates a new book in the catalog")
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        return new ResponseEntity<>(bookService.createBook(book), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a book", description = "Updates an existing book in the catalog")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @Valid @RequestBody Book book) {
        return ResponseEntity.ok(bookService.updateBook(id, book));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a book", description = "Deletes a book from the catalog")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}