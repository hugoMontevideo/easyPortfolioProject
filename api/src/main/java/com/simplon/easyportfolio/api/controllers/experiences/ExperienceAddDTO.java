package com.simplon.easyportfolio.api.controllers.experiences;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceAddDTO {
    private String title;
    private Long portfolioId;
}
