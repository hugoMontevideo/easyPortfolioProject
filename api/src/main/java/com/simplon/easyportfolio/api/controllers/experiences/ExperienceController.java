package com.simplon.easyportfolio.api.controllers.experiences;

import com.simplon.easyportfolio.api.exceptions.PortfolioNotFoundException;
import com.simplon.easyportfolio.api.mappers.EasyfolioMapper;
import com.simplon.easyportfolio.api.services.experiences.ExperienceServiceModel;
import com.simplon.easyportfolio.api.services.experiences.ExperienceServiceResponseModel;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioService;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin
@RestController
@RequestMapping("api/experiences")
public class ExperienceController {
    @Autowired
    PortfolioService portfolioService;
    private final EasyfolioMapper mapper = EasyfolioMapper.INSTANCE;

    @GetMapping("/{id}")  //  GET BY ID   *****
    public ResponseEntity<ExperienceGetDTO> findById(@PathVariable Long id){

        try{
            ExperienceServiceResponseModel responseModel = portfolioService.findExperienceById(id);

            ExperienceGetDTO DTO = mapper.experienceSvcToGetDTO(responseModel);
            // todo  ***  creer un byIddto qui a objet portfolio sans l'array de skills
            return new ResponseEntity<>( DTO, HttpStatus.OK);
        }catch (PortfolioNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getReason());
        }
    }

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

    // delete Experience
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try {
            portfolioService.deleteExperience(id);
            return ResponseEntity.noContent().build(); // Statut 204 No Content
        } catch(DataAccessException e) {
            return ResponseEntity.status(502).build(); // Statut
        } catch(Exception e){
            return ResponseEntity.notFound().build(); // Statut 404 Not Found
        }
    }








}
