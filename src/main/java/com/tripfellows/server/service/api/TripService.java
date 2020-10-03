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
}
