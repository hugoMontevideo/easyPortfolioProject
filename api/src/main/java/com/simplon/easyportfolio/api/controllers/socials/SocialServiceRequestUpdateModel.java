package com.simplon.easyportfolio.api.controllers.socials;

import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocialServiceRequestUpdateModel {
    private Optional<Long> id;
    private String link;
    private Long categorySocialId;
    private Optional<PortfolioServiceModel> portfolio;
    private Optional<Long> portfolioId;
}
