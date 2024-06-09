package com.simplon.easyportfolio.api.services.projects;

import com.simplon.easyportfolio.api.mappers.EasyfolioMapper;
import com.simplon.easyportfolio.api.repositories.projects.ProjectRepository;
import com.simplon.easyportfolio.api.repositories.skills.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    @Autowired
    ProjectRepository projectRepository;
    private final EasyfolioMapper mapper = EasyfolioMapper.INSTANCE;



}
