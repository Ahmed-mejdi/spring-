package com.example.bookcatalog.controller;

import com.example.bookcatalog.service.BookService;
import com.example.bookcatalog.service.CacheMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/cache")
public class CacheController {

    private final BookService bookService;
    private final CacheMonitorService cacheMonitorService;

    @Autowired
    public CacheController(BookService bookService, CacheMonitorService cacheMonitorService) {
        this.bookService = bookService;
        this.cacheMonitorService = cacheMonitorService;
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getCacheStatistics() {
        return ResponseEntity.ok(cacheMonitorService.getCacheStatistics());
    }

    @DeleteMapping
    public ResponseEntity<String> clearAllCaches() {
        bookService.clearAllCaches();
        return ResponseEntity.ok("All caches have been cleared");
    }
}