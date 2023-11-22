package com.simplon.easyportfolio.api.controllers.skills;

import com.simplon.easyportfolio.api.controllers.portfolios.PortfolioDTO;
import com.simplon.easyportfolio.api.exceptions.PortfolioNotFoundException;
import com.simplon.easyportfolio.api.mappers.EasyfolioMapper;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioService;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceModel;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceRequestModel;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceResponseModel;
import com.simplon.easyportfolio.api.services.skills.SkillService;
import com.simplon.easyportfolio.api.services.skills.SkillServiceModel;
import com.simplon.easyportfolio.api.services.skills.SkillServiceResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("api/skills")
public class SkillController {

    @Autowired
    SkillService skillService;
    @Autowired
    PortfolioService portfolioService;

    private final EasyfolioMapper mapper = EasyfolioMapper.INSTANCE;

    @GetMapping("/{id}")  //  GET BY ID   *****
    public ResponseEntity<SkillGetDTO> findById(@PathVariable Long id){

        try{
           SkillServiceResponseModel responseModel = skillService.findById(id);

           SkillGetDTO DTO = mapper.skillSvcToGetDTO(responseModel);
        // todo  ***  creer un byIddto qui a objet portfolio sans l'array de skills
            return new ResponseEntity<>( DTO, HttpStatus.OK);
        }catch (PortfolioNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getReason());
        }
    }
    @PostMapping
    public boolean add(@RequestBody SkillDTO skillDTO){

        if (portfolioService.findById(skillDTO.getPortfolioId()) != null ){
            PortfolioServiceModel portfolio = new PortfolioServiceModel( skillDTO.getPortfolioId() );

            //todo *** try catch du findById
            SkillServiceModel skillServiceModel = mapper.skillDtoToServiceModel(skillDTO);
            //adding portfolio manually
            skillServiceModel.setPortfolio(portfolio);

            skillService.add( skillServiceModel );

            return true;
        }
        return false;
    }
}

