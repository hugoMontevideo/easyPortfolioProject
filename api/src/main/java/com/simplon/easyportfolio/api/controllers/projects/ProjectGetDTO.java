package com.simplon.easyportfolio.api.controllers.projects;

import com.simplon.easyportfolio.api.repositories.projects.DocumentProjectRepositoryModel;
import com.simplon.easyportfolio.api.services.projects.DocumentProjectServiceModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectGetDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDate date;
    private List<DocumentProjectGetDTO> documents ;
}
