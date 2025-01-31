package com.razvanbaboiu.event_bucket.event_manager.cosumer;

import com.razvanbaboiu.event_bucket.account_api.AccountDto;
import com.razvanbaboiu.event_bucket.event_api.EventDto;
import com.razvanbaboiu.event_bucket.event_manager.client.AccountClient;
import com.razvanbaboiu.event_bucket.event_manager.configuration.EventProcessingMessageQueueConfiguration;
import com.razvanbaboiu.event_bucket.event_manager.service.EventService;
import com.razvanbaboiu.event_bucket.event_manager.service.NotificationService;
import com.razvanbaboiu.event_bucket.event_manager.service.RedisCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;


@Component
@Slf4j
@RequiredArgsConstructor
public class EventProcessingConsumer {

    private final EventService eventService;
    private final NotificationService notificationService;
    private final AccountClient accountClient;
    private final RedisCacheService redisCacheService;

    @RabbitListener(queues = "${spring.rabbitmq.event-processing-queue}")
    public void consumeMessage(EventDto message) {
        log.info("New event received {}", message);
        if (redisCacheService.keyExists(message.deduplicationId())) {
            log.info("Message already consumed");
            return;
        }
        AccountDto account = accountClient.getAccount(message.projectId());
        eventService.saveEvent(message);
        notificationService.sendNotification(message, account.email());
        redisCacheService.setValue(message.deduplicationId(), "PROCESSED", 5, TimeUnit.MINUTES);
    }

    @RabbitListener(queues = EventProcessingMessageQueueConfiguration.DLQ)
    public void processFailedMessage(EventDto message) {
        log.error("Processing failed message from DLQ: {}", message);
    }

}
