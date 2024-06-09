package com.simplon.easyportfolio.api.services.socials;

import com.simplon.easyportfolio.api.repositories.portfolios.PortfolioRepositoryModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SocialServiceResponseModel {
    private Long Id;
    private String link;
    private Long categorySocialId;
}
