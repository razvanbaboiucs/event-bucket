package com.razvanbaboiu.event_bucket.event_manager.controller;

import com.razvanbaboiu.event_bucket.event_api.EventDto;
import com.razvanbaboiu.event_bucket.event_manager.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
@Slf4j
public class EventController {

    private final EventService eventService;

    @GetMapping("/projects/{projectId}")
    public List<EventDto> getEvents(@PathVariable String projectId) {
        log.info("Get all events");
        return eventService.getAllEvents(projectId);
    }

    @GetMapping("/projects/{projectId}/filter")
    public List<EventDto> getEventsFiltered(@PathVariable String projectId,
                                            @RequestParam(required = false) String id,
                                            @RequestParam(required = false) String userId) {
        log.info("Get all events filtered");
        return eventService.getAllEventsFiltered(projectId, id, userId);
    }
}
