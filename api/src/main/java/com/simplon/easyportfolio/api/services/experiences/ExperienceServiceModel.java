package com.simplon.easyportfolio.api.services.experiences;

import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceServiceModel {
    private Long id;

    private String title;

    private String company;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;
    //@OneToMany(mappedBy = "experience", orphanRemoval = true)
    //private List<DocumentExperienceRepositoryModel> skills = new ArrayList<>();
    private PortfolioServiceModel portfolio;
}
