package com.razvanbaboiu.event_bucket.event_manager.service;

import com.razvanbaboiu.event_bucket.event_api.EventDto;
import com.razvanbaboiu.event_bucket.event_api.IdentificationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {
    public void sendNotification(EventDto message, String email) {
        log.info("Sending notification for message {} to email {}", message.id(), email);
    }

    public void sendNotification(IdentificationDto message, String email) {
        log.info("Sending notification for identification {} to email {}", message.userId(), email);
    }
}
