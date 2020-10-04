package com.tripfellows.server.service.impl;

import com.tripfellows.server.mapper.TripAccountMapper;
import com.tripfellows.server.model.TripMember;
import com.tripfellows.server.repository.TripAccountRepository;
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

    public TripAccountServiceImpl(TripAccountRepository tripAccountRepository) {
        this.tripAccountRepository = tripAccountRepository;
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
}
