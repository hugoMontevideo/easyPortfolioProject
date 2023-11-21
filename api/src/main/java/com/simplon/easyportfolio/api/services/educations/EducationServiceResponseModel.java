package com.simplon.easyportfolio.api.services.educations;

import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceResponseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EducationServiceResponseModel {
    private Long id;
    private String training;
    private String school;
    private String degree;
    private Long trainingYear;
    private String description;
    //@OneToMany(mappedBy = "experience", orphanRemoval = true)
    //private List<DocumentExperienceRepositoryModel> skills = new ArrayList<>();
    private Long portfolioId;
}
