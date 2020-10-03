package com.tripfellows.server.service.impl;

import com.tripfellows.server.entity.TripEntity;
import com.tripfellows.server.mapper.TripMapper;
import com.tripfellows.server.model.Trip;
import com.tripfellows.server.repository.TripRepository;
import com.tripfellows.server.service.api.TripService;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


/**
 * Service for Trips
 */
@Slf4j
@Service
@Transactional
public class TripServiceImpl implements TripService {

    private final TripMapper tripMapper;

    private final TripRepository tripRepository;

    public TripServiceImpl(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
        tripMapper = Mappers.getMapper(TripMapper.class);
    }

    @Override
    public Optional<Trip> findById(Integer id) {
        log.debug("Retrieving trip with id {}", id);
        Optional<TripEntity> tripEntity = tripRepository.findById(id);

        return tripEntity.map(tripMapper::map);
    }
}
