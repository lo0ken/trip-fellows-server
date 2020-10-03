package com.tripfellows.server.controller;

import com.tripfellows.server.model.Trip;
import com.tripfellows.server.service.api.TripService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing trips
 */
@Slf4j
@RestController
@RequestMapping("/api/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    /**
     * GET  /api/trips/:id : get the "id" trip.
     *
     * @param id the id of the trip to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the trip, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Trip> findTrip(@PathVariable Integer id) {
        log.debug("REST request to get Trip : {}", id);

        Optional<Trip> trip = tripService.findById(id);
        return trip.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * GET  /api/trips/getByAccount/ : get trips of the "accountId".
     *
     * @param accountId the accountId of the trips to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the trips of account
     */
    @GetMapping("/getByAccount")
    public ResponseEntity<List<Trip>> findTripsByAccount(@RequestParam(value = "accountId", required = true) Integer accountId) {
        log.debug("REST request to get all trips of accountId: {}", accountId);

        List<Trip> trips = tripService.findTripsByAccount(accountId);
        return ResponseEntity.ok(trips);
    }
}
