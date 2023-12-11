package com.simplon.easyportfolio.api.controllers.skills;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkillUpdateDTO {
    private Optional<Long> id;
    @Pattern(regexp = ".{2,60}", message = "Le titre doit avoir entre 2 et 60 caractères")
    private String title;
    private String description;
    private Long portfolioId;
}
