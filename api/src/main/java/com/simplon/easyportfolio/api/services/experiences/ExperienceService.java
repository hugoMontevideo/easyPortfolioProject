package com.simplon.easyportfolio.api.services.experiences;

import com.simplon.easyportfolio.api.mappers.EasyfolioMapper;
import com.simplon.easyportfolio.api.repositories.experiences.ExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExperienceService {
    @Autowired
    ExperienceRepository experienceRepository;
    private final EasyfolioMapper mapper = EasyfolioMapper.INSTANCE;


}
