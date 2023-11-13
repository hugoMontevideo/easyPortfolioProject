package com.simplon.easyportfolio.api.services.skills;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SkillServiceRequestModel {
    private String title;
    private String description;
    private Long idPortfolio;
}
