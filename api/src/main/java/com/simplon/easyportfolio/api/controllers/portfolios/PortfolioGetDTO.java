package com.simplon.easyportfolio.api.controllers.portfolios;

import com.simplon.easyportfolio.api.controllers.skills.SkillGetDTO;
import com.simplon.easyportfolio.api.services.skills.SkillServiceResponseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioGetDTO {
    public Long id;
    private String title;
    private String name;
    private String firstname;
    private String email;
    private ArrayList<SkillGetDTO> skills = new ArrayList<>();

}