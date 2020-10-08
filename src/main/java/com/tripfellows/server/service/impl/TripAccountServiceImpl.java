package com.tripfellows.server.service.impl;

import com.tripfellows.server.entity.TripAccountEntity;
import com.tripfellows.server.enums.RoleCodeEnum;
import com.tripfellows.server.mapper.TripAccountMapper;
import com.tripfellows.server.model.TripMember;
import com.tripfellows.server.repository.TripAccountRepository;
import com.tripfellows.server.service.api.RoleService;
import com.tripfellows.server.service.api.TripAccountService;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for TripAccount many to many relationship
 */
@Slf4j
@Service
@Transactional
public class TripAccountServiceImpl implements TripAccountService {

    private final TripAccountMapper tripAccountMapper;

    private final TripAccountRepository tripAccountRepository;

    private final RoleService roleService;

    public TripAccountServiceImpl(TripAccountRepository tripAccountRepository, RoleService roleService) {
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
        Integer roleId = roleService.findByCode(roleCode).getId();

        TripAccountEntity entity = tripAccountMapper.from(tripId, accountId, roleId);

        TripAccountEntity saved = tripAccountRepository.save(entity);
        log.debug("Saved new trip_account record with id : {}", saved.getId());

        return tripAccountMapper.map(saved);
    }

    @Override
    public void removeTripMember(Integer tripMemberId) {
        log.debug("Deleting trip member from trip_account table with id : {}", tripMemberId);
        tripAccountRepository.deleteById(tripMemberId);
    }
}
