package com.simplon.easyportfolio.api.services.projects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectServiceResponseModel {
    private Long id;
    private String title;
    private String description;
    private Long date;
    private Long portfolioId;
}
