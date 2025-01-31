package com.razvanbaboiu.event_bucket.event_api;

import lombok.Builder;

@Builder
public record IdentificationDto(
        String deduplicationId,
        String projectId,
        String userId
) implements ProjectIdentified, DeduplicationIdentified {
}
