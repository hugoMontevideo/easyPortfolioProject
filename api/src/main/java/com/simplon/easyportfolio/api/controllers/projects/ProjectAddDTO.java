package com.simplon.easyportfolio.api.controllers.projects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectAddDTO {
    private String title;
    private Long portfolioId;
}
