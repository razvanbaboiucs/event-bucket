package com.razvanbaboiu.event_bucket.event_router.message;

import com.razvanbaboiu.event_bucket.event_api.DeduplicationIdentified;

import java.util.Optional;
import java.util.UUID;

public class MessagingUtils {
    public static String generateDeduplicationId(DeduplicationIdentified dto) {
        return Optional.of(dto)
                .map(DeduplicationIdentified::deduplicationId)
                .orElse(UUID.randomUUID().toString());
    }
}
