package com.example.bookcatalog.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

@Component
public class RedisHealthIndicator implements HealthIndicator {

    private final RedisConnectionFactory redisConnectionFactory;

    public RedisHealthIndicator(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    @Override
    public Health health() {
        try {
            RedisConnection connection = redisConnectionFactory.getConnection();
            String pong = new String(connection.ping());
            connection.close();

            if ("PONG".equalsIgnoreCase(pong)) {
                return Health.up()
                        .withDetail("ping", "PONG")
                        .withDetail("status", "Redis is operational")
                        .build();
            } else {
                return Health.down()
                        .withDetail("ping", pong)
                        .withDetail("status", "Redis ping didn't return PONG")
                        .build();
            }
        } catch (Exception e) {
            return Health.down()
                    .withDetail("status", "Redis connection failed")
                    .withDetail("error", e.getMessage())
                    .build();
        }
    }
}