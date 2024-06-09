package com.simplon.easyportfolio.api.controllers.educations;

import com.simplon.easyportfolio.api.controllers.portfolios.PortfolioGetDTO;
import com.simplon.easyportfolio.api.repositories.portfolios.PortfolioRepositoryModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EducationGetDTO {
    private Long id;
    private String training;
    private String school;
    private String degree;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    //@OneToMany(mappedBy = "education", orphanRemoval = true)
    //private List<DocumentEducationRepositoryModel> skills = new ArrayList<>();
}
