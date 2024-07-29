package com.simplon.easyportfolio.api.controllers.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdatePasswordDTO {

    private String code;
    private String email;
    private String password;
}
