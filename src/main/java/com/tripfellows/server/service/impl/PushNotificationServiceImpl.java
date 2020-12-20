package com.tripfellows.server.service.impl;

import com.tripfellows.server.messaging.FcmClient;
import com.tripfellows.server.messaging.PushNotificationRequest;
import com.tripfellows.server.model.Trip;
import com.tripfellows.server.service.api.PushNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PushNotificationServiceImpl implements PushNotificationService {

    private static final String NEW_TRIP_TOPIC_NAME = "TRIP";

    private final FcmClient fcmClient;

    @Override
    public void notifyTripCreated(Trip trip) {
        log.debug("Notifying topic: {} about new trip created", NEW_TRIP_TOPIC_NAME);

        PushNotificationRequest request = new PushNotificationRequest();
        request.setTopic(NEW_TRIP_TOPIC_NAME);
        request.setTitle("New trip was added!");
        request.setMessage("Click me to see it!");

        fcmClient.sendMessage(request, makeNotificationData(trip));
    }

    private Map<String, String> makeNotificationData(Trip trip) {
        Map<String, String> data = new HashMap<>();
        data.put("tripId", trip.getId().toString());
        data.put("creatorUid", trip.getCreator().getUid());
        return data;
    }

    @Override
    public void notifyTripStatusChanged(Trip trip) {

    }
}
