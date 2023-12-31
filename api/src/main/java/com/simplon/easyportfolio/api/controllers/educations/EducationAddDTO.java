package com.simplon.easyportfolio.api.controllers.educations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EducationAddDTO {
    private String training;
    private Long portfolioId;
}
