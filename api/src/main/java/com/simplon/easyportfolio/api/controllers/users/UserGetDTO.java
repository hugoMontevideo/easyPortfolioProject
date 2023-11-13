package com.simplon.easyportfolio.api.controllers.users;

import lombok.NoArgsConstructor;


public record UserGetDTO (Long id, String name, String firstname, String email, String role) {
}
