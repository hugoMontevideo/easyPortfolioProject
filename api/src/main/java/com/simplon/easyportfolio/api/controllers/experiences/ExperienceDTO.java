package com.simplon.easyportfolio.api.controllers.experiences;

import jakarta.persistence.Column;

public class ExperienceDTO {
    private Long id;

    private String title;

    private String company;

    private String description;

    private Long startDate;

    private Long endDate;

    private Long portfolio_id;
}
