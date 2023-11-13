package com.simplon.easyportfolio.api.controllers.skills;

import com.simplon.easyportfolio.api.controllers.portfolios.PortfolioGetDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkillGetDTO {
    private Long id;
    private String title;
    private String description;
    private PortfolioGetDTO portfolio;

}
