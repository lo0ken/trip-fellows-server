package com.tripfellows.server.controller;

import com.tripfellows.server.enums.TripStatusCodeEnum;
import com.tripfellows.server.model.Trip;
import com.tripfellows.server.service.api.PushNotificationService;
import com.tripfellows.server.service.api.TripStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * REST controller for managing trip status
 */
@Slf4j
@RestController
@RequestMapping("/api/trip-status")
@RequiredArgsConstructor
public class TripStatusController {

    private final TripStatusService tripStatusService;

    private final PushNotificationService pushNotificationService;

    /**
     * PUT  /api/trip-status : update status of the trip by "id" and "status".
     *
     * @param tripId the id of the trip
     * @param status new status for the trip
     * @return the ResponseEntity with status 200 (OK) and with body the trip, or with status 404 (Not Found)
     */
    @PutMapping
    public ResponseEntity<Trip> updateStatus(@RequestParam(value = "tripId") Integer tripId,
                                             @RequestParam(value = "status") TripStatusCodeEnum status) {
        log.debug("REST request to set status {} for the Trip with id : {}", status, tripId);

        Optional<Trip> tripOptional = tripStatusService.updateTripStatus(tripId, status);
        tripOptional.ifPresent(pushNotificationService::notifyTripStatusChanged);

        return tripOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
