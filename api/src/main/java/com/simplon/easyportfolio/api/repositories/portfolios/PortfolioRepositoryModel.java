package com.simplon.easyportfolio.api.repositories.portfolios;

import com.simplon.easyportfolio.api.domain.User;
import com.simplon.easyportfolio.api.repositories.educations.EducationRepositoryModel;
import com.simplon.easyportfolio.api.repositories.experiences.ExperienceRepositoryModel;
import com.simplon.easyportfolio.api.repositories.projects.ProjectRepositoryModel;
import com.simplon.easyportfolio.api.repositories.skills.SkillRepositoryModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="portfolio")
public class PortfolioRepositoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "name")
    private String name;
    @Column(name="firstname")
    private String firstname;
    @Column(name="email")
    private String email;
    @Column(name="city")
    private String city;
    @Column(name="profile_img_path")
    private String profileImgPath;
    @OneToMany(mappedBy = "portfolio", orphanRemoval = true)
    private List<ProjectRepositoryModel> projects ;
    @OneToMany(mappedBy = "portfolio", orphanRemoval = true)
    private List<ExperienceRepositoryModel> experiences ;
    @OneToMany(mappedBy = "portfolio", orphanRemoval = true)
    private List<EducationRepositoryModel> educations ;
    @OneToMany(mappedBy = "portfolio", orphanRemoval = true)
    private List<SkillRepositoryModel> skills;
    //@OneToMany(mappedBy = "portfolio", orphanRemoval = true)
    //private List<SocialLinkRepositoryModel> socialLinks ;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public PortfolioRepositoryModel(String title, String name, String firstname, String email){
        this.title = title;
        this.name = name;
        this.firstname = firstname;
        this.email = email;
    }


}
