package com.simplon.easyportfolio.api.controllers.projects;

import com.simplon.easyportfolio.api.repositories.projects.ProjectRepositoryModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentProjectDTO {

    private String title;
    private String mime;
    private Optional <String> filename;
    private Optional<MultipartFile> file;
    private Long projectId;

    public DocumentProjectDTO(String title, Optional<String> filename, Optional<MultipartFile> file, Long projectId ){
        this.title = title;
        this.filename = filename;
        this.file = file;
        this.projectId = projectId;
    }


    public DocumentProjectDTO(String title, Optional<String> filename, Long projectId) {
        this.title = title;
        this.filename = filename;
        this.projectId = projectId;
    }
}
