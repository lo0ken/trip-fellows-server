package com.tripfellows.server.controller;

import com.tripfellows.server.model.Trip;
import com.tripfellows.server.service.api.TripService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * GET  /trips/:id : get the "id" trip.
     *
     * @param id the id of the trip to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the group, or with status 404 (Not Found)
     */
    @GetMapping("/trips/{id}")
    public ResponseEntity<Trip> findTrip(@PathVariable Integer id) {
        log.debug("REST request to get Trip : {}", id);

        Optional<Trip> trip = tripService.findById(id);
        return trip.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
