package com.simplon.easyportfolio.api.services.experiences;

import com.simplon.easyportfolio.api.repositories.portfolios.PortfolioRepositoryModel;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceResponseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceServiceResponseModel {

    private Long id;

    private String title;

    private String company;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;
    //@OneToMany(mappedBy = "experience", orphanRemoval = true)
    //private List<DocumentExperienceRepositoryModel> skills = new ArrayList<>();

}
