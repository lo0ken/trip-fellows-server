package com.tripfellows.server.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.tripfellows.server.entity.TripEntity;
import com.tripfellows.server.model.Trip;
import lombok.SneakyThrows;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TripMapperTest {
    TripMapper mapper = Mappers.getMapper(TripMapper.class);

    @Test
    @SneakyThrows
    public void tripConversionTest() {
        EasyRandom generator = new EasyRandom();

        Trip expectedDto = generator.nextObject(Trip.class);
        TripEntity entity = mapper.map(expectedDto);
        Trip actualDto = mapper.map(entity);

        ObjectWriter writer = new ObjectMapper().writerWithDefaultPrettyPrinter();
        assertEquals(writer.writeValueAsString(expectedDto), writer.writeValueAsString(actualDto),
                "Trip dto and entity are not equal after conversion!");
    }
}
