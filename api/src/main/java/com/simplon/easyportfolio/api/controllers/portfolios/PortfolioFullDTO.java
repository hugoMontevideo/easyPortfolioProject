package com.simplon.easyportfolio.api.controllers.portfolios;

import com.simplon.easyportfolio.api.controllers.auth.UserResponseDTO;
import com.simplon.easyportfolio.api.controllers.educations.EducationGetDTO;
import com.simplon.easyportfolio.api.controllers.experiences.ExperienceGetDTO;
import com.simplon.easyportfolio.api.controllers.projects.ProjectGetDTO;
import com.simplon.easyportfolio.api.controllers.skills.SkillGetDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioFullDTO {
    public Long id;
    private String title;
    private String description;
    private String name;
    private String firstname;
    private String email;
    private String city;
    private String profileImgPath;
    private String aboutMe;
    private List<ProjectGetDTO> projects;
    private List<ExperienceGetDTO> experiences;
    private List<EducationGetDTO> educations;
    private List<SkillGetDTO> skills;
    private UserResponseDTO user;
}
