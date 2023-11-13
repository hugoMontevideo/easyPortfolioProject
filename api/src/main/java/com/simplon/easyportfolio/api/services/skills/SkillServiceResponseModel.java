package com.simplon.easyportfolio.api.services.skills;

import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceResponseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SkillServiceResponseModel {
    private Long id;
    private String title;
    private String description;
    private Long portfolioId;
}
