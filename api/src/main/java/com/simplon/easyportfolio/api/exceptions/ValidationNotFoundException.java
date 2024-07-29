package com.simplon.easyportfolio.api.exceptions;

public class ValidationNotFoundException extends RuntimeException {
    public ValidationNotFoundException(String message){
        super(message);
    }
}
