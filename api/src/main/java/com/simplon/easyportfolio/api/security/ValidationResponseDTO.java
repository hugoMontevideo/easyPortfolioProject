package com.simplon.easyportfolio.api.security;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
@Builder
@Data
public class ValidationResponseDTO {
    private String email;
    private Instant expires;
}
