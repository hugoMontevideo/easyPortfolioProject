package com.simplon.easyportfolio.api.controllers.experiences;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceDTO {
    private String title;

    private String company;

    private String description;

    private Long startDate;

    private Long endDate;

    private Long portfolioId;
}
