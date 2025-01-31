package com.razvanbaboiu.event_bucket.account_api;

import lombok.Builder;

@Builder
public record AccountDto(Long id, String teamName, String email) {
}
