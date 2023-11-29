package com.simplon.easyportfolio.api.controllers.projects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {
    private String title;
    private String description;
    private LocalDate date;
    private String fileName;
    private Optional<MultipartFile> file;
    private Long portfolioId;

}
