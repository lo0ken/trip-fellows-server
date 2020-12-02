package com.tripfellows.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "No available places in the trip")
public class NoAvailablePlacesFoundException extends RuntimeException {
}
