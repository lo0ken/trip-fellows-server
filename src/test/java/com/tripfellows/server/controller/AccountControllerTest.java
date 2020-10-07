package com.tripfellows.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.mockito.Mockito.*;
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

    @Test
    public void createAccountTest() throws Exception {
        EasyRandom easyRandom = new EasyRandom();
        Account toCreate = easyRandom.nextObject(Account.class);
        toCreate.setId(null);
        Account created = new Account(toCreate.getName(), toCreate.getPhoneNumber());
        created.setId(1);

        when(accountService.save(toCreate)).thenReturn(created);

        mockMvc.perform(post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(toCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(created.getId()))
                .andExpect(jsonPath("$.name").value(created.getName()))
                .andExpect(jsonPath("$.phoneNumber").value(created.getPhoneNumber()));
    }

    @Test
    public void createAccountWithExistingId() throws Exception {
        Account account = new EasyRandom().nextObject(Account.class);

        mockMvc.perform(post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(account)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void checkAccountIsRequired() throws Exception {
        mockMvc.perform(post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(null)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    public void updateAccountTest() throws Exception {
        Account account = new EasyRandom().nextObject(Account.class);

        when(accountService.save(account)).thenReturn(account);

        mockMvc.perform(put("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(account)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(account)));
    }

    @Test
    public void updateNonExistingAccountTest() throws Exception {
        EasyRandom easyRandom = new EasyRandom();

        Account account = easyRandom.nextObject(Account.class);
        account.setId(null);
        Account created = easyRandom.nextObject(Account.class);

        when(accountService.save(account)).thenReturn(created);

        mockMvc.perform(put("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(account)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }
    @Test
    public void deleteAccountTest() throws Exception {
        Account account = easyRandom.nextObject(Account.class);
        account.setId(1);

        mockMvc.perform(delete("/api/accounts/{id}", account.getId()))
                .andExpect(status().isOk());

        verify(accountService).deleteById(account.getId());
    }
}