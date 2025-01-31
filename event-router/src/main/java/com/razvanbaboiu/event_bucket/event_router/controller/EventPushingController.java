package com.razvanbaboiu.event_bucket.event_router.controller;

import com.razvanbaboiu.event_bucket.event_api.EventDto;
import com.razvanbaboiu.event_bucket.event_router.configuration.MessageQueueConfiguration;
import com.razvanbaboiu.event_bucket.event_router.security.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.razvanbaboiu.event_bucket.event_router.message.MessagingUtils.generateDeduplicationId;

@RestController
@RequestMapping("/api/v1/events")
@Slf4j
@RequiredArgsConstructor
public class EventPushingController {

    private final RabbitTemplate rabbitTemplate;
    private final SecurityUtils securityUtils;

    @PostMapping
    public void addEvent(@RequestBody EventDto event, HttpServletRequest request) {
        log.info("Sending new event: {}", event);
        EventDto eventDto = new EventDto(generateDeduplicationId(event), event.id(),
                event.projectId(), event.title(), event.description(), event.userId());
        securityUtils.checkApiKey(request, eventDto);
        rabbitTemplate.convertAndSend(MessageQueueConfiguration.EVENTS_TOPIC_EXCHANGE_NAME,
                MessageQueueConfiguration.EVENT_ROUTING_KEY,
                eventDto);
    }
}
