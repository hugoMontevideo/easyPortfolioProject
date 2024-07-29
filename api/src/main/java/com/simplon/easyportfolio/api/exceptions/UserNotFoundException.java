package com.simplon.easyportfolio.api.exceptions;



public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message){
        super(message);
    }

}
