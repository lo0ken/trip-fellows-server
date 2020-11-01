package com.tripfellows.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripfellows.server.model.request.SignUpRequest;
import com.tripfellows.server.security.AuthProvider;
import com.tripfellows.server.service.api.UserService;
import org.jeasy.random.EasyRandom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RegistrationController.class)
public class RegistrationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService accountService;

    @MockBean
    AuthProvider authProvider;

    @Test
    public void signUpTest() throws Exception {
        SignUpRequest request = new EasyRandom().nextObject(SignUpRequest.class);

        mockMvc.perform(post("/signUp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(request)))
                .andExpect(status().isOk());
    }
}