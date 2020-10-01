package com.tripfellows.server.service.impl;

import com.tripfellows.server.repository.TripRepository;
import com.tripfellows.server.service.api.TripService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service for Trips
 */
@Slf4j
@Service
@Transactional
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;

    public TripServiceImpl(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }
}
