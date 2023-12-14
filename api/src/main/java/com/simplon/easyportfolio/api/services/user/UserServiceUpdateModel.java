package com.simplon.easyportfolio.api.services.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserServiceUpdateModel {
    private int id;
    private String email;
    private  String password;
    private String name;
    private String firstname;
    private LocalDate inscriptionDate;
    private LocalDate connectionDate;
    private String profileImgPath;



}
