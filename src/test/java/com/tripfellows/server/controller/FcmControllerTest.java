package com.tripfellows.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripfellows.server.model.Account;
import com.tripfellows.server.model.request.UpdateFcmTokenRequest;
import com.tripfellows.server.security.SecurityService;
import com.tripfellows.server.service.api.FcmTokenService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FcmController.class)
public class FcmControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SecurityService securityService;

    @MockBean
    FcmTokenService fcmTokenService;

    EasyRandom easyRandom = new EasyRandom();

    ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {
        when(securityService.getCurrentAccount())
                .thenReturn(easyRandom.nextObject(Account.class));
    }

    @Test
    @WithMockUser
    public void updateTest() throws Exception {
        UpdateFcmTokenRequest request = easyRandom.nextObject(UpdateFcmTokenRequest.class);

        mockMvc.perform(post("/api/fcm/updateFcmToken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk());

        verify(fcmTokenService).update(any(), eq(request.getFcmToken()));
    }

    @Test
    @WithMockUser
    public void deleteTest() throws Exception {
        mockMvc.perform(delete("/api/fcm/deleteFcmToken")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(fcmTokenService).delete(any());
    }
}