package com.simplon.easyportfolio.api.repositories.experiences;

import com.simplon.easyportfolio.api.repositories.portfolios.PortfolioRepositoryModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="experience")
public class ExperienceRepositoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "company")
    private String company;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private Long startDate;

    @Column(name = "end_date")
    private Long endDate;


    //@OneToMany(mappedBy = "experience", orphanRemoval = true)
    //private List<DocumentExperienceRepositoryModel> skills = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private PortfolioRepositoryModel portfolio;
}
