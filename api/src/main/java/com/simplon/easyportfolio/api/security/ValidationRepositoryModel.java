package com.simplon.easyportfolio.api.security;

import com.simplon.easyportfolio.api.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidationRepositoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "email")// security: avoid conceptual duplicates to avoid unexpected behaviors
    private String email;

    @Column(name = "password")
    private String password = null;

    @Column(name = "expires")
    private Instant expires = null;

    @Column(name = "code")
    private String code;

}