package com.simplon.easyportfolio.api.security;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Builder
@Data
public class VerifyCodeDTO {
    private boolean code;
    private boolean expires;
}
