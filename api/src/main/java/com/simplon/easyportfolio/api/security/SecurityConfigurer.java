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
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfigurer {
    @Bean
    SecurityFilter securityFilter() {
        return new SecurityFilter();
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }
    @Bean
    SecurityFilterChain configure(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
    // REST API Standard
        http
            .cors()
            .and()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(securityFilter(), UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests((authorize) -> authorize
                            .requestMatchers(mvc.pattern("/auth/users/**")).authenticated()
                            .requestMatchers(mvc.pattern("/api/**")).authenticated()
                            //SECRET.requestMatchers(antMatcher("/public/**")).permitAll()
                            .anyRequest().permitAll()

        );
        return http.build();
    }
}

            //r.requestMatchers("/auth/**").permitAll();
            //r.requestMatchers("/api/portfolios/online/**").permitAll();
