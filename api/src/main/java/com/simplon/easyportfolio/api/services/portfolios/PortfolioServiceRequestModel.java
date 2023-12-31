package com.simplon.easyportfolio.api.services.portfolios;

import com.simplon.easyportfolio.api.domain.User;
import com.simplon.easyportfolio.api.services.educations.EducationServiceResponseModel;
import com.simplon.easyportfolio.api.services.experiences.ExperienceServiceResponseModel;
import com.simplon.easyportfolio.api.services.projects.ProjectServiceResponseModel;
import com.simplon.easyportfolio.api.services.skills.SkillServiceResponseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioServiceRequestModel {
    public Optional<Long> id;
    private String title;
    private String description;
    private String name;
    private String firstname;
    private String email;
    private String city;
    private String profileImgPath;
    private String aboutMe;
    private Optional<Integer> userId;
    private Optional<User> user;

    public PortfolioServiceRequestModel(Optional<Long> id) {
        this.id = id ;
    }
    public PortfolioServiceRequestModel( String title, Integer userId) {
        this.title = title;
        this.userId = Optional.ofNullable(userId);
    }
    public PortfolioServiceRequestModel(Optional<Long> id,  String title, String name, String firstname, String email) {
        this.title = title;
        this.name = name;
        this.firstname = firstname;
        this.email = email;
    }

    public PortfolioServiceRequestModel(Long id, String title, String description, String name, String firstname, String email, String city, String profileImgPath, String aboutMe) {
        this.id = Optional.ofNullable(id);
        this.title = title;
        this.description = description;
        this.name = name;
        this.firstname = firstname;
        this.email = email;
        this.city = city;
        this.profileImgPath = profileImgPath;
        this.aboutMe = aboutMe;
    }


}
