package com.tripfellows.server.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.tripfellows.server.entity.PointEntity;
import com.tripfellows.server.model.Point;
import lombok.SneakyThrows;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PointMapperTest {

    PointMapper mapper = Mappers.getMapper(PointMapper.class);

    @Test
    @SneakyThrows
    public void roleConversionTest() {
        EasyRandom generator = new EasyRandom();

        Point expectedDto = generator.nextObject(Point.class);
        PointEntity entity = mapper.map(expectedDto);
        Point actualDto = mapper.map(entity);

        ObjectWriter writer = new ObjectMapper().writerWithDefaultPrettyPrinter();
        assertEquals(writer.writeValueAsString(expectedDto), writer.writeValueAsString(actualDto),
                "Point dto and entity are not equal after conversion!");
    }
}