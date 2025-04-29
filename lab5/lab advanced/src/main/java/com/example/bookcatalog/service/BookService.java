package com.example.bookcatalog.service;

import com.example.bookcatalog.exception.BookNotFoundException;
import com.example.bookcatalog.model.Book;
import com.example.bookcatalog.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Cacheable(value = "books")
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Cacheable(value = "books", key = "#id", condition = "#id > 0")
    @Transactional(readOnly = true)
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
    }

    @Cacheable(value = "booksByCategory", key = "#category", condition = "!#category.equals('Fiction')")
    @Transactional(readOnly = true)
    public List<Book> getBooksByCategory(String category) {
        return bookRepository.findByCategoryIgnoreCase(category);
    }

    @Cacheable(value = "booksByTitle", key = "#title")
    @Transactional(readOnly = true)
    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    @Cacheable(value = "booksByAuthor", key = "#author")
    @Transactional(readOnly = true)
    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

    @Transactional(readOnly = true)
    public Page<Book> getBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @CachePut(value = "books", key = "#result.id")
    @CacheEvict(value = {"booksByTitle", "booksByAuthor", "booksByCategory"}, allEntries = true)
    @Transactional
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    @Caching(
            put = { @CachePut(value = "books", key = "#id") },
            evict = {
                    @CacheEvict(value = "booksByTitle", allEntries = true),
                    @CacheEvict(value = "booksByAuthor", allEntries = true),
                    @CacheEvict(value = "booksByCategory", allEntries = true)
            }
    )
    @Transactional
    public Book updateBook(Long id, Book bookDetails) {
        Book book = getBookById(id);
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setIsbn(bookDetails.getIsbn());
        book.setPublicationDate(bookDetails.getPublicationDate());
        book.setPrice(bookDetails.getPrice());
        book.setDescription(bookDetails.getDescription());
        book.setCategory(bookDetails.getCategory());
        return bookRepository.save(book);
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "books", key = "#id"),
                    @CacheEvict(value = "booksByTitle", allEntries = true),
                    @CacheEvict(value = "booksByAuthor", allEntries = true),
                    @CacheEvict(value = "booksByCategory", allEntries = true)
            }
    )
    @Transactional
    public void deleteBook(Long id) {
        Book book = getBookById(id);
        bookRepository.delete(book);
    }

    @CacheEvict(value = {"books", "booksByTitle", "booksByAuthor", "booksByCategory"}, allEntries = true)
    public void clearAllCaches() {
        // This method intentionally left blank. The caches are cleared by the annotation.
    }
}