package com.tripfellows.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tripfellows.server.enums.RoleCodeEnum;
import com.tripfellows.server.exception.NoAvailablePlacesFoundException;
import com.tripfellows.server.exception.PassengerOfAnotherTripException;
import com.tripfellows.server.model.Account;
import com.tripfellows.server.model.TripMember;
import com.tripfellows.server.model.request.AddMemberRequest;
import com.tripfellows.server.security.SecurityService;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
    SecurityService securityService;

    @MockBean
    TripAccountService tripAccountService;

    ObjectMapper objectMapper;

    EasyRandom easyRandom = new EasyRandom();

    @Before
    public void configureObjectMapper() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        when(securityService.getCurrentAccount()).thenReturn(easyRandom.nextObject(Account.class));
    }

    @Test
    @WithMockUser
    public void addMemberTest() throws Exception {
        AddMemberRequest request = easyRandom.nextObject(AddMemberRequest.class);
        TripMember created = easyRandom.nextObject(TripMember.class);

        Account account = easyRandom.nextObject(Account.class);

        when(securityService.getCurrentAccount()).thenReturn(account);

        when(tripAccountService.addTripMember(request.getTripId(), account.getId(), RoleCodeEnum.PASSENGER))
                .thenReturn(created);

        mockMvc.perform(post("/api/trip-members/addMember")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isNotEmpty());

        verify(tripAccountService).addTripMember(request.getTripId(), account.getId(),
                request.getRoleCode());
    }

    @Test
    @WithMockUser
    public void addMemberWhenNoAvailablePlacesFoundTest() throws Exception {
        AddMemberRequest request = easyRandom.nextObject(AddMemberRequest.class);

        when(tripAccountService.addTripMember(any(), any(), any()))
                .thenThrow(new NoAvailablePlacesFoundException());

        mockMvc.perform(post("/api/trip-members/addMember")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser
    public void addMemberWhenAlreadyHaveTripTest() throws Exception {
        AddMemberRequest request = easyRandom.nextObject(AddMemberRequest.class);

        when(tripAccountService.addTripMember(any(), any(), any()))
                .thenThrow(new PassengerOfAnotherTripException());

        mockMvc.perform(post("/api/trip-members/addMember")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser
    public void removeMemberTest() throws Exception {
        Integer idToDelete = easyRandom.nextInt();

        mockMvc.perform(delete("/api/trip-members/removeMember/{id}", idToDelete))
                .andExpect(status().isOk());

        verify(tripAccountService).removeTripMember(idToDelete);
    }
}