package com.example.bookcatalog.controller;

import com.example.bookcatalog.model.Book;
import com.example.bookcatalog.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cache-demo")
public class CacheDemoController {

    private static final Logger logger = LoggerFactory.getLogger(CacheDemoController.class);
    private final BookService bookService;
    private final Map<Long, LocalDateTime> requestTimes = new HashMap<>();

    @Autowired
    public CacheDemoController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<Map<String, Object>> demonstrateBookCaching(@PathVariable Long id) {
        // Record start time
        LocalDateTime startTime = LocalDateTime.now();

        // Check if we have a previous request time for this ID
        boolean isCached = requestTimes.containsKey(id);
        LocalDateTime previousRequestTime = requestTimes.get(id);

        // Execute the potentially cached operation
        Book book = bookService.getBookById(id);

        // Record end time and calculate duration
        LocalDateTime endTime = LocalDateTime.now();
        long durationMs = ChronoUnit.MILLIS.between(startTime, endTime);

        // Update the request time for this ID
        requestTimes.put(id, endTime);

        // Prepare response with timing information
        Map<String, Object> response = new HashMap<>();
        response.put("book", book);
        response.put("requestTime", endTime);
        response.put("executionTimeMs", durationMs);
        response.put("likelyCacheHit", isCached && durationMs < 50); // Assuming cache hits are fast

        if (previousRequestTime != null) {
            response.put("timeSinceLastRequestMs",
                    ChronoUnit.MILLIS.between(previousRequestTime, endTime));
        }

        // Log the request
        logger.info("Book ID: {}, Duration: {}ms, Likely Cache Hit: {}",
                id, durationMs, response.get("likelyCacheHit"));

        return ResponseEntity.ok(response);
    }
}