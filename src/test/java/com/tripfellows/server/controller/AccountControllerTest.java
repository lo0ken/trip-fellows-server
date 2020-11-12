package com.tripfellows.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripfellows.server.model.Account;
import com.tripfellows.server.security.SecurityService;
import com.tripfellows.server.service.api.AccountService;
import org.jeasy.random.EasyRandom;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;
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

    @MockBean
    SecurityService securityService;

    EasyRandom easyRandom = new EasyRandom();

    @Before
    public void setUp() {
        when(securityService.getAuthenticatedUserUid())
                .thenReturn(easyRandom.nextObject(String.class));
    }

    @Test
    @WithMockUser
    public void getAccountWhenExistsTest() throws Exception {
        Account account = easyRandom.nextObject(Account.class);

        when(accountService.findById(account.getId())).thenReturn(Optional.of(account));

        mockMvc.perform(get("/api/accounts/{id}", account.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(account.getId()));
    }

    @Test
    @WithMockUser
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
    @WithMockUser
    public void createAccountTest() throws Exception {
        EasyRandom easyRandom = new EasyRandom();
        Account toCreate = easyRandom.nextObject(Account.class);
        toCreate.setId(null);
        Account created = new Account(toCreate.getUid(), toCreate.getName(), toCreate.getPhoneNumber());
        created.setId(1);

        when(accountService.save(toCreate)).thenReturn(created);

        mockMvc.perform(post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(toCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(created.getId()))
                .andExpect(jsonPath("$.uid").value(created.getUid()))
                .andExpect(jsonPath("$.name").value(created.getName()))
                .andExpect(jsonPath("$.phoneNumber").value(created.getPhoneNumber()));
    }

    @Test
    @WithMockUser
    public void createAccountWithExistingId() throws Exception {
        Account account = new EasyRandom().nextObject(Account.class);

        mockMvc.perform(post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(account)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser
    public void checkAccountIsRequired() throws Exception {
        mockMvc.perform(post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(null)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser
    public void checkSetUidWhenCreateAccount() throws Exception {
        Account someAccount = new EasyRandom().nextObject(Account.class);
        someAccount.setUid(null);
        someAccount.setId(null);

        when(accountService.save(someAccount)).thenReturn(someAccount);

        mockMvc.perform(post("/api/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(someAccount)))
                .andExpect(status().isCreated());

        verify(accountService).save(argThat(account -> Objects.nonNull(account.getUid())));
    }

    @Test
    @WithMockUser
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
    @WithMockUser
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
    @WithMockUser
    public void deleteAccountTest() throws Exception {
        Integer idToDelete = easyRandom.nextInt();

        mockMvc.perform(delete("/api/accounts/{id}", idToDelete))
                .andExpect(status().isOk());

        verify(accountService).deleteById(idToDelete);
    }
}