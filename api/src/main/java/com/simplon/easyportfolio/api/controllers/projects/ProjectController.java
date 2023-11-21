package com.simplon.easyportfolio.api.controllers.projects;

import com.simplon.easyportfolio.api.mappers.EasyfolioMapper;
import com.simplon.easyportfolio.api.services.skills.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("api/projects")
public class ProjectController {
    @Autowired
    SkillService skillService;

    private final EasyfolioMapper mapper = EasyfolioMapper.INSTANCE;
}
