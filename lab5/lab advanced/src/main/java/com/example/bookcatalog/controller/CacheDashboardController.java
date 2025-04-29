package com.example.bookcatalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/dashboard")
public class CacheDashboardController {

    private final CacheManager cacheManager;
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public CacheDashboardController(CacheManager cacheManager, RedisTemplate<String, Object> redisTemplate) {
        this.cacheManager = cacheManager;
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/redis")
    public String redisDashboard(Model model) {
        // Get cache names
        model.addAttribute("cacheNames", cacheManager.getCacheNames());

        // Get Redis stats
        Map<String, Object> redisStats = new HashMap<>();

        // Get all keys for each cache
        Map<String, Set<String>> cacheKeys = new HashMap<>();
        for (String cacheName : cacheManager.getCacheNames()) {
            Set<String> keys = redisTemplate.keys(cacheName + "*");
            cacheKeys.put(cacheName, keys);
            redisStats.put(cacheName + "_size", keys != null ? keys.size() : 0);
        }

        model.addAttribute("redisStats", redisStats);
        model.addAttribute("cacheKeys", cacheKeys);

        return "redis-dashboard";
    }
}