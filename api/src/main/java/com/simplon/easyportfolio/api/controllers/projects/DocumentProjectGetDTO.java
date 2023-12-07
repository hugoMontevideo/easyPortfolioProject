package com.simplon.easyportfolio.api.controllers.projects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentProjectGetDTO {
    private Long id;
    private String title;
    private String mime;
    private String filename;
}
