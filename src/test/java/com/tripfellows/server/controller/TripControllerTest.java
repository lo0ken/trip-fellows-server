package com.tripfellows.server.controller;

import com.tripfellows.server.model.Trip;
import com.tripfellows.server.service.api.TripService;
import org.jeasy.random.EasyRandom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TripController.class)
public class TripControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TripService tripService;

    EasyRandom easyRandom = new EasyRandom();

    @Test
    public void getTripWhenExistsTest() throws Exception {
        Trip trip = easyRandom.nextObject(Trip.class);

        when(tripService.findById(trip.getId())).thenReturn(Optional.of(trip));

        mockMvc.perform(get("/api/trips/{id}", trip.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(trip.getId()));
    }

    @Test
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
    public void getTripsByAccountWhenNotEmptyTest() throws Exception {
        Integer accountId = 1;
        int quantity = 10;

        List<Trip> expected = new EasyRandom().objects(Trip.class, quantity)
                .collect(toList());

        when(tripService.findTripsByAccount(accountId)).thenReturn(expected);

        mockMvc.perform(get("/api/trips/getByAccount", accountId)
                .param("accountId", accountId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(quantity)));
    }

    @Test
    public void getTripsByAccountWhenEmptyTest() throws Exception {
        Integer accountId = 1;
        int quantity = 0;

        List<Trip> expected = Collections.emptyList();

        when(tripService.findTripsByAccount(accountId)).thenReturn(expected);

        mockMvc.perform(get("/api/trips/getByAccount", accountId)
                .param("accountId", accountId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(quantity)));
    }
}