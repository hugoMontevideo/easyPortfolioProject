package com.simplon.easyportfolio.api.security;

import com.simplon.easyportfolio.api.services.jwt.JwtUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUserService jwtUserService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try{
            String header = request.getHeader("Authorization");
            // Authentication
            String incomingJwt = header.substring(7);
            UserDetails userDetails = jwtUserService.getUserFromJwt(incomingJwt);
            // on le passe aux controllers grâce au context
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch (Exception e) {
            logger.info("Trying parse token but failed");
        }
        filterChain.doFilter(request, response);
    }
}
