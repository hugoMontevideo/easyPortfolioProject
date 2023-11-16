package com.simplon.easyportfolio.api.services.user;

import com.simplon.easyportfolio.api.exceptions.AccountExistsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    Authentication authenticate(String email, String password) throws Exception;

    UserDetails save(String email, String password) throws AccountExistsException;

}
