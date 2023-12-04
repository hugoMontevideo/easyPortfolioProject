package com.simplon.easyportfolio.api.services.projects;

import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Optional;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectServiceRequestUpdateModel {
    private Optional<Long> id;
    private String title;
    private String description;
    private LocalDate date;
    private String fileName;
    private MultipartFile file;
    private Optional<PortfolioServiceModel> portfolio;
    private Optional<Long> portfolioId;
}
