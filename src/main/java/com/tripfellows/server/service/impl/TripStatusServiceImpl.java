package com.tripfellows.server.service.impl;

import com.tripfellows.server.repository.TripStatusRepository;
import com.tripfellows.server.service.api.TripStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service for Trip status
 */
@Slf4j
@Service
@Transactional
public class TripStatusServiceImpl implements TripStatusService {

    private final TripStatusRepository tripStatusRepository;

    public TripStatusServiceImpl(TripStatusRepository tripStatusRepository) {
        this.tripStatusRepository = tripStatusRepository;
    }
}
