package com.simplon.easyportfolio.api.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class CorsConfiguration {
    @Value("${domain}")
    private String domain;
    //@Value("${domaines}")
    //private List<String> domains;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        //System.out.println(domains.toArray(String[]::new));
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(domain) //l'URL du frontend Angular
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*");
            }
        };
    }
}

// "http://localhost:4200", "http://projet.cda.hugo.dece5725.odns.fr"