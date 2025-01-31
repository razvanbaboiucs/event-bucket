package com.razvanbaboiu.event_bucket.account_manager.controller;

import com.razvanbaboiu.event_bucket.account_api.AccountDto;
import com.razvanbaboiu.event_bucket.account_manager.dto.AccountCreationDto;
import com.razvanbaboiu.event_bucket.account_manager.dto.ProjectApiKeyDto;
import com.razvanbaboiu.event_bucket.account_manager.dto.ProjectCreationDto;
import com.razvanbaboiu.event_bucket.account_manager.dto.ProjectDto;
import com.razvanbaboiu.event_bucket.account_manager.service.AccountService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public Long createAccount(@RequestBody AccountCreationDto dto) {
        return accountService.addAccount(dto);
    }

    @PostMapping("/projects")
    public ProjectApiKeyDto createProject(@RequestBody ProjectCreationDto dto) {
        return accountService.addProject(dto);
    }

    @GetMapping("/projects/{projectId}")
    public AccountDto getAccountForProject(@PathVariable String projectId) {
        return accountService.getAccountForProjectId(projectId);
    }

    @GetMapping
    public AccountDto getAccount() {
        return accountService.getAccount(1L);
    }

    @GetMapping("/projects/{projectId}/apiKey")
    public String getApiKeyForProject(@PathVariable String projectId) {
        return accountService.getApiKeyForProjectId(projectId);
    }

    @GetMapping("/{accountId}")
    public List<ProjectDto> getProjects(@PathVariable Long accountId) {
        return accountService.getProjects(accountId);
    }
}
