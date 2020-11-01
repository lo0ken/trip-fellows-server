package com.tripfellows.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidCredentialsException extends AbstractException {

    private static final String message = "Invalid username/password supplied";

    public InvalidCredentialsException() {
        super(message, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
