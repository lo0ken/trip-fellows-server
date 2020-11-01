package com.tripfellows.server.controller;

import com.tripfellows.server.model.request.SignUpRequest;
import com.tripfellows.server.service.api.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    /**
     * POST /signUp : register user in system with specified information
     *
     * @param request SignUpRequest with information about user to register and a new account
     * @return the ResponseEntity with the status 200 (OK) and empty body
     */
    @PostMapping("/signUp")
    public ResponseEntity<Void> register(@RequestBody SignUpRequest request) {
        log.debug("REST request to register user: {} with username: {}", request.getName(), request.getUsername());

        userService.signUp(request);
        return ResponseEntity.ok().build();
    }
}
