package com.tripfellows.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tripfellows.server.model.Account;
import com.tripfellows.server.model.Trip;
import com.tripfellows.server.security.SecurityService;
import com.tripfellows.server.service.api.TripAccountService;
import com.tripfellows.server.service.api.TripService;
import org.jeasy.random.EasyRandom;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TripController.class)
public class TripControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SecurityService securityService;

    @MockBean
    TripService tripService;

    @MockBean
    TripAccountService tripAccountService;

    EasyRandom easyRandom = new EasyRandom();

    ObjectMapper objectMapper;

    @Before
    public void configureObjectMapper() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    @WithMockUser
    public void getTripWhenExistsTest() throws Exception {
        Trip trip = easyRandom.nextObject(Trip.class);

        when(tripService.findById(trip.getId())).thenReturn(Optional.of(trip));

        mockMvc.perform(get("/api/trips/{id}", trip.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(trip.getId()));
    }

    @Test
    @WithMockUser
    public void getTripWhenNotExistsTest() throws Exception {
        Integer existsId = 1;
        Integer notExistsId = 2;

        Trip trip = Trip.builder().build();
        trip.setId(existsId);

        when(tripService.findById(trip.getId())).thenReturn(Optional.of(trip));

        mockMvc.perform(get("/api/trips/{id}", notExistsId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser
    public void getTripsByAccountWhenNotEmptyTest() throws Exception {
        Integer accountId = 1;
        int quantity = 10;

        List<Trip> expected = new EasyRandom().objects(Trip.class, quantity)
                .collect(toList());

        when(tripService.findByAccountId(accountId)).thenReturn(expected);

        mockMvc.perform(get("/api/trips/getByAccount", accountId)
                .param("accountId", accountId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(quantity)));
    }

    @Test
    @WithMockUser
    public void getTripsByAccountWhenEmptyTest() throws Exception {
        Integer accountId = 1;
        int quantity = 0;

        List<Trip> expected = Collections.emptyList();
        when(tripService.findByAccountId(accountId)).thenReturn(expected);

        mockMvc.perform(get("/api/trips/getByAccount", accountId)
                .param("accountId", accountId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(quantity)));
    }

    @Test
    @WithMockUser
    public void createTripTest() throws Exception {
        EasyRandom easyRandom = new EasyRandom();
        Trip toCreate = easyRandom.nextObject(Trip.class);
        toCreate.setId(null);
        Trip created = easyRandom.nextObject(Trip.class);

        when(tripService.create(any())).thenReturn(created);

        mockMvc.perform(post("/api/trips")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(toCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    @WithMockUser
    public void createTripWithExistingId() throws Exception {
        Trip trip = new EasyRandom().nextObject(Trip.class);

        mockMvc.perform(post("/api/trips")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(trip)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @WithMockUser
    public void setAccountWhenCreateTest() throws Exception {
        EasyRandom easyRandom = new EasyRandom();
        Trip toCreate = easyRandom.nextObject(Trip.class);
        toCreate.setId(null);
        toCreate.setCreator(null);
        Trip created = easyRandom.nextObject(Trip.class);

        Account currentAccount = easyRandom.nextObject(Account.class);

        when(tripService.create(any())).thenReturn(created);
        when(securityService.getCurrentAccount()).thenReturn(currentAccount);

        mockMvc.perform(post("/api/trips")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(toCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.creator").isNotEmpty());

        verify(securityService).getCurrentAccount();
        verify(tripService).create(argThat(t -> t.getCreator().getUid().equals(currentAccount.getUid())));
    }
}