package com.simplon.easyportfolio.api.services.educations;

import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Optional;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EducationServiceRequestUpdateModel {
    private Optional<Long> id;
    private String training;
    private String school;
    private String degree;
    private Optional<LocalDate> startDate;
    private Optional<LocalDate> endDate;
    private String description;
    private Optional<PortfolioServiceModel> portfolio;
    private Optional<Long> portfolioId;
}
