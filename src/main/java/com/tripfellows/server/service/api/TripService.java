package com.tripfellows.server.service.api;

import com.tripfellows.server.model.Trip;

import java.util.List;
import java.util.Optional;

public interface TripService {

    /**
     * Retrieve trip from database
     *
     * @param id trip id to retrieve
     * @return Optional with body the trip or Optional.empty
     */
    Optional<Trip> findById(Integer id);

    /**
     * Retrieve trips from database, filtered by accountId
     *
     * @param accountId id of account which trips to retrieve
     * @return list of trips of the account or empty list
     */
    List<Trip> findByAccountId(Integer accountId);

    /**
     * Retrieve all trips from database
     *
     * @return list of all trips
     */
    List<Trip> findAll();

    /**
     * Retrieve all active trips from database
     *
     * @return list of all trips with status 'WAITING'
     */
    List<Trip> findAllActive();

    /**
     * Save all information for a specific trip, and return the created trip
     *
     * @param trip trip to create
     * @return created trip
     */
    Trip create(Trip trip);

    /**
     * Update all information for a specific trip, and return the modified trip
     * Or create it if not exists
     *
     * @param trip trip to update
     * @return updated trip
     */
    Trip update(Trip trip);
}
