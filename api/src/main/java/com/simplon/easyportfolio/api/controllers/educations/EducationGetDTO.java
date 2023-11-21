package com.simplon.easyportfolio.api.controllers.educations;

import com.simplon.easyportfolio.api.controllers.portfolios.PortfolioGetDTO;
import com.simplon.easyportfolio.api.repositories.portfolios.PortfolioRepositoryModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EducationGetDTO {

    private Long id;
    private String training;
    private String school;
    private String degree;
    private Long trainingYear;
    private String description;
    //@OneToMany(mappedBy = "education", orphanRemoval = true)
    //private List<DocumentEducationRepositoryModel> skills = new ArrayList<>();
    private Long portfolioId;
}
