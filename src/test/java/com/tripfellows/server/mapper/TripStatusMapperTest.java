package com.tripfellows.server.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.tripfellows.server.entity.PointEntity;
import com.tripfellows.server.entity.TripStatusEntity;
import com.tripfellows.server.model.Point;
import com.tripfellows.server.model.TripStatus;
import lombok.SneakyThrows;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

public class TripStatusMapperTest {

    TripStatusMapper mapper = Mappers.getMapper(TripStatusMapper.class);

    @Test
    @SneakyThrows
    public void roleConversionTest() {
        EasyRandom generator = new EasyRandom();

        TripStatus expectedDto = generator.nextObject(TripStatus.class);
        TripStatusEntity entity = mapper.map(expectedDto);
        TripStatus actualDto = mapper.map(entity);

        ObjectWriter writer = new ObjectMapper().writerWithDefaultPrettyPrinter();
        assertEquals(writer.writeValueAsString(expectedDto), writer.writeValueAsString(actualDto),
                "TripStatus dto and entity are not equal after conversion!");
    }
}