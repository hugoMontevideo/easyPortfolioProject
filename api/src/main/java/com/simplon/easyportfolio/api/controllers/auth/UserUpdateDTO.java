package com.simplon.easyportfolio.api.controllers.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {
    private int id;
    private String email; // login
    private  String password;
    private String name;
    private String firstname;
    private LocalDate inscriptionDate;
    private LocalDate connectionDate;
    private String profileImgPath;

}
