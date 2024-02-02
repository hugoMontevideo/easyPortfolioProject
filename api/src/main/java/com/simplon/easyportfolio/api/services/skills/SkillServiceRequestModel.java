package com.simplon.easyportfolio.api.services.skills;

import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SkillServiceRequestModel {
    private String title;
    private String description;
    private Optional<PortfolioServiceModel> portfolio;
    private Optional<Long> portfolioId;

    public SkillServiceRequestModel(String title, Long portfolioId) {
        this.title = title;
        this.portfolioId= Optional.ofNullable(portfolioId);
    }

}
