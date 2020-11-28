package com.tripfellows.server.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.tripfellows.server.entity.TripAccountEntity;
import com.tripfellows.server.entity.TripEntity;
import com.tripfellows.server.model.Trip;
import com.tripfellows.server.model.TripMember;
import lombok.SneakyThrows;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TripMapperTest {
    TripMapper mapper = Mappers.getMapper(TripMapper.class);

    @Test
    @SneakyThrows
    public void tripConversionTest() {
        ObjectWriter writer = new ObjectMapper().writerWithDefaultPrettyPrinter();
        EasyRandom generator = new EasyRandom();

        Trip expectedDto = generator.nextObject(Trip.class);
        TripEntity entity = mapper.map(expectedDto);
        Trip actualDto = mapper.map(entity);

        assertEquals(expectedDto.getId(), actualDto.getId());
        assertEquals(writer.writeValueAsString(expectedDto.getDepartureAddress()), writer.writeValueAsString(actualDto.getDepartureAddress()));
        assertEquals(writer.writeValueAsString(expectedDto.getDestinationAddress()), writer.writeValueAsString(actualDto.getDestinationAddress()));
        assertEquals(writer.writeValueAsString(expectedDto.getPrice()), writer.writeValueAsString(actualDto.getPrice()));
        assertEquals(writer.writeValueAsString(expectedDto.getCreator()), writer.writeValueAsString(actualDto.getCreator()));
        assertEquals(writer.writeValueAsString(expectedDto.getCreateDate()), writer.writeValueAsString(actualDto.getCreateDate()));
        assertEquals(writer.writeValueAsString(expectedDto.getStartDate()), writer.writeValueAsString(actualDto.getStartDate()));
        assertEquals(writer.writeValueAsString(expectedDto.getEndDate()), writer.writeValueAsString(actualDto.getEndDate()));
        assertEquals(expectedDto.getComment(), actualDto.getComment());
        assertEquals(writer.writeValueAsString(expectedDto.getStatus()), writer.writeValueAsString(actualDto.getStatus()));
        assertEquals(expectedDto.getPlacesCount(), actualDto.getPlacesCount());
    }

    @Test
    public void tripMembersConversionTest() {
        EasyRandom generator = new EasyRandom();
        TripEntity entity = generator.nextObject(TripEntity.class);
        Trip dto = mapper.map(entity);

        List<TripAccountEntity> tripToAccounts = entity.getTripToAccounts();

        assertEquals(tripToAccounts.size(), dto.getMembers().size());

        for (int i = 0; i < tripToAccounts.size(); i++) {
            TripAccountEntity tripAccount = tripToAccounts.get(i);
            TripMember tripMember = dto.getMembers().get(i);

            assertEqualsMemberToTripAccount(tripAccount, tripMember);
        }
    }

    private void assertEqualsMemberToTripAccount(TripAccountEntity tripAccount, TripMember tripMember) {
        assertEquals(tripAccount.getAccount().getId(), tripMember.getAccount().getId());
        assertEquals(tripAccount.getAccount().getName(), tripMember.getAccount().getName());
        assertEquals(tripAccount.getAccount().getPhoneNumber(), tripMember.getAccount().getPhoneNumber());
        assertEquals(tripAccount.getRole().getId(), tripMember.getRole().getId());
        assertEquals(tripAccount.getRole().getCode(), tripMember.getRole().getCode());
        assertEquals(tripAccount.getRole().getName(), tripMember.getRole().getName());
    }
}
