package com.tripfellows.server.service.api;

import com.tripfellows.server.model.request.SignUpRequest;

public interface UserService {
    /**
     * Register user in system, creates auth_user with role USER and an account for it
     *
     * @param request SignUpRequest with the information about new user and account
     */
    void signUp(SignUpRequest request);
}
