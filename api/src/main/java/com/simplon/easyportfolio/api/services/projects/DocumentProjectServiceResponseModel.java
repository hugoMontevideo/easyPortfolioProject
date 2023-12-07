package com.simplon.easyportfolio.api.services.projects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentProjectServiceResponseModel {
    private Long id;
    private String title;
    private String mime;
    private String filename;

}
