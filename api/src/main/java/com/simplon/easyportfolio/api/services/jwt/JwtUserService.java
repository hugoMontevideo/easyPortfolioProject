package com.simplon.easyportfolio.api.services.jwt;

import com.simplon.easyportfolio.api.services.user.UserService;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtUserService extends UserService {

    String generateJwtForUser(UserDetails user);

    UserDetails getUserFromJwt(String jwt);
}
