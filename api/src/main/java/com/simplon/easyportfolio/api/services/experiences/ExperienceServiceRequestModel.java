package com.simplon.easyportfolio.api.services.experiences;

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
public class ExperienceServiceRequestModel {
    private String title;
    private String company;
    private String description;
    private Optional<LocalDate> startDate;
    private Optional<LocalDate> endDate;
    //@OneToMany(mappedBy = "experience", orphanRemoval = true)
    //private List<DocumentExperienceRepositoryModel> skills = new ArrayList<>();
    private Optional<PortfolioServiceModel> portfolio;
    private Optional<Long> portfolioId;

    public ExperienceServiceRequestModel(String title, Long portfolioId) {
        this.title = title;
        this.portfolioId= Optional.ofNullable(portfolioId);
    }

}
