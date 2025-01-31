package com.razvanbaboiu.event_bucket.event_manager.client;

import com.razvanbaboiu.event_bucket.account_api.AccountDto;
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

    public AccountDto getAccount(String projectId) {
        AccountDto account = restTemplate.getForObject(
                "http://account-manager/api/v1/account/projects/{id}",
                AccountDto.class,
                projectId
        );
        if (account == null) {
            throw new NoSuchElementException("No account found for the given project");
        }
        log.info("Account for project id retrieved: {}", account);
        return account;
    }

}
