package com.tripfellows.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Authentication failed")
public class AuthenticationFailedException extends RuntimeException {
}
