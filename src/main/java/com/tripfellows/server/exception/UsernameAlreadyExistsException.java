package com.tripfellows.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UsernameAlreadyExistsException extends AbstractException {

    private static final String message = "Username is already in use";

    public UsernameAlreadyExistsException() {
        super(message, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
