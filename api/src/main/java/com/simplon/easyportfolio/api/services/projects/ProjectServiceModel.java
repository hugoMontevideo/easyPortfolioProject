package com.simplon.easyportfolio.api.services.projects;

import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectServiceModel {
    private Long id;
    private String title;
    private String description;
    private String languages;
    private LocalDate date;
    private String fileName;
    private List<DocumentProjectServiceModel> documents ;
    private PortfolioServiceModel portfolio;


    public ProjectServiceModel(String title, String description, LocalDate date, PortfolioServiceModel portfolio){
        this.title = title;
        this.description = description;
        this.date = date;
        this.portfolio = portfolio;
    }
}
