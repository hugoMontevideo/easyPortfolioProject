package com.simplon.easyportfolio.api.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(code=HttpStatus.NOT_FOUND, reason = "Portfolio Not Found")
@Getter
public class PortfolioNotFoundException extends ResponseStatusException {

    public PortfolioNotFoundException(HttpStatus status){super(status); }

    public PortfolioNotFoundException(HttpStatus status, String reason){super(status, reason); }

    public PortfolioNotFoundException(HttpStatus status, String reason, Throwable cause){super(status, reason, cause); }
}

