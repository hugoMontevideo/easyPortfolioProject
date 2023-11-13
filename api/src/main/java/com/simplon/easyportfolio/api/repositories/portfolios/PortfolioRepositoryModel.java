package com.simplon.easyportfolio.api.repositories.portfolios;

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

    @Column(name = "name")
    private String name;

    @Column(name="firstname")
    private String firstname;

    @Column(name="email")
    private String email;

    @OneToMany(mappedBy = "portfolio", orphanRemoval = true)
    private List<SkillRepositoryModel> skills = new ArrayList<>();



    public PortfolioRepositoryModel(String title, String name, String firstname, String email){
        this.title = title;
        this.name = name;
        this.firstname = firstname;
        this.email = email;
    }


}
