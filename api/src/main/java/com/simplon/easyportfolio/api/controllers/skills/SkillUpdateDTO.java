package com.simplon.easyportfolio.api.controllers.skills;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkillUpdateDTO {
    private Optional<Long> id;
    private String title;
    private String description;
    private Long portfolioId;
}
