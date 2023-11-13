package com.simplon.easyportfolio.api.services.users;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserServiceModel {
    public Optional<Long> id;

    private String name;

    private String firstname;

    private String email;

    private String role;

    public UserServiceModel(String name, String email) {
        this.name = name;
        this.email = email;
    }


}
