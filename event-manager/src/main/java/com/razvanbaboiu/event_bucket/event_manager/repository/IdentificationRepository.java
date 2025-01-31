package com.razvanbaboiu.event_bucket.event_manager.repository;

import com.razvanbaboiu.event_bucket.event_manager.model.Identification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IdentificationRepository extends JpaRepository<Identification, Long> {

    Optional<Identification> findByUserIdAndProjectId(String userId, String projectId);

    List<Identification> findAllByProjectId(String projectId);
}
