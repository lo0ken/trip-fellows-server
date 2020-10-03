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

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TripController.class)
class TripControllerTest {

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
}