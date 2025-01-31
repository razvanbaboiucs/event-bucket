package com.razvanbaboiu.event_bucket.event_api;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record InsightMutationDto(
        String deduplicationId,
        String projectId,
        String id,
        MutationType mutationType,
        BigDecimal mutationValue
) implements ProjectIdentified, DeduplicationIdentified {
}
