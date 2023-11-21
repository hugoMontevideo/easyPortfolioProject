package com.simplon.easyportfolio.api.controllers.projects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectGetDTO {
    private Long id;
    private String title;
    private String description;
    private Long date;
    private Long portfolioId;
}
