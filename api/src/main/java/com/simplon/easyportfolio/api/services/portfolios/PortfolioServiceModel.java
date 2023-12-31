package com.simplon.easyportfolio.api.services.portfolios;

import com.simplon.easyportfolio.api.domain.User;
import com.simplon.easyportfolio.api.repositories.educations.EducationRepositoryModel;
import com.simplon.easyportfolio.api.repositories.experiences.ExperienceRepositoryModel;
import com.simplon.easyportfolio.api.repositories.projects.ProjectRepositoryModel;
import com.simplon.easyportfolio.api.repositories.skills.SkillRepositoryModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PortfolioServiceModel {

    private Long id;
    private String title;
    private String description;
    private String name;
    private String firstname;
    private String email;
    private String city;
    private String profileImgPath;
    private String aboutMe;
    private List<ProjectRepositoryModel> projects ;
    private List<ExperienceRepositoryModel> experiences ;
    private List<EducationRepositoryModel> educations ;
    private List<SkillRepositoryModel> skills;
    //@OneToMany(mappedBy = "portfolio", orphanRemoval = true)
    //private List<SocialLinkRepositoryModel> socialLinks ;
    private User user;

    public PortfolioServiceModel(Long id) {
        this.id = id ;
    }
}
