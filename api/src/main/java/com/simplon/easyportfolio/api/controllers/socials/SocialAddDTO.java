package com.simplon.easyportfolio.api.controllers.socials;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocialAddDTO {

    private String link;
    private Long categorySocialId;
    private Long portfolioId;

}

