package com.simplon.easyportfolio.api.repositories.portfolios;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="category_portfolio")
public class CategoryPortfolioRepositoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;


}


/**
 * portfolios categories id
 *
 * 1  classic
 * 2  dev
 * 3  girly
 * 4  fresh
 *
 * */