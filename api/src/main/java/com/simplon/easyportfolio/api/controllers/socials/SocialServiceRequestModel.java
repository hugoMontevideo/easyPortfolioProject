package com.simplon.easyportfolio.api.controllers.socials;

import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialServiceRequestModel {
    private String link;
    private Long categorySocialId;
    private Optional<PortfolioServiceModel> portfolio;
    private Optional<Long> portfolioId;

    public SocialServiceRequestModel(String link, Long categorySocialId, Optional<Long> portfolioId) {
        this.link = link;
        this.categorySocialId = categorySocialId;
        this.portfolioId = portfolioId;
    }


}
