package com.razvanbaboiu.event_bucket.event_manager.service.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@Service
public class DistributedLockService {
    private static final Logger logger = LoggerFactory.getLogger(DistributedLockService.class);
    private final RedisLockRegistry redisLockRegistry;

    public DistributedLockService(RedisLockRegistry redisLockRegistry) {
        this.redisLockRegistry = redisLockRegistry;
    }

    public <T> T executeWithLock(String lockKey, int waitTime, TimeUnit timeUnit, LockedOperation<T> operation) {
        Lock lock = redisLockRegistry.obtain(lockKey);
        try {
            boolean locked = lock.tryLock(waitTime, timeUnit);
            if (!locked) {
                throw new IllegalStateException("Could not acquire lock for key: " + lockKey);
            }
            logger.info("Acquired lock for key: {}", lockKey);
            return operation.execute();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Lock acquisition interrupted", e);
        } finally {
            try {
                lock.unlock();
                logger.info("Released lock for key: {}", lockKey);
            } catch (Exception e) {
                logger.error("Error releasing lock for key: {}", lockKey, e);
            }
        }
    }
}
