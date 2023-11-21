package com.simplon.easyportfolio.api.controllers.experiences;

import com.simplon.easyportfolio.api.mappers.EasyfolioMapper;
import com.simplon.easyportfolio.api.services.experiences.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("api/experiences")
public class ExperienceController {
    @Autowired
    ExperienceService experienceService;
    private final EasyfolioMapper mapper = EasyfolioMapper.INSTANCE;




}
