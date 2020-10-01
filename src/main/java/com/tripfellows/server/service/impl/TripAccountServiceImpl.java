package com.tripfellows.server.service.impl;

import com.tripfellows.server.repository.TripAccountRepository;
import com.tripfellows.server.service.api.TripAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service for TripAccount many to many relationship
 */
@Slf4j
@Service
@Transactional
public class TripAccountServiceImpl implements TripAccountService {

    private final TripAccountRepository tripAccountRepository;

    public TripAccountServiceImpl(TripAccountRepository tripAccountRepository) {
        this.tripAccountRepository = tripAccountRepository;
    }
}
