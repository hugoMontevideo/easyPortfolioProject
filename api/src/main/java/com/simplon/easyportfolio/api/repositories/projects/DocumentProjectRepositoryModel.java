package com.simplon.easyportfolio.api.repositories.projects;

import com.simplon.easyportfolio.api.repositories.portfolios.PortfolioRepositoryModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="document_project")
public class DocumentProjectRepositoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "mime")
    private String mime;

    @Column(name = "file_name")
    private String filename;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private ProjectRepositoryModel project;
}
