package com.razvanbaboiu.event_bucket.event_router.security;

import com.razvanbaboiu.event_bucket.event_api.ProjectIdentified;
import com.razvanbaboiu.event_bucket.event_router.client.AccountClient;
import com.razvanbaboiu.event_bucket.event_router.exception.NotAuthorizedException;
import com.razvanbaboiu.event_bucket.event_router.service.RedisCacheService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class SecurityUtils {

    private final RedisCacheService redisCacheService;
    private final AccountClient accountClient;

    public void checkApiKey(HttpServletRequest request, ProjectIdentified dto) {
        String apiKey = request.getHeader("x-eb-api-key");
        if (StringUtils.isBlank(apiKey)) {
            throw new NotAuthorizedException("API key is empty");
        }
        String cachedApiKey = redisCacheService.getValue(dto.projectId())
                .orElseGet(() -> getApiKeyFromAccount(dto));
        if (!cachedApiKey.equals(apiKey)) {
            throw new NotAuthorizedException("API key does not match");
        }
    }

    private String getApiKeyFromAccount(ProjectIdentified dto) {
        String accountApiKey = accountClient.getApiKey(dto.projectId());
        if (StringUtils.isBlank(accountApiKey)) {
            throw new NotAuthorizedException("API key does not exist");
        }
        redisCacheService.setValue(dto.projectId(), accountApiKey, 5, TimeUnit.MINUTES);
        return accountApiKey;
    }
}
