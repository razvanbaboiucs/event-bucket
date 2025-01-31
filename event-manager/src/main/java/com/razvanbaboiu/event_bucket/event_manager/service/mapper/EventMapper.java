package com.razvanbaboiu.event_bucket.event_manager.service.mapper;

import com.razvanbaboiu.event_bucket.event_api.EventDto;
import com.razvanbaboiu.event_bucket.event_manager.model.Event;
import com.razvanbaboiu.event_bucket.event_manager.model.Identification;

import java.util.Optional;

public class EventMapper {

    public static Event map(EventDto eventDto) {
        Event event = new Event();
        event.setTitle(eventDto.title());
        event.setDescription(eventDto.description());
        event.setProjectId(eventDto.projectId());
        event.setTypeId(eventDto.id());
        return event;
    }

    public static EventDto map(Event entity) {
        return new EventDto(null,
                entity.getTypeId(),
                entity.getProjectId(),
                entity.getTitle(),
                entity.getDescription(),
                Optional.ofNullable(entity.getIdentification())
                        .map(Identification::getUserId)
                        .orElse(null));
    }
}
