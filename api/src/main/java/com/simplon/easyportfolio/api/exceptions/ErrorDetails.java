package com.simplon.easyportfolio.api.exceptions;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDetails {
    private int statusCode;
    private String message;
    private String details;

}
