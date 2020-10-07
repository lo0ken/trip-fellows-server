package com.tripfellows.server.service.impl;

import com.tripfellows.server.entity.TripEntity;
import com.tripfellows.server.enums.TripStatusCodeEnum;
import com.tripfellows.server.mapper.TripMapper;
import com.tripfellows.server.model.Point;
import com.tripfellows.server.model.Trip;
import com.tripfellows.server.model.TripMember;
import com.tripfellows.server.repository.TripRepository;
import com.tripfellows.server.service.api.PointService;
import com.tripfellows.server.service.api.TripAccountService;
import com.tripfellows.server.service.api.TripService;
import com.tripfellows.server.service.api.TripStatusService;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

/**
 * Service for Trips
 */
@Slf4j
@Service
@Transactional
public class TripServiceImpl implements TripService {

    private final TripMapper tripMapper;

    private final TripRepository tripRepository;

    private final TripStatusService tripStatusService;

    private final TripAccountService tripAccountService;

    private final PointService pointService;

    public TripServiceImpl(TripRepository tripRepository, TripStatusService tripStatusService,
                           TripAccountService tripAccountService, PointService pointService) {
        this.tripRepository = tripRepository;
        this.tripStatusService = tripStatusService;
        this.tripAccountService = tripAccountService;
        this.pointService = pointService;
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

    @Override
    public Trip create(Trip trip) {
        trip.setStatus(tripStatusService.findByCode(TripStatusCodeEnum.WAITING));

        return save(trip);
    }

    @Override
    public Trip update(Trip trip) {
        return save(trip);
    }

    private Trip save(Trip trip) {
        saveEndPoints(trip);

        TripEntity saved = tripRepository.save(tripMapper.map(trip));
        List<TripMember> members = tripAccountService.saveAll(saved.getId(), trip.getMembers());

        Trip result = tripMapper.map(saved);
        result.setMembers(members);

        log.debug("Account with id {} has been saved", saved.getId());
        return result;
    }

    private void saveEndPoints(Trip trip) {
        Point startPoint = trip.getStartPoint();
        Point endPoint = trip.getEndPoint();

        if (isNull(startPoint.getId())) {
            trip.setStartPoint(pointService.save(startPoint));
        }

        if (isNull(endPoint.getId())) {
            trip.setEndPoint(pointService.save(endPoint));
        }
    }
}
