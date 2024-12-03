package com.nlo.model;

import lombok.Data;

@Data
public class NotificationRequest {
    private String title;
    private String body;
    private String token;
}