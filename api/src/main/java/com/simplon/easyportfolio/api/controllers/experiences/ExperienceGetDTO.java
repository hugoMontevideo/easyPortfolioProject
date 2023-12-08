package com.simplon.easyportfolio.api.controllers.experiences;

import com.simplon.easyportfolio.api.controllers.portfolios.PortfolioGetDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceGetDTO {
    private Long id;
    private String title;
    private String company;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    //@OneToMany(mappedBy = "experience", orphanRemoval = true)
    //private List<DocumentExperienceRepositoryModel> skills = new ArrayList<>();
}
