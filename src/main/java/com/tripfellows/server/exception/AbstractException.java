package com.tripfellows.server.exception;

import org.springframework.http.HttpStatus;

public abstract class AbstractException extends RuntimeException{

    private final String message;

    private final HttpStatus httpStatus;

    AbstractException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
