package com.simplon.easyportfolio.api.services.educations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EducationServiceRequestModel {
    private String training;
    private String school;
    private String degree;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private Long portfolioId;
}
