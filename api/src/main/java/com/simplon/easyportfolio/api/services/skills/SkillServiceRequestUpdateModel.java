package com.simplon.easyportfolio.api.services.skills;

import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkillServiceRequestUpdateModel {
    private Optional<Long> id;
    private String title;
    private String description;
    private Long categorySkillId;
    private Optional<PortfolioServiceModel> portfolio;
    private Optional<Long> portfolioId;

}