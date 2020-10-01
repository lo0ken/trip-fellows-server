package com.tripfellows.server.controller;

import com.tripfellows.server.service.api.TripStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
