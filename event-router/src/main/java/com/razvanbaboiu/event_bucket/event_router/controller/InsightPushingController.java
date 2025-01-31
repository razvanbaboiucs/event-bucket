package com.razvanbaboiu.event_bucket.event_router.controller;

import com.razvanbaboiu.event_bucket.event_api.InsightMutationDto;
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
@RequestMapping("/api/v1/insights")
@Slf4j
@RequiredArgsConstructor
public class InsightPushingController {

    private final RabbitTemplate rabbitTemplate;
    private final SecurityUtils securityUtils;

    @PostMapping("/mutation")
    public void addInsight(@RequestBody InsightMutationDto insightMutation, HttpServletRequest request) {
        log.info("Sending new insight: {}", insightMutation);
        InsightMutationDto insightMutationDto = new InsightMutationDto(generateDeduplicationId(insightMutation),
                insightMutation.projectId(), insightMutation.id(), insightMutation.mutationType(),
                insightMutation.mutationValue());
        securityUtils.checkApiKey(request, insightMutationDto);
        rabbitTemplate.convertAndSend(MessageQueueConfiguration.INSIGHT_TOPIC_EXCHANGE_NAME,
                MessageQueueConfiguration.INSIGHT_ROUTING_KEY,
                insightMutationDto);
    }
}
