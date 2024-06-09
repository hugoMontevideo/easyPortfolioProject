package com.simplon.easyportfolio.api.services.educations;

import com.simplon.easyportfolio.api.repositories.portfolios.PortfolioRepositoryModel;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EducationServiceModel {
    private Long id;
    private String training;
    private String school;
    private String degree;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    //@OneToMany(mappedBy = "education", orphanRemoval = true)
    //private List<DocumentEducationRepositoryModel> skills = new ArrayList<>();
    private PortfolioServiceModel portfolio;

    public EducationServiceModel(String training, String school, String degree, LocalDate startDate, LocalDate endDate, String description, PortfolioServiceModel portfolio) {
        this.training = training;
        this.school = school;
        this.degree = degree;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.portfolio = portfolio;
    }
}
