package com.simplon.easyportfolio.api.services.impl;

import com.simplon.easyportfolio.api.domain.User;
import com.simplon.easyportfolio.api.exceptions.AccountExistsException;
import com.simplon.easyportfolio.api.repositories.security.OwnerRepository;
import com.simplon.easyportfolio.api.services.jwt.JwtUserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtUserServiceImpl implements JwtUserService {
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private PasswordEncoder passwordEncoder;
    private final String signingKey;

    public JwtUserServiceImpl(@Value("${jwt.signing.key}") String signingKey ){
     this.signingKey = signingKey;
   }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = ownerRepository.findByEmail(username);

        if(user == null) {
            throw new UsernameNotFoundException("The owner could not be found. HM");
        }
        return user;
    }

    // USED FOR REGISTRATION
    @Override
    public Authentication authenticate(String username, String password) throws Exception {
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        return  authenticationConfiguration.getAuthenticationManager().authenticate(authentication);
    }

    @Override
    public UserDetails save(String username, String password) throws AccountExistsException {
        UserDetails existingUser = ownerRepository.findByEmail(username);
        if (existingUser != null) {
            throw new AccountExistsException();
        }
        User user = new User();
        user.setEmail(username);
        user.setPassword(passwordEncoder.encode(password));
        ownerRepository.save(user);
        return user;
    }


    //USED FOR AUTHENTIFICATION
    @Override
    public UserDetails getUserFromJwt(String jwt) {
        String username = getUsernameFromToken(jwt);
        return loadUserByUsername(username);
    }


    private String getUsernameFromToken(String token) {
        Claims claims =
                Jwts.parser()
                    .setSigningKey(signingKey)
                    .parseClaimsJws(token)
                    .getBody();
        return claims.getSubject();
    }


    @Override
    public String generateJwtForUser(UserDetails user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 3600 * 1000);
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, signingKey)
                .compact();

    }
}