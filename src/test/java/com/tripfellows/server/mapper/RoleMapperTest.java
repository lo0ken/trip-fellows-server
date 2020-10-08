package com.tripfellows.server.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.tripfellows.server.entity.RoleEntity;
import com.tripfellows.server.model.Role;
import lombok.SneakyThrows;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoleMapperTest {

    RoleMapper mapper = Mappers.getMapper(RoleMapper.class);

    @Test
    @SneakyThrows
    public void roleConversionTest() {
        EasyRandom generator = new EasyRandom();

        Role expectedDto = generator.nextObject(Role.class);
        RoleEntity entity = mapper.map(expectedDto);
        Role actualDto = mapper.map(entity);

        ObjectWriter writer = new ObjectMapper().writerWithDefaultPrettyPrinter();
        assertEquals(writer.writeValueAsString(expectedDto), writer.writeValueAsString(actualDto),
                "Role dto and entity are not equal after conversion!");
    }
}