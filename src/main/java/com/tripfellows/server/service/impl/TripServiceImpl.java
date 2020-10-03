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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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

    @Override
    public List<Trip> findByAccountId(Integer accountId) {
        log.debug("Retrieving trips of account with id {}", accountId);
        List<TripEntity> trips = tripRepository.findByAccountId(accountId);

        return trips.stream()
                .map(tripMapper::map)
                .collect(Collectors.toList());
    }
}
