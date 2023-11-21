package com.simplon.easyportfolio.api.repositories.educations;

import com.simplon.easyportfolio.api.repositories.portfolios.PortfolioRepositoryModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="education")
public class EducationRepositoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "training")
    private String training;

    @Column(name = "school")
    private String school;

    @Column(name = "degree")
    private String degree;

    @Column(name = "training_year")
    private Long trainingYear;

    @Column(name = "description")
    private String description;

    //@OneToMany(mappedBy = "education", orphanRemoval = true)
    //private List<DocumentEducationRepositoryModel> skills = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private PortfolioRepositoryModel portfolio;


}
