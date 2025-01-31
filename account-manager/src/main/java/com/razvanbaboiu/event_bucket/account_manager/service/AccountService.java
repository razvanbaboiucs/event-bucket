package com.razvanbaboiu.event_bucket.account_manager.service;

import com.razvanbaboiu.event_bucket.account_api.AccountDto;
import com.razvanbaboiu.event_bucket.account_manager.dto.AccountCreationDto;
import com.razvanbaboiu.event_bucket.account_manager.dto.ProjectApiKeyDto;
import com.razvanbaboiu.event_bucket.account_manager.dto.ProjectCreationDto;
import com.razvanbaboiu.event_bucket.account_manager.dto.ProjectDto;
import com.razvanbaboiu.event_bucket.account_manager.model.Account;
import com.razvanbaboiu.event_bucket.account_manager.model.Project;
import com.razvanbaboiu.event_bucket.account_manager.repository.AccountRepository;
import com.razvanbaboiu.event_bucket.account_manager.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    public static final int API_KEY_LENGTH = 64;
    private final AccountRepository accountRepository;
    private final ProjectRepository projectRepository;

    private static String generateApiKey() {
        String saltChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < API_KEY_LENGTH) {
            int index = (int) (rnd.nextFloat() * saltChars.length());
            salt.append(saltChars.charAt(index));
        }
        return salt.toString();
    }

    private static String generateProjectId() {
        return UUID.randomUUID().toString();
    }

    @Transactional
    public Long addAccount(AccountCreationDto dto) {
        Account account = new Account();
        account.setEmail(dto.email());
        account.setTeamName(dto.teamName());
        return accountRepository.save(account).getId();
    }

    @Transactional
    public ProjectApiKeyDto addProject(ProjectCreationDto dto) {
        Project project = new Project();
        project.setProjectName(dto.projectName());
        Account account = accountRepository.findById(dto.accountId())
                .orElseThrow(() -> new IllegalArgumentException("Account with given id does not exist"));
        project.setAccount(account);
        project.setProjectId(generateProjectId());
        project.setApiKey(generateApiKey());
        Project savedProject = projectRepository.save(project);
        return ProjectApiKeyDto.builder()
                .projectId(savedProject.getProjectId())
                .apiKey(savedProject.getApiKey())
                .build();
    }

    public AccountDto getAccountForProjectId(String projectId) {
        Project project = projectRepository.findByProjectId(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project with given id not found"));
        return accountRepository.findById(project.getAccount().getId())
                .map(acc -> new AccountDto(acc.getId(), acc.getTeamName(), acc.getEmail()))
                .orElseThrow(() -> new IllegalArgumentException("Account with given id not found"));
    }

    public String getApiKeyForProjectId(String projectId) {
        return projectRepository.findByProjectId(projectId)
                .map(Project::getApiKey)
                .orElseThrow(() -> new IllegalArgumentException("Project with given id not found"));
    }

    public List<ProjectDto> getProjects(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account with given id not found"));
        return projectRepository.findAllByAccount(account).stream()
                .map(project -> new ProjectDto(project.getProjectId(), project.getProjectName()))
                .toList();
    }

    public AccountDto getAccount(Long id) {
        return accountRepository.findById(id)
                .map(acc -> new AccountDto(acc.getId(), acc.getTeamName(), acc.getEmail()))
                .orElseThrow(() -> new IllegalArgumentException("Account with given api key not found"));
    }
}
