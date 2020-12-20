package com.tripfellows.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tripfellows.server.enums.TripStatusCodeEnum;
import com.tripfellows.server.model.Trip;
import com.tripfellows.server.service.api.PushNotificationService;
import com.tripfellows.server.service.api.TripStatusService;
import org.jeasy.random.EasyRandom;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TripStatusController.class)
class TripStatusControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    PushNotificationService pushNotificationService;

    @MockBean
    TripStatusService tripStatusService;

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
    void updateStatusWhenTripExists() throws Exception {
        Trip trip = easyRandom.nextObject(Trip.class);
        trip.setId(1);
        TripStatusCodeEnum tripStatus = easyRandom.nextObject(TripStatusCodeEnum.class);

        when(tripStatusService.updateTripStatus(any(), any())).thenReturn(of(trip));

        mockMvc.perform(put("/api/trip-status")
                .param("tripId", trip.getId().toString())
                .param("status", tripStatus.getValue()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status.code").value(trip.getStatus().getCode().getValue()));

        verify(tripStatusService).updateTripStatus(trip.getId(), tripStatus);
        verify(pushNotificationService).notifyTripStatusChanged(trip);
    }

    @Test
    @WithMockUser
    void updateStatusWhenTripNotExists() throws Exception {
        Integer tripId = 1;
        TripStatusCodeEnum tripStatus = easyRandom.nextObject(TripStatusCodeEnum.class);

        when(tripStatusService.updateTripStatus(any(), any())).thenReturn(empty());

        mockMvc.perform(put("/api/trip-status")
                .param("tripId", tripId.toString())
                .param("status", tripStatus.getValue()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist());

        verify(tripStatusService).updateTripStatus(tripId, tripStatus);
        verify(pushNotificationService, never()).notifyTripStatusChanged(any());
    }
}