package com.simplon.easyportfolio.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Account already exists")
public class AccountExistsException extends Exception{
}
