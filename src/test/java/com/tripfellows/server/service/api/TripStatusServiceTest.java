package com.tripfellows.server.service.api;

import com.tripfellows.server.entity.TripStatusEntity;
import com.tripfellows.server.enums.TripStatusCodeEnum;
import com.tripfellows.server.model.Trip;
import com.tripfellows.server.model.TripStatus;
import com.tripfellows.server.repository.TripStatusRepository;
import com.tripfellows.server.service.impl.TripStatusServiceImpl;
import org.jeasy.random.EasyRandom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TripStatusServiceTest {

    @Mock
    TripStatusRepository tripStatusRepository;

    @Mock
    TripService tripService;

    @InjectMocks
    TripStatusServiceImpl tripStatusService;

    @Test
    public void findByCodeTest() {
        EasyRandom easyRandom = new EasyRandom();
        TripStatusCodeEnum expectedCode = easyRandom.nextObject(TripStatusCodeEnum.class);
        TripStatusEntity statusEntity = easyRandom.nextObject(TripStatusEntity.class);
        statusEntity.setCode(expectedCode);

        when(tripStatusRepository.findByCode(expectedCode)).thenReturn(statusEntity);

        TripStatus actualCode = tripStatusService.findByCode(expectedCode);

        assertEquals(expectedCode, actualCode.getCode());
    }

    @Test
    public void updateStatus() {
        EasyRandom easyRandom = new EasyRandom();
        Trip saved = easyRandom.nextObject(Trip.class);
        saved.setId(1);

        TripStatusCodeEnum statusToSet = easyRandom.nextObject(TripStatusCodeEnum.class);

        TripStatusEntity statusEntity = easyRandom.nextObject(TripStatusEntity.class);
        statusEntity.setCode(statusToSet);

        when(tripService.findById(saved.getId())).thenReturn(Optional.of(saved));
        when(tripService.update(any())).thenAnswer((Answer) invocation -> invocation.getArguments()[0]);
        when(tripStatusRepository.findByCode(statusEntity.getCode())).thenReturn(statusEntity);

        Optional<Trip> actual = tripStatusService.updateTripStatus(saved.getId(), statusToSet);

        verify(tripService).update(argThat(a -> a.getStatus().getCode().equals(statusToSet)));

        assertThat(actual.isPresent()).isEqualTo(true);
        actual.ifPresent(trip -> assertEquals(statusToSet, trip.getStatus().getCode()));
    }
}