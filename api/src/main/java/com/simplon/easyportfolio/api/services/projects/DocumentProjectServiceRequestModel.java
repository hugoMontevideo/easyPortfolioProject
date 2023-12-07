package com.simplon.easyportfolio.api.services.projects;

import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentProjectServiceRequestModel {
    private String title;
    private String mime;
    private Optional<String> filename;
    private Optional<MultipartFile> file;
    private Optional<ProjectServiceModel> project;
    private Optional<Long> projectId;

    public DocumentProjectServiceRequestModel(String filename, Long projectId) {
        this.filename = Optional.ofNullable(filename);
        this.projectId = Optional.ofNullable(projectId);
    }
}
