package com.tripfellows.server.service.api;

import com.tripfellows.server.messaging.FcmClient;
import com.tripfellows.server.messaging.PushNotificationRequest;
import com.tripfellows.server.model.Trip;
import com.tripfellows.server.service.impl.PushNotificationServiceImpl;
import org.jeasy.random.EasyRandom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

import static java.util.stream.Collectors.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PushNotificationServiceTest {

    @Mock
    FcmTokenService fcmTokenService;

    @Mock
    FcmClient fcmClient;

    @InjectMocks
    PushNotificationServiceImpl pushNotificationService;

    EasyRandom easyRandom = new EasyRandom();

    @Test
    public void notifyTripCreatedTest() {
        Trip trip = easyRandom.nextObject(Trip.class);

        pushNotificationService.notifyTripCreated(trip);

        verify(fcmClient).sendMessage(
                argThat(this::validateTripCreatedRequest),
                argThat(data -> validateTripData(trip, data))
        );
    }

    private boolean validateTripCreatedRequest(PushNotificationRequest request) {
        return PushNotificationServiceImpl.NEW_TRIP_TOPIC_NAME.equals(request.getTopic());
    }

    private boolean validateTripData(Trip trip, Map<String, String> data) {
        String tripId = data.get("tripId");
        String creatorUid = data.get("creatorUid");

        return !isNull(tripId) && !isNull(creatorUid) &&
                tripId.equals(trip.getId().toString()) &&
                creatorUid.equals(trip.getCreator().getUid());
    }

    @Test
    public void notifyTripStatusChangedTest() {
        Trip trip = easyRandom.nextObject(Trip.class);
        List<String> tokens = easyRandom.objects(String.class, trip.getMembers().size()).collect(toList());

        when(fcmTokenService.findByAccountIds(any())).thenReturn(tokens);

        pushNotificationService.notifyTripStatusChanged(trip);

        verify(fcmClient).sendMulticastMessage(
                argThat(request -> validateTripStatusChangedRequest(trip, request)),
                argThat(data -> validateTripData(trip, data)),
                argThat(tokenList -> tokenList.size() == tokens.size())
        );
    }

    private boolean validateTripStatusChangedRequest(Trip trip, PushNotificationRequest request) {
        return request.getMessage().contains(trip.getStatus().getName());
    }
}