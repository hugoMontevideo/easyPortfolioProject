package com.simplon.easyportfolio.api.repositories.skills;

import com.simplon.easyportfolio.api.repositories.portfolios.PortfolioRepositoryModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="skill")
public class SkillRepositoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private PortfolioRepositoryModel portfolio;

}
