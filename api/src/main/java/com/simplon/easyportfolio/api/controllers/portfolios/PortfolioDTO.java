package com.simplon.easyportfolio.api.controllers.portfolios;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioDTO {
    private String title;
    private String name;
    private String firstname;
    private String email;
}
