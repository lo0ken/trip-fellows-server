package com.tripfellows.server.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.tripfellows.server.model.Trip;
import com.tripfellows.server.security.SecurityService;
import com.tripfellows.server.service.api.PushNotificationService;
import com.tripfellows.server.service.api.TripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static java.util.Objects.isNull;

/**
 * REST controller for managing trips
 */
@Slf4j
@RestController
@RequestMapping("/api/trips")
@RequiredArgsConstructor
public class TripController {

    private final SecurityService securityService;

    private final TripService tripService;

    private final PushNotificationService pushNotificationService;

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
    public ResponseEntity<List<Trip>> findTripsByAccount(@RequestParam(value = "accountId") Integer accountId) {
        log.debug("REST request to get all trips of accountId: {}", accountId);

        List<Trip> trips = tripService.findByAccountId(accountId);
        return ResponseEntity.ok(trips);
    }

    /**
     * GET /api/trips : get all trips
     *
     * @return response entity with status 200 (OK) and body the list of all trips
     */
    @GetMapping
    public ResponseEntity<List<Trip>> getAllTrips() {
        log.debug("REST request to get all trips");

        List<Trip> all = tripService.findAll();

        return ResponseEntity.ok(all);
    }

    /**
     * GET /api/trips/active : get all trips, which user can join
     *
     * @return response entity with status 200 (OK) and body the list of active trips
     */
    @GetMapping("/active")
    public ResponseEntity<List<Trip>> getActiveTrips() {
        log.debug("REST request to get all trips");

        List<Trip> allActive = tripService.findAllActive();

        return ResponseEntity.ok(allActive);
    }


    /**
     * GET /api/trips/currentTrip : get current trip of user
     *
     * @return response entity with status 200 (OK) and body the current trip
     * or with status 404 (Not found) with empty body if trip does not exists
     */
    @GetMapping("/currentTrip")
    public ResponseEntity<Trip> getCurrentTrip() {
        Integer accountId = securityService.getCurrentAccount().getId();

        log.debug("REST request to get current trip of user with accountId: {}", accountId);

        Optional<Trip> trip = tripService.findCurrentTrip(accountId);

        return trip.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * POST /api/trips : create a new trip
     *
     * @param trip the trip to create
     * @return the ResponseEntity with status 201 (Created) and with body the new trip, or with status 400 (Bad Request) if the trip has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping
    public ResponseEntity<Trip> createTrip(@RequestBody Trip trip) throws URISyntaxException {
        log.debug("REST request to create trip");

        if (!isNull(trip.getId())) {
            return ResponseEntity.badRequest().body(null);
        }

        trip.setCreator(securityService.getCurrentAccount());

        Trip result = tripService.create(trip);
        pushNotificationService.notifyTripCreated(result);

        return ResponseEntity.created(new URI("/api/trips/" + result.getId()))
                .body(result);
    }
}
