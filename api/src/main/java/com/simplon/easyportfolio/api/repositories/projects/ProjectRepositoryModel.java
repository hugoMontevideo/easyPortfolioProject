package com.simplon.easyportfolio.api.repositories.projects;

import com.simplon.easyportfolio.api.repositories.portfolios.PortfolioRepositoryModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="project")
public class ProjectRepositoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "languages")
    private String languages;

    @Column(name = "date")
    private LocalDate date;

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
    private List<DocumentProjectRepositoryModel> documents ;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private PortfolioRepositoryModel portfolio;

}
