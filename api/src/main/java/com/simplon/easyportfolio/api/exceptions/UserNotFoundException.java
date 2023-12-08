package com.simplon.easyportfolio.api.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;



public class UserNotFoundException extends Exception {

    public UserNotFoundException(String message){
        super(message);
    }

}
