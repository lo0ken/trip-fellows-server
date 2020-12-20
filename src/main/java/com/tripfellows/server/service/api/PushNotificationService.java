package com.tripfellows.server.service.api;

import com.tripfellows.server.model.Trip;

public interface PushNotificationService {

    /**
     * Notifying all subscribed users that trip has been created
     *
     * @param trip created trip
     */
    void notifyTripCreated(Trip trip);

    /**
     * Notifying all passengers of the trip that trip status has been changed
     *
     * @param trip trip with new status
     */
    void notifyTripStatusChanged(Trip trip);
}
