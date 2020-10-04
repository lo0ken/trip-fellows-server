package com.tripfellows.server.service.api;

import com.tripfellows.server.entity.TripStatusEntity;
import com.tripfellows.server.enums.TripStatusCodeEnum;
import com.tripfellows.server.model.TripStatus;
import com.tripfellows.server.repository.TripStatusRepository;
import com.tripfellows.server.service.impl.TripStatusServiceImpl;
import org.jeasy.random.EasyRandom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TripStatusServiceTest {

    @Mock
    TripStatusRepository tripStatusRepository;

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
}