package com.simplon.easyportfolio.api.services.portfolios;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioServiceRequestModel {
    public Optional<Long> id;
    private String title;
    private String name;
    private String firstname;
    private String email;

    public PortfolioServiceRequestModel(String title, String name, String firstname, String email) {
        this.title = title;
        this.name = name;
        this.firstname = firstname;
        this.email = email;
    }
}
