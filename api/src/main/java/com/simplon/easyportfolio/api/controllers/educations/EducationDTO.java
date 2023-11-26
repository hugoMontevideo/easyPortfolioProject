package com.simplon.easyportfolio.api.controllers.educations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EducationDTO {

    private String training;
    private String school;
    private String degree;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private Long portfolioId;
}
