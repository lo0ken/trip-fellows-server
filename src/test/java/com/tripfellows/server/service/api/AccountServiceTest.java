package com.tripfellows.server.service.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.tripfellows.server.entity.AccountEntity;
import com.tripfellows.server.mapper.AccountMapper;
import com.tripfellows.server.model.Account;
import com.tripfellows.server.repository.AccountRepository;
import com.tripfellows.server.service.impl.AccountServiceImpl;
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
public class AccountServiceTest {
    @InjectMocks
    AccountServiceImpl accountService;

    @Mock
    AccountRepository accountRepository;


    AccountMapper accountMapper = Mappers.getMapper(AccountMapper.class);

    @Test
    public void findWhenAccountExistsTest() throws JsonProcessingException {
        AccountEntity saved = new EasyRandom().nextObject(AccountEntity.class);

        when(accountRepository.findById(saved.getId())).thenReturn(Optional.of(saved));

        Optional<Account> account = accountService.findById(saved.getId());

        assertTrue(account.isPresent());

        ObjectWriter writer = new ObjectMapper().writerWithDefaultPrettyPrinter();
        assertEquals(writer.writeValueAsString(accountMapper.map(saved)), writer.writeValueAsString(account.get()));
    }

    @Test
    public void findWhenAccountDoesNotExistsTest() {
        when(accountRepository.findById(any())).thenReturn(Optional.empty());
        Optional<Account> account = accountService.findById(1);

        assertFalse(account.isPresent());
    }
}