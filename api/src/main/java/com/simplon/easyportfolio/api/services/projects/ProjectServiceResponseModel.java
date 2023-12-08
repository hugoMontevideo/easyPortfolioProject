package com.simplon.easyportfolio.api.services.projects;

import com.simplon.easyportfolio.api.repositories.projects.DocumentProjectRepositoryModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectServiceResponseModel {
    private Long id;
    private String title;
    private String description;
    private LocalDate date;
    private List<DocumentProjectServiceModel> documents ;
}
