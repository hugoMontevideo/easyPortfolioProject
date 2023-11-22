package com.simplon.easyportfolio.api.controllers.auth;

import lombok.Data;

@Data
public class AuthRequestDto {
    private String email;
    private String password;
}
