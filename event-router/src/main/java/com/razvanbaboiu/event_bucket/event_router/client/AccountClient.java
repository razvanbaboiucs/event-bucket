package com.razvanbaboiu.event_bucket.event_router.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountClient {

    private final RestTemplate restTemplate;

    public String getApiKey(String projectId) {
        String apiKey = restTemplate.getForObject(
                "http://account-manager/api/v1/account/projects/{id}/apiKey",
                String.class,
                projectId
        );
        log.info("Api key for project id retrieved: {}", apiKey);
        return apiKey;
    }

}
