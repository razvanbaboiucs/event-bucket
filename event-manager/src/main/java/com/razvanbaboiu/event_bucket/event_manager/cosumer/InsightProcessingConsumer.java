package com.razvanbaboiu.event_bucket.event_manager.cosumer;

import com.razvanbaboiu.event_bucket.event_api.EventDto;
import com.razvanbaboiu.event_bucket.event_api.InsightMutationDto;
import com.razvanbaboiu.event_bucket.event_manager.configuration.EventProcessingMessageQueueConfiguration;
import com.razvanbaboiu.event_bucket.event_manager.service.InsightService;
import com.razvanbaboiu.event_bucket.event_manager.service.RedisCacheService;
import com.razvanbaboiu.event_bucket.event_manager.service.lock.DistributedLockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Component
@Slf4j
@RequiredArgsConstructor
public class InsightProcessingConsumer {

    private final InsightService insightService;
    private final DistributedLockService distributedLockService;
    private final RedisCacheService redisCacheService;

    @RabbitListener(queues = "${spring.rabbitmq.insight-processing-queue}")
    public void consumeMessage(InsightMutationDto message) {
        log.info("New insight received {}", message);
        if (redisCacheService.keyExists(message.deduplicationId())) {
            log.info("Message already consumed");
            return;
        }
        distributedLockService.executeWithLock(
                getInsightMutationLockKey(message),
                1, TimeUnit.SECONDS,
                () -> {
                    insightService.mutateInsight(message);
                    redisCacheService.setValue(message.deduplicationId(), "PROCESSED", 5, TimeUnit.MINUTES);
                    return null;
                });
    }

    private String getInsightMutationLockKey(InsightMutationDto dto) {
        return dto.projectId() + "." + dto.id();
    }

    @RabbitListener(queues = EventProcessingMessageQueueConfiguration.DLQ)
    public void processFailedMessage(EventDto message) {
        log.error("Processing failed message from DLQ: {}", message);
    }

}
