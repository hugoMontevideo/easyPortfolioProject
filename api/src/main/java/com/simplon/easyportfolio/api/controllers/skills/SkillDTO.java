package com.simplon.easyportfolio.api.controllers.skills;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkillDTO {
    private String title;
    private String description;
    private Long idPortfolio;
}
