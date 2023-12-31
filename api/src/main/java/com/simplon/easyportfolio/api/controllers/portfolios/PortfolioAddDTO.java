package com.simplon.easyportfolio.api.controllers.portfolios;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioAddDTO {
    private String title;
    private Integer userId;
}
