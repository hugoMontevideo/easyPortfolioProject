package com.simplon.easyportfolio.api.services.skills;

import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkillServiceModel {
    private Long id;
    private String title;
    private String description;
    private PortfolioServiceModel portfolio;


}
