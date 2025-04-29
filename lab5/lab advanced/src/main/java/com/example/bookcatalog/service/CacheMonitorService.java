package com.example.bookcatalog.service;

import org.springframework.boot.actuate.metrics.cache.CacheMetricsRegistrar;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class CacheMonitorService {

    private final CacheManager cacheManager;

    public CacheMonitorService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public Map<String, Object> getCacheStatistics() {
        Map<String, Object> stats = new HashMap<>();
        Collection<String> cacheNames = cacheManager.getCacheNames();

        stats.put("availableCaches", cacheNames);
        stats.put("cacheCount", cacheNames.size());

        return stats;
    }
}