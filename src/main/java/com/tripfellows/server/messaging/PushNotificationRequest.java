package com.tripfellows.server.messaging;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
public class PushNotificationRequest {
    private String title;
    private String message;
    private String topic;
}

