package com.simplon.easyportfolio.api.services.educations;

import com.simplon.easyportfolio.api.mappers.EasyfolioMapper;
import com.simplon.easyportfolio.api.repositories.educations.EducationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EducationService {
    @Autowired
    EducationRepository educationRepository;
    private final EasyfolioMapper mapper = EasyfolioMapper.INSTANCE;
}
