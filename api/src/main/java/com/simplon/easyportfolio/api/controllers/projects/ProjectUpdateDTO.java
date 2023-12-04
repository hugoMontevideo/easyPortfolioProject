package com.simplon.easyportfolio.api.controllers.projects;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Optional;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectUpdateDTO {
    private Optional<Long> id;
    @Pattern(regexp = ".{2,60}", message = "Le titre doit avoir entre 2 et 60 caractères")
    private String title;
    private String description;
    private LocalDate date;
    private String fileName;
    private Optional<MultipartFile> file;
    private Long portfolioId;
}
