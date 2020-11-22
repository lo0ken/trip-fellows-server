package com.tripfellows.server.exception;

import org.springframework.http.HttpStatus;

public class AuthenticationFailedException extends AbstractException {
    private static final String message = "Authentication failed";

    public AuthenticationFailedException() {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
