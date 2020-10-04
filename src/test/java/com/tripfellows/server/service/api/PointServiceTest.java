package com.tripfellows.server.service.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.tripfellows.server.entity.PointEntity;
import com.tripfellows.server.mapper.PointMapper;
import com.tripfellows.server.model.Point;
import com.tripfellows.server.repository.PointRepository;
import com.tripfellows.server.service.impl.PointServiceImpl;
import org.jeasy.random.EasyRandom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PointServiceTest {

    @Mock
    PointRepository pointRepository;

    @InjectMocks
    PointServiceImpl pointService;

    PointMapper pointMapper = Mappers.getMapper(PointMapper.class);

    @Test
    public void savePointTest() throws JsonProcessingException {
        Point point = new EasyRandom().nextObject(Point.class);

        when(pointRepository.save(any(PointEntity.class))).thenReturn(pointMapper.map(point));
        Point saved = pointService.save(point);

        ObjectWriter writer = new ObjectMapper().writerWithDefaultPrettyPrinter();
        assertEquals(writer.writeValueAsString(point), writer.writeValueAsString(saved));
    }
}