package com.razvanbaboiu.event_bucket.account_manager.dto;

import lombok.Builder;

@Builder
public record AccountCreationDto(String email, String teamName) {
}
