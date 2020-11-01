package com.tripfellows.server.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.tripfellows.server.entity.AccountEntity;
import com.tripfellows.server.model.Account;
import com.tripfellows.server.model.request.SignUpRequest;
import lombok.SneakyThrows;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountMapperTest {

    AccountMapper mapper = Mappers.getMapper(AccountMapper.class);

    @Test
    @SneakyThrows
    public void accountConversionTest() {
        EasyRandom generator = new EasyRandom();

        Account expectedDto = generator.nextObject(Account.class);
        AccountEntity entity = mapper.map(expectedDto);
        Account actualDto = mapper.map(entity);

        ObjectWriter writer = new ObjectMapper().writerWithDefaultPrettyPrinter();
        assertEquals(writer.writeValueAsString(expectedDto), writer.writeValueAsString(actualDto),
                "Account dto and entity are not equal after conversion!");
    }

    @Test
    public void mappingFromSignUpRequestTest() {
        EasyRandom generator = new EasyRandom();

        SignUpRequest request = generator.nextObject(SignUpRequest.class);
        Account account = mapper.map(request);

        assertEquals(request.getName(), account.getName());
        assertEquals(request.getPhoneNumber(), account.getPhoneNumber());
    }
}