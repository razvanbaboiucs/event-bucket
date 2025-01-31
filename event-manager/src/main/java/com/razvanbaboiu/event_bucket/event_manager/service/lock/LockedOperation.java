package com.razvanbaboiu.event_bucket.event_manager.service.lock;

@FunctionalInterface
public interface LockedOperation<T> {
    T execute();
}
