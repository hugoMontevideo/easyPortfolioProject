package com.simplon.easyportfolio.api.controllers.skills;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkillAddDTO {
    private String title;
    private Long categorySkillId;
    private Long portfolioId;
}
