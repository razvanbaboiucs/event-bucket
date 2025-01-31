package com.razvanbaboiu.event_bucket.event_router.controller;

import com.razvanbaboiu.event_bucket.event_api.IdentificationDto;
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
@RequestMapping("/api/v1/identifications")
@Slf4j
@RequiredArgsConstructor
public class IdentificationPushingController {

    private final RabbitTemplate rabbitTemplate;
    private final SecurityUtils securityUtils;

    @PostMapping
    public void addIdentification(@RequestBody IdentificationDto identification, HttpServletRequest request) {
        log.info("Sending new identification: {}", identification);
        IdentificationDto identificationDto = new IdentificationDto(generateDeduplicationId(identification),
                identification.projectId(), identification.userId());
        securityUtils.checkApiKey(request, identificationDto);
        rabbitTemplate.convertAndSend(MessageQueueConfiguration.IDENTIFICATION_TOPIC_EXCHANGE_NAME,
                MessageQueueConfiguration.IDENTIFICATION_ROUTING_KEY,
                identificationDto);
    }
}
