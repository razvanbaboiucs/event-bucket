package com.razvanbaboiu.event_bucket.event_manager.service;

import com.razvanbaboiu.event_bucket.event_api.EventDto;
import com.razvanbaboiu.event_bucket.event_manager.model.Event;
import com.razvanbaboiu.event_bucket.event_manager.model.Identification;
import com.razvanbaboiu.event_bucket.event_manager.repository.EventRepository;
import com.razvanbaboiu.event_bucket.event_manager.service.mapper.EventMapper;
import jakarta.transaction.Transactional;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final IdentificationService identificationService;

    public EventService(EventRepository eventRepository, IdentificationService identificationService) {
        this.eventRepository = eventRepository;
        this.identificationService = identificationService;
    }

    @Transactional
    public void saveEvent(EventDto eventDto) {
        Event event = EventMapper.map(eventDto);
        if (eventDto.userId() != null) {
            Optional<Identification> identification =
                    identificationService.getIdentificationForUserId(eventDto.userId(), eventDto.projectId());
            identification.ifPresent(event::setIdentification);
        }
        eventRepository.save(event);
    }

    public List<EventDto> getAllEvents(String projectId) {
        return eventRepository.findAllByProjectId(projectId).stream()
                .map(EventMapper::map)
                .toList();
    }

    public List<EventDto> getAllEventsFiltered(String projectId, String id, String userId) {
        Specification<Event> whereClause = Specification.where(hasProjectId(projectId));
        if (StringUtils.isNotBlank(id)) {
            whereClause = whereClause.and(typeIdContains(id));
        }
        if (StringUtils.isNotBlank(userId)) {
            whereClause = whereClause.and(userIdContains(userId));
        }
        return eventRepository.findAll(whereClause).stream()
                .map(EventMapper::map)
                .toList();
    }

    private Specification<Event> hasProjectId(String projectId) {
        return (event, _, cb) -> cb.equal(event.get("projectId"), projectId);
    }

    private Specification<Event> typeIdContains(String id) {
        return (event, _, cb) -> cb.like(event.get("typeId"), "%" + id + "%");
    }

    private Specification<Event> userIdContains(String userId) {
        return (event, _, cb) -> cb.like(event.get("identification").get("userId"), "%" + userId + "%");
    }
}
