package com.simplon.easyportfolio.api.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfigurer {
    @Bean
    public SecurityFilter securityFilter() {
        return new SecurityFilter();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
// REST API Standard
        http = http
                .cors()
                .and()
                .csrf()
                .disable();
        http = http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

// Put the filter on our middleware
        http = http.addFilterBefore(securityFilter(),
                UsernamePasswordAuthenticationFilter.class);
// Détermination des endpoints privées
        http = http.authorizeHttpRequests((r) ->{
            r.requestMatchers("/auth/**").permitAll();
            r.requestMatchers("/auth/users/**").authenticated();
            r.requestMatchers("/api/portfolios/online/**").permitAll();
            r.requestMatchers("/api/**").authenticated();
            r.anyRequest().permitAll() ;

        });
        return http.build();
    }
}
