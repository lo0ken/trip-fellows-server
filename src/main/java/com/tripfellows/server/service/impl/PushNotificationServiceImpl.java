package com.tripfellows.server.service.impl;

import com.tripfellows.server.messaging.FcmClient;
import com.tripfellows.server.messaging.PushNotificationRequest;
import com.tripfellows.server.model.Trip;
import com.tripfellows.server.service.api.FcmTokenService;
import com.tripfellows.server.service.api.PushNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PushNotificationServiceImpl implements PushNotificationService {

    public static final String NEW_TRIP_TOPIC_NAME = "TRIP";

    private final FcmTokenService fcmTokenService;

    private final FcmClient fcmClient;

    @Override
    public void notifyTripCreated(Trip trip) {
        log.debug("Notifying topic: {} about new trip with id : {} created",
                NEW_TRIP_TOPIC_NAME, trip.getId());

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
        log.debug("Notifying members of trip with id: {} that status changed to {}",
                trip.getId(), trip.getStatus().getName());

        List<Integer> tripMemberIds = trip.getMembers().stream()
                .map(member -> member.getAccount().getId())
                .collect(toList());

        List<String> memberTokens = fcmTokenService.findByAccountIds(tripMemberIds);

        PushNotificationRequest request = new PushNotificationRequest();
        request.setTitle("Your trip");
        request.setMessage("Status of your trip was changed to " + trip.getStatus().getName());

        fcmClient.sendMulticastMessage(request, makeNotificationData(trip), memberTokens);
    }
}
