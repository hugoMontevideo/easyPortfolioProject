package com.simplon.easyportfolio.api.services.projects;

import com.simplon.easyportfolio.api.repositories.projects.ProjectRepositoryModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentProjectServiceModel {
    private Long id;

    private String title;

    private String mime;

    private String filename;

    //private ProjectRepositoryModel project;
}
