package com.tripfellows.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR,
        reason = "You already have joined to another trip!")
public class PassengerOfAnotherTripException extends RuntimeException {
}
