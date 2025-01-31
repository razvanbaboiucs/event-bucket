package com.razvanbaboiu.event_bucket.event_manager.repository;

import com.razvanbaboiu.event_bucket.event_manager.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    List<Event> findAllByProjectId(String projectId);

    @Query(value = "SELECT e FROM Event e " +
            "WHERE e.projectId = :projectId " +
            "AND e.identification.userId = :userId " +
            "AND e.typeId = :typeId")
    List<Event> findAllFiltered(String projectId, String typeId, String userId);
}
