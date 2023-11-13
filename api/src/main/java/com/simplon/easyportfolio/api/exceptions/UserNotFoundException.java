package com.simplon.easyportfolio.api.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(code= HttpStatus.NOT_FOUND, reason = "User not Found")
@Getter
public class UserNotFoundException extends ResponseStatusException {

    public UserNotFoundException(HttpStatus status){super(status); }

    public UserNotFoundException(HttpStatus status, String reason){super(status, reason); }

    public UserNotFoundException(HttpStatus status, String reason, Throwable cause){super(status, reason, cause); }
}
