package com.simplon.easyportfolio.api.controllers.experiences;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Optional;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceUpdateDTO {
    private Optional<Long> id;
    private String title;
    private String company;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long portfolioId;
}
