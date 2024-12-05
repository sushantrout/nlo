package com.nlo.service;

import com.google.firebase.messaging.*;
import com.nlo.model.NotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FCMService {

    public void sendNotification(NotificationRequest notificationRequest) {
        try {
            Notification notification = Notification.builder().setTitle(notificationRequest.getTitle()).setBody(notificationRequest.getBody()).build();
            Message message = Message.builder()
                    .setToken(notificationRequest.getToken())
                    .setNotification(notification)
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
            log.info("Successfully sent message=> {}", response);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error occurs in FCMService {}", String.valueOf(e));

        }
    }
}