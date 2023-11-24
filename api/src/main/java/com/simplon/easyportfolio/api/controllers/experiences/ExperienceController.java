package com.simplon.easyportfolio.api.controllers.experiences;

import com.simplon.easyportfolio.api.controllers.skills.SkillDTO;
import com.simplon.easyportfolio.api.mappers.EasyfolioMapper;
import com.simplon.easyportfolio.api.services.experiences.ExperienceService;
import com.simplon.easyportfolio.api.services.experiences.ExperienceServiceModel;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioService;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceModel;
import com.simplon.easyportfolio.api.services.skills.SkillServiceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/experiences")
public class ExperienceController {
    @Autowired
    PortfolioService portfolioService;
    private final EasyfolioMapper mapper = EasyfolioMapper.INSTANCE;

    // addExperience
    @PostMapping
    public boolean add(@RequestBody ExperienceDTO experienceDTO){

        if (portfolioService.findById(experienceDTO.getPortfolioId()) != null ){
            PortfolioServiceModel portfolio = new PortfolioServiceModel( experienceDTO.getPortfolioId() );

            //todo *** try catch du findById
            ExperienceServiceModel experienceServiceModel = mapper.experienceDtoToServiceModel(experienceDTO);
            //adding portfolio manually
            experienceServiceModel.setPortfolio(portfolio);

            portfolioService.addExperience( experienceServiceModel );

            return true;
        }
        return false;
    }




}
