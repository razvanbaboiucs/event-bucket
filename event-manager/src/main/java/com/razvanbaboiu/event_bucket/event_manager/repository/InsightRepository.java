package com.razvanbaboiu.event_bucket.event_manager.repository;

import com.razvanbaboiu.event_bucket.event_manager.model.Insight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InsightRepository extends JpaRepository<Insight, Long> {
    Optional<Insight> findByTypeIdAndProjectId(String typeId, String projectId);

    List<Insight> findAllByProjectId(String projectId);
}
