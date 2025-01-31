package com.razvanbaboiu.event_bucket.event_manager.cosumer;

import com.razvanbaboiu.event_bucket.account_api.AccountDto;
import com.razvanbaboiu.event_bucket.event_api.IdentificationDto;
import com.razvanbaboiu.event_bucket.event_manager.client.AccountClient;
import com.razvanbaboiu.event_bucket.event_manager.configuration.IdentificationProcessingMessageQueueConfiguration;
import com.razvanbaboiu.event_bucket.event_manager.service.IdentificationService;
import com.razvanbaboiu.event_bucket.event_manager.service.NotificationService;
import com.razvanbaboiu.event_bucket.event_manager.service.RedisCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Component
@Slf4j
@RequiredArgsConstructor
public class IdentificationProcessingConsumer {

    private final IdentificationService identificationService;
    private final NotificationService notificationService;
    private final AccountClient accountClient;
    private final RedisCacheService redisCacheService;

    @RabbitListener(queues = "${spring.rabbitmq.identification-processing-queue}")
    public void consumeMessage(IdentificationDto message) {
        log.info("New identification received {}", message);
        if (redisCacheService.keyExists(message.deduplicationId())) {
            log.info("Message already consumed");
            return;
        }
        AccountDto account = accountClient.getAccount(message.projectId());
        identificationService.saveIdentification(message);
        notificationService.sendNotification(message, account.email());
        redisCacheService.setValue(message.deduplicationId(), "PROCESSED", 5, TimeUnit.MINUTES);
    }

    @RabbitListener(queues = IdentificationProcessingMessageQueueConfiguration.DLQ)
    public void processFailedMessage(IdentificationDto message) {
        log.error("Processing failed message from DLQ: {}", message);
    }

}
