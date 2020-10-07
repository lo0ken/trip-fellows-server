package com.tripfellows.server.service.api;

import com.tripfellows.server.enums.TripStatusCodeEnum;
import com.tripfellows.server.model.Trip;
import com.tripfellows.server.model.TripStatus;

import java.util.Optional;

public interface TripStatusService {

    /**
     * Finds trip status by code
     *
     * @param code code to search trip status by
     * @return trip status with id code and name
     */
    TripStatus findByCode(TripStatusCodeEnum code);

    /**
     * Update trip status and return the modified trip if exists
     * if not exists, returns empty
     *
     * @param tripId trip to update status in
     * @param tripStatus new tripStatus for the trip
     * @return updated trip
     */
    Optional<Trip> updateTripStatus(Integer tripId, TripStatusCodeEnum tripStatus);
}
