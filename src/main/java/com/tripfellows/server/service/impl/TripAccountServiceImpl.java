package com.tripfellows.server.service.impl;

import com.tripfellows.server.entity.TripAccountEntity;
import com.tripfellows.server.enums.RoleCodeEnum;
import com.tripfellows.server.exception.NoAvailablePlacesFoundException;
import com.tripfellows.server.exception.PassengerOfAnotherTripException;
import com.tripfellows.server.mapper.TripAccountMapper;
import com.tripfellows.server.model.Trip;
import com.tripfellows.server.model.TripMember;
import com.tripfellows.server.repository.TripAccountRepository;
import com.tripfellows.server.service.api.RoleService;
import com.tripfellows.server.service.api.TripAccountService;
import com.tripfellows.server.service.api.TripService;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service for TripAccount many to many relationship
 */
@Slf4j
@Service
@Transactional
public class TripAccountServiceImpl implements TripAccountService {

    private final TripAccountMapper tripAccountMapper;

    private final TripService tripService;

    private final TripAccountRepository tripAccountRepository;

    private final RoleService roleService;

    public TripAccountServiceImpl(@Lazy TripService tripService, TripAccountRepository tripAccountRepository, RoleService roleService) {
        this.tripService = tripService;
        this.tripAccountRepository = tripAccountRepository;
        this.roleService = roleService;
        tripAccountMapper = Mappers.getMapper(TripAccountMapper.class);
    }

    @Override
    public List<TripMember> saveAll(Integer tripId, List<TripMember> members) {
        for (TripMember member: members) {
            tripAccountRepository.save(tripAccountMapper.map(member, tripId));
        }

        log.debug("Saved members to trip with id: {}", tripId);

        return members;
    }

    @Override
    public TripMember addTripMember(Integer tripId, Integer accountId, RoleCodeEnum roleCode) {
        if (isPassengerOfAnotherTrip(accountId)) {
            throw new PassengerOfAnotherTripException();
        }

        if (tripService.findAvailablePlacesOfTrip(tripId) <= 0) {
            throw new NoAvailablePlacesFoundException();
        }

        Integer roleId = roleService.findByCode(roleCode).getId();

        TripAccountEntity entity = tripAccountMapper.from(tripId, accountId, roleId);

        TripAccountEntity saved = tripAccountRepository.save(entity);
        log.debug("Saved new trip_account record with id : {}", saved.getId());

        return tripAccountMapper.map(saved);
    }

    private boolean isPassengerOfAnotherTrip(Integer accountId) {
        Optional<Trip> currentPassengerTrip = tripService.findCurrentTrip(accountId);
        return currentPassengerTrip.isPresent();
    }

    @Override
    public void removeTripMember(Integer tripMemberId) {
        log.debug("Deleting trip member from trip_account table with id : {}", tripMemberId);
        tripAccountRepository.deleteById(tripMemberId);
    }
}
