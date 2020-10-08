package com.tripfellows.server.controller;

import com.tripfellows.server.enums.TripStatusCodeEnum;
import com.tripfellows.server.model.Trip;
import com.tripfellows.server.service.api.TripService;
import com.tripfellows.server.service.api.TripStatusService;
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
public class TripStatusController {

    private final TripStatusService tripStatusService;

    public TripStatusController(TripStatusService tripStatusService) {
        this.tripStatusService = tripStatusService;
    }

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

        Optional<Trip> trip = tripStatusService.updateTripStatus(tripId, status);
        return trip.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
