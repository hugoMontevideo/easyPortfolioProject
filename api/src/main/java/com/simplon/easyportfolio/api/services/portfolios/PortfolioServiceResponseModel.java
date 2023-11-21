package com.simplon.easyportfolio.api.services.portfolios;

import com.simplon.easyportfolio.api.services.educations.EducationServiceResponseModel;
import com.simplon.easyportfolio.api.services.experiences.ExperienceServiceResponseModel;
import com.simplon.easyportfolio.api.services.projects.ProjectServiceResponseModel;
import com.simplon.easyportfolio.api.services.skills.SkillServiceResponseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioServiceResponseModel {
    public Long id;
    private String title;
    private String name;
    private String firstname;
    private String email;
    private List<ProjectServiceResponseModel> projects;
    private List<ExperienceServiceResponseModel> experiences;
    private List<EducationServiceResponseModel> educations;
    private List<SkillServiceResponseModel> skills;
}
