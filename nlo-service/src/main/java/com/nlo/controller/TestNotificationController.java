package com.nlo.controller;

import com.nlo.model.NotificationRequest;
import com.nlo.service.FCMService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notification-test")
@RequiredArgsConstructor
public class TestNotificationController {
    private final FCMService fcmService;

    @GetMapping
    public void testNotification() {
        NotificationRequest request = new NotificationRequest(); //Javed
        request.setToken("dkDztLvuQtSF5H-iE7MV1h:APA91bEG6ygxZ5hD-6uF1MRS6apHVpdlwr3E5OMXfTE_ZZnWLc_6wpbPV8Zbkb_kuTzKfrXindSzhRfYGh7hifZACCbGdr0rwIHatkyj3MQe5cP5hJ6lrXY");
        request.setTitle("Title");
        request.setBody("Body");
        fcmService.sendNotification(request);

        request = new NotificationRequest(); //Soumya Nayak
        request.setToken("eNHMFzOIZEnLow_0eAOotn:APA91bF4q75Ms_iDNcgxsEf-B2hCm7_NUvR5uGraApdazQBo9kRcMAAnZ1AcT0ObVYy2-3AcUr_xVNeR8OvbQCggth-hCqzZK5S_4BP6_X28RGosMuIDTE8");
        request.setTitle("Title 1");
        request.setBody("Body 1");
        fcmService.sendNotification(request);
    }
}
