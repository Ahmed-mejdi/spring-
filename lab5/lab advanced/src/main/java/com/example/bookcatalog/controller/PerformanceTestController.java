package com.example.bookcatalog.controller;

import com.example.bookcatalog.model.Book;
import com.example.bookcatalog.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/performance")
public class PerformanceTestController {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceTestController.class);
    private final BookService bookService;

    @Autowired
    public PerformanceTestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/test/{id}/{iterations}")
    public ResponseEntity<Map<String, Object>> performanceTest(
            @PathVariable Long id,
            @PathVariable int iterations,
            @RequestParam(defaultValue = "true") boolean enableCache) {

        List<Long> executionTimes = new ArrayList<>();
        Book result = null;

        // Clear cache if requested
        if (!enableCache) {
            bookService.clearAllCaches();
        }

        // Perform the test
        for (int i = 0; i < iterations; i++) {
            long startTime = System.nanoTime();

            result = bookService.getBookById(id);

            long endTime = System.nanoTime();
            long duration = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
            executionTimes.add(duration);

            logger.info("Iteration {}: {}ms", i + 1, duration);

            // Small delay to avoid overwhelming the system
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Clear cache between iterations if cache is disabled
            if (!enableCache) {
                bookService.clearAllCaches();
            }
        }

        // Calculate statistics
        long totalTime = executionTimes.stream().mapToLong(Long::longValue).sum();
        double averageTime = executionTimes.stream().mapToLong(Long::longValue).average().orElse(0);
        long minTime = executionTimes.stream().mapToLong(Long::longValue).min().orElse(0);
        long maxTime = executionTimes.stream().mapToLong(Long::longValue).max().orElse(0);

        // Prepare response
        Map<String, Object> response = new HashMap<>();
        response.put("book", result);
        response.put("iterations", iterations);
        response.put("cacheEnabled", enableCache);
        response.put("executionTimes", executionTimes);
        response.put("totalTimeMs", totalTime);
        response.put("averageTimeMs", averageTime);
        response.put("minTimeMs", minTime);
        response.put("maxTimeMs", maxTime);

        return ResponseEntity.ok(response);
    }
}