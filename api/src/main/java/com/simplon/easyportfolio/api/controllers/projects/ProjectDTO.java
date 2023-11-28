package com.simplon.easyportfolio.api.controllers.projects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {
    private String title;
    private String description;
    private LocalDate date;
    private Long portfolioId;

}
