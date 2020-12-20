package com.tripfellows.server.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class UpdateFcmTokenRequest {
    private String fcmToken;
}
