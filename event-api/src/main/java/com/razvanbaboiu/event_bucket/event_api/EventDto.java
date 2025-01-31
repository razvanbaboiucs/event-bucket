package com.razvanbaboiu.event_bucket.event_api;

import lombok.Builder;

@Builder
public record EventDto(
        String deduplicationId,
        String id,
        String projectId,
        String title,
        String description,
        String userId
) implements ProjectIdentified, DeduplicationIdentified {
}
