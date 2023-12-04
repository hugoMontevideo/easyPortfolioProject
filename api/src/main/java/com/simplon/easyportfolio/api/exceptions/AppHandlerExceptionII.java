package com.simplon.easyportfolio.api.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
@RestControllerAdvice
public class AppHandlerExceptionII {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String,String> handleContraintViolation(ConstraintViolationException ex){
        Map<String,String> errorMap = new HashMap<>();
        ex.getConstraintViolations().forEach( error-> {
            errorMap.put("title", error.getMessageTemplate());
        });
        return errorMap;
    }

}
