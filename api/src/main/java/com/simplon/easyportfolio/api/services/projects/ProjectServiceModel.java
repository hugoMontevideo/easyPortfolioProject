package com.simplon.easyportfolio.api.services.projects;

import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectServiceModel {
    private Long id;
    private String title;
    private String description;
    private LocalDate date;
    private PortfolioServiceModel portfolio;

    public ProjectServiceModel(String title, String description, LocalDate date, PortfolioServiceModel portfolio){
        this.title = title;
        this.description = description;
        this.date = date;
        this.portfolio = portfolio;
    }
}
