package com.razvanbaboiu.event_bucket.event_router.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisCacheService {
    private final RedisTemplate<String, String> redisTemplate;

    public void setValue(String key, String value, long timeout, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
        } catch (Exception e) {
            log.error("Error setting value for key: {}", key, e);
            throw new RuntimeException("Failed to set cache value", e);
        }
    }

    public Optional<String> getValue(String key) {
        try {
            String value = redisTemplate.opsForValue().get(key);
            return Optional.ofNullable(value);
        } catch (Exception e) {
            log.error("Error retrieving value for key: {}", key, e);
            return Optional.empty();
        }
    }
}