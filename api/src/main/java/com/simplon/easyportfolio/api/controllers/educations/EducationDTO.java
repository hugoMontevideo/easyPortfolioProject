package com.simplon.easyportfolio.api.controllers.educations;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EducationDTO {
    @Pattern(regexp = ".{2,60}", message = "Le titre doit avoir entre 2 et 60 caractères")
    private String training;
    private String school;
    private String degree;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private Long portfolioId;
}
