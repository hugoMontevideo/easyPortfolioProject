package com.simplon.easyportfolio.api.controllers.experiences;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceDTO {
    @Pattern(regexp = ".{2,60}", message = "Le titre doit avoir entre 2 et 60 caractères")
    private String title;
    private String company;
    private String description;
    private String city;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long portfolioId;
}
