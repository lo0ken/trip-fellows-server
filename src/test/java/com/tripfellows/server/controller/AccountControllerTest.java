package com.tripfellows.server.controller;

import com.tripfellows.server.model.Account;
import com.tripfellows.server.service.api.AccountService;
import org.jeasy.random.EasyRandom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AccountService accountService;

    EasyRandom easyRandom = new EasyRandom();

    @Test
    public void getAccountWhenExistsTest() throws Exception {
        Account account = easyRandom.nextObject(Account.class);

        when(accountService.findById(account.getId())).thenReturn(Optional.of(account));

        mockMvc.perform(get("/api/accounts/{id}", account.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(account.getId()));
    }

    @Test
    public void getAccountWhenNotExistsTest() throws Exception {
        Integer existsId = 1;
        Integer notExistsId = 2;

        Account account = new Account();
        account.setId(existsId);

        when(accountService.findById(account.getId())).thenReturn(Optional.of(account));

        mockMvc.perform(get("/api/accounts/{id}", notExistsId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist());
    }
}