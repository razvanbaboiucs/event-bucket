package com.razvanbaboiu.event_bucket.account_manager.repository;

import com.razvanbaboiu.event_bucket.account_manager.model.Account;
import com.razvanbaboiu.event_bucket.account_manager.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByProjectId(String projectId);

    List<Project> findAllByAccount(Account account);
}
