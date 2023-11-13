package com.simplon.easyportfolio.api.controllers.users;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String name;
    private String firstname;
    private String email;
    private String role;

    public UserDTO(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
