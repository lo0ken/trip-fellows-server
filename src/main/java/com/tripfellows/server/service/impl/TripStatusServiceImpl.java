package com.tripfellows.server.service.impl;

import com.tripfellows.server.entity.TripStatusEntity;
import com.tripfellows.server.enums.TripStatusCodeEnum;
import com.tripfellows.server.mapper.TripStatusMapper;
import com.tripfellows.server.model.Trip;
import com.tripfellows.server.model.TripStatus;
import com.tripfellows.server.repository.TripStatusRepository;
import com.tripfellows.server.service.api.TripService;
import com.tripfellows.server.service.api.TripStatusService;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


/**
 * Service for Trip status
 */
@Slf4j
@Service
@Transactional
public class TripStatusServiceImpl implements TripStatusService {

    private final TripStatusMapper tripStatusMapper;

    private final TripService tripService;

    private final TripStatusRepository tripStatusRepository;

    public TripStatusServiceImpl(TripStatusRepository tripStatusRepository, @Lazy TripService tripService) {
        this.tripStatusRepository = tripStatusRepository;
        this.tripService = tripService;
        tripStatusMapper = Mappers.getMapper(TripStatusMapper.class);
    }

    @Override
    public TripStatus findByCode(TripStatusCodeEnum code) {
        log.debug("searching trip status by code : {}", code.getValue());
        TripStatusEntity tripStatusEntity = tripStatusRepository.findByCode(code);

        return tripStatusMapper.map(tripStatusEntity);
    }

    @Override
    public Optional<Trip> updateTripStatus(Integer tripId, TripStatusCodeEnum tripStatus) {
        Optional<Trip> saved = tripService.findById(tripId);

        if (saved.isEmpty()) {
            return Optional.empty();
        }

        Trip trip = saved.get();
        trip.setStatus(findByCode(tripStatus));
        log.debug("Status of trip with id {} has been saved. New status is {}", trip.getId(), tripStatus.getValue());

        return Optional.of(tripService.update(trip));
    }
}
