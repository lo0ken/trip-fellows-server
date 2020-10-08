package com.tripfellows.server.mapper;

import com.tripfellows.server.entity.TripAccountEntity;
import com.tripfellows.server.model.TripMember;
import org.jeasy.random.EasyRandom;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TripAccountMapperTest {

    TripAccountMapper tripAccountMapper = Mappers.getMapper(TripAccountMapper.class);

    @Test
    public void tripAccountConversionTest() {
        EasyRandom easyRandom = new EasyRandom();
        TripMember tripMember = easyRandom.nextObject(TripMember.class);
        Integer tripId = easyRandom.nextInt();

        TripAccountEntity entity = tripAccountMapper.map(tripMember, tripId);

        assertEquals(tripId, entity.getTrip().getId());
        assertEquals(tripMember.getAccount().getId(), entity.getAccount().getId());
        assertEquals(tripMember.getRole().getId(), entity.getRole().getId());
    }

    @Test
    public void tripAccountEntityToDtoConversion() {
        EasyRandom easyRandom = new EasyRandom();
        TripAccountEntity entity = easyRandom.nextObject(TripAccountEntity.class);

        TripMember member = tripAccountMapper.map(entity);

        assertEquals(entity.getId(), member.getId());
        assertEquals(entity.getAccount().getId(), member.getAccount().getId());
        assertEquals(entity.getRole().getCode(), member.getRole().getCode());
    }
}