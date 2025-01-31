package com.razvanbaboiu.event_bucket.event_api;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record InsightDto(
        String projectId,
        String id,
        String title,
        BigDecimal value
) implements ProjectIdentified {
}
