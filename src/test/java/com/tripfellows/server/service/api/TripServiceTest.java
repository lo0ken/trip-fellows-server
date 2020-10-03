package com.tripfellows.server.service.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.tripfellows.server.entity.TripEntity;
import com.tripfellows.server.mapper.TripMapper;
import com.tripfellows.server.model.Trip;
import com.tripfellows.server.repository.TripRepository;
import com.tripfellows.server.service.impl.TripServiceImpl;
import org.jeasy.random.EasyRandom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TripServiceTest {
    @Mock
    TripRepository tripRepository;

    @InjectMocks
    TripServiceImpl tripService;

    TripMapper tripMapper = Mappers.getMapper(TripMapper.class);

    @Test
    public void findWhenTripExistsTest() throws JsonProcessingException {
        TripEntity saved = new EasyRandom().nextObject(TripEntity.class);

        when(tripRepository.findById(any())).thenReturn(Optional.of(saved));

        Optional<Trip> trip = tripService.findById(saved.getId());

        assertTrue(trip.isPresent());

        ObjectWriter writer = new ObjectMapper().writerWithDefaultPrettyPrinter();
        assertEquals(writer.writeValueAsString(tripMapper.map(saved)), writer.writeValueAsString(trip.get()));
    }

    @Test
    public void findWhenAccountDoesNotExistsTest() {
        when(tripRepository.findById(any())).thenReturn(Optional.empty());
        Optional<Trip> trip = tripService.findById(1);

        assertFalse(trip.isPresent());
    }
}