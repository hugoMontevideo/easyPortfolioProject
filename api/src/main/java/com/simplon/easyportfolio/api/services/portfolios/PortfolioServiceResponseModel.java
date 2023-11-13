package com.simplon.easyportfolio.api.services.portfolios;

import com.simplon.easyportfolio.api.services.skills.SkillServiceResponseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Optional;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioServiceResponseModel {
    public Long id;
    private String title;
    private String name;
    private String firstname;
    private String email;
    private ArrayList<SkillServiceResponseModel> skills = new ArrayList<>();
}
