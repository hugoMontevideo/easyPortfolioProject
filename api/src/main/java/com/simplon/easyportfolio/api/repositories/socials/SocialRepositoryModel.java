package com.simplon.easyportfolio.api.repositories.socials;

import com.simplon.easyportfolio.api.repositories.portfolios.PortfolioRepositoryModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="social")
public class SocialRepositoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "link", columnDefinition = "LONGTEXT")
    private String link;

    @Column(name = "category_social_id")
    private Long categorySocialId;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private PortfolioRepositoryModel portfolio;

}
