package com.simplon.easyportfolio.api.controllers.educations;

import com.simplon.easyportfolio.api.mappers.EasyfolioMapper;
import com.simplon.easyportfolio.api.services.educations.EducationServiceRequestModel;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/educations")
public class EducationController {
    @Autowired
    PortfolioService portfolioService;
    private final EasyfolioMapper mapper = EasyfolioMapper.INSTANCE;

    // add Education
    @PostMapping
    public boolean add(@RequestBody EducationDTO educationDTO){
        EducationServiceRequestModel educationServiceRequestModel =
                mapper.educationDtoToServiceRequestModel(educationDTO);

             return portfolioService.addEducation( educationServiceRequestModel );
    }


}
