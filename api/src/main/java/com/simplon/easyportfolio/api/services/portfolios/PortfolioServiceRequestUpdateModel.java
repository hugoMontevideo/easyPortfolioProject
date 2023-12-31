package com.simplon.easyportfolio.api.services.portfolios;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioServiceRequestUpdateModel {
    public Optional<Long> id;
    private String title;
    private String name;
    private String firstname;
    private String email;
    private String city;
    private String profileImgPath;
    private String aboutMe;
}
