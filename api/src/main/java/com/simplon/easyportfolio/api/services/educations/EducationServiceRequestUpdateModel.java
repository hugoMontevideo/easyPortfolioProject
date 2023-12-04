package com.simplon.easyportfolio.api.services.educations;

import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceModel;

import java.time.LocalDate;
import java.util.Optional;

public class EducationServiceRequestUpdateModel {
    private Optional<Long> id;
    private String training;
    private String school;
    private String degree;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private Optional<PortfolioServiceModel> portfolio;
    private Optional<Long> portfolioId;
}
