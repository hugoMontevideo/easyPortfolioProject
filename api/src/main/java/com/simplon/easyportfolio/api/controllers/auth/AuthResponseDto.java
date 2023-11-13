package com.simplon.easyportfolio.api.controllers.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDto {

    private UserDetails user;
    private String token;

}
