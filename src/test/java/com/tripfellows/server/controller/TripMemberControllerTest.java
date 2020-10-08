package com.tripfellows.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tripfellows.server.model.TripMember;
import com.tripfellows.server.model.request.AddMemberRequest;
import com.tripfellows.server.service.api.TripAccountService;
import com.tripfellows.server.service.api.TripService;
import org.jeasy.random.EasyRandom;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TripMemberController.class)
public class TripMemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TripService tripService;

    @MockBean
    TripAccountService tripAccountService;

    ObjectMapper objectMapper;

    @Before
    public void configureObjectMapper() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    public void addMemberTest() throws Exception {
        EasyRandom easyRandom = new EasyRandom();
        AddMemberRequest request = easyRandom.nextObject(AddMemberRequest.class);
        TripMember created = easyRandom.nextObject(TripMember.class);

        when(tripAccountService.addTripMember(request.getTripId(), request.getAccountId(), request.getRoleCode()))
                .thenReturn(created);

        mockMvc.perform(post("/api/trip-members/addMember")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isNotEmpty());
    }
}