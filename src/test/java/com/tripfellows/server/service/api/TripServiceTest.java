package com.tripfellows.server.service.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.tripfellows.server.entity.AccountEntity;
import com.tripfellows.server.entity.TripAccountEntity;
import com.tripfellows.server.entity.TripEntity;
import com.tripfellows.server.entity.TripStatusEntity;
import com.tripfellows.server.enums.TripStatusCodeEnum;
import com.tripfellows.server.mapper.TripMapper;
import com.tripfellows.server.model.Point;
import com.tripfellows.server.model.Trip;
import com.tripfellows.server.model.TripStatus;
import com.tripfellows.server.repository.TripRepository;
import com.tripfellows.server.service.impl.PointServiceImpl;
import com.tripfellows.server.service.impl.TripServiceImpl;
import com.tripfellows.server.service.impl.TripStatusServiceImpl;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class TripServiceTest {
    @Mock
    TripRepository tripRepository;

    @Mock
    TripStatusServiceImpl tripStatusService;

    @Mock
    PointServiceImpl pointService;

    @Mock
    TripAccountService tripAccountService;

    @InjectMocks
    TripServiceImpl tripService;

    TripMapper tripMapper = Mappers.getMapper(TripMapper.class);

    @Test
    public void findWhenTripExistsTest() throws JsonProcessingException {
        TripEntity saved = new EasyRandom().nextObject(TripEntity.class);

        when(tripRepository.findById(saved.getId())).thenReturn(Optional.of(saved));

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

    @Test
    public void findAllTripsByAccountTest() throws JsonProcessingException {
        int accountId = 1;
        int quantity = 10;

        AccountEntity account = new EasyRandom().nextObject(AccountEntity.class);
        account.setId(accountId);

        EasyRandomParameters parameters = new EasyRandomParameters()
                .randomize(AccountEntity.class, () -> account);

        List<TripEntity> saved = new EasyRandom(parameters)
                .objects(TripAccountEntity.class, quantity)
                .map(TripAccountEntity::getTrip)
                .collect(toList());

        when(tripRepository.findByAccountId(accountId)).thenReturn(saved);

        List<Trip> expected = saved.stream()
                .map(tripMapper::map)
                .collect(toList());

        List<Trip> result = tripService.findByAccountId(accountId);

        ObjectWriter writer = new ObjectMapper().writerWithDefaultPrettyPrinter();
        assertEquals(writer.writeValueAsString(expected), writer.writeValueAsString(result));
    }

    @Test
    public void findAllTripsTest() {
        List<TripEntity> expectedTrips = new EasyRandom().objects(TripEntity.class, 10).collect(toList());
        when(tripRepository.findAll()).thenReturn(expectedTrips);

        List<Trip> result = tripService.findAll();

        assertThat(result).hasSize(expectedTrips.size());
    }

    @Test
    public void findAllActiveTripsTest() {
        List<TripEntity> expectedTrips = new EasyRandom().objects(TripEntity.class, 10).collect(toList());
        when(tripRepository.findAllActive()).thenReturn(expectedTrips);

        List<Trip> result = tripService.findAllActive();

        assertThat(result).hasSize(expectedTrips.size());
    }

    @Test
    public void findAllWhenEmptyTest() {
        when(tripRepository.findAll()).thenReturn(Collections.emptyList());
        assertThat(tripService.findAll()).hasSize(0);
    }

    @Test
    public void checkStatusWaitingTest() {
        TripStatus finishedStatus = new TripStatus(TripStatusCodeEnum.FINISHED, "");
        TripStatus waitingStatus = new TripStatus(TripStatusCodeEnum.WAITING, "");

        Trip tripToSave = new EasyRandom().nextObject(Trip.class);
        tripToSave.setStatus(finishedStatus);
        tripToSave.setMembers(Collections.emptyList());

        TripEntity tripEntity = tripMapper.map(tripToSave);
        tripEntity.setStatus(new TripStatusEntity(1, TripStatusCodeEnum.WAITING, ""));

        when(tripStatusService.findByCode(TripStatusCodeEnum.WAITING)).thenReturn(waitingStatus);
        when(tripRepository.save(any())).thenReturn(tripEntity);
        when(tripAccountService.saveAll(any(), any())).thenReturn(Collections.emptyList());

        Trip tripSaved = tripService.create(tripToSave);

        assertEquals(TripStatusCodeEnum.WAITING, tripSaved.getStatus().getCode());

        verify(tripRepository).save(argThat(t -> t.getStatus().getCode().equals(TripStatusCodeEnum.WAITING)));
    }

    @Test
    public void checkSaveMembersTest() {
        Trip trip = new EasyRandom().nextObject(Trip.class);

        TripEntity tripEntity = tripMapper.map(trip);

        when(tripRepository.save(any())).thenReturn(tripEntity);
        when(tripAccountService.saveAll(tripEntity.getId(), trip.getMembers())).thenReturn(trip.getMembers());

        Trip result = tripService.create(trip);

        verify(tripAccountService).saveAll(eq(tripEntity.getId()),
                argThat(members -> members.containsAll(trip.getMembers())));

        assertThat(result.getMembers()).containsAll(trip.getMembers());
    }

    @Test
    public void checkDoesNotSaveMembersWhenNullTest() {
        Trip trip = new EasyRandom().nextObject(Trip.class);
        trip.setMembers(null);
        TripEntity tripEntity = tripMapper.map(trip);

        when(tripRepository.save(any())).thenReturn(tripEntity);

        Trip result = tripService.create(trip);

        verify(tripAccountService, never()).saveAll(any(), any());
        assertThat(result.getMembers()).hasSize(0);
    }

    @Test
    public void checkPointSaved() {
        EasyRandom easyRandom = new EasyRandom();
        Trip trip = easyRandom.nextObject(Trip.class);

        Point startPoint = trip.getDepartureAddress();
        startPoint.setId(null);
        Point endPoint = trip.getDestinationAddress();
        endPoint.setId(null);

        Point startPointWithId = new Point(startPoint.getLatitude(), startPoint.getLongitude(), startPoint.getAddress());
        startPointWithId.setId(easyRandom.nextInt());

        Point endPointWithId = new Point(endPoint.getLatitude(), endPoint.getLongitude(), endPoint.getAddress());
        endPointWithId.setId(easyRandom.nextInt());

        TripEntity tripEntity = tripMapper.map(trip);

        when(tripRepository.save(any())).thenReturn(tripEntity);
        when(pointService.save(startPoint)).thenReturn(startPointWithId);
        when(pointService.save(endPoint)).thenReturn(endPointWithId);

        tripService.create(trip);

        verify(pointService).save(startPoint);
        verify(pointService).save(endPoint);
    }

    @Test
    public void checkPointNotSaved() {
        EasyRandom easyRandom = new EasyRandom();
        Trip trip = easyRandom.nextObject(Trip.class);

        TripEntity tripEntity = tripMapper.map(trip);

        when(tripRepository.save(any())).thenReturn(tripEntity);

        tripService.create(trip);

        verify(pointService, never()).save(any());
    }
}