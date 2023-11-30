package com.simplon.easyportfolio.api.controllers.skills;

import com.simplon.easyportfolio.api.exceptions.PortfolioNotFoundException;
import com.simplon.easyportfolio.api.mappers.EasyfolioMapper;
import com.simplon.easyportfolio.api.repositories.skills.SkillRepositoryModel;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioService;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceModel;
import com.simplon.easyportfolio.api.services.skills.SkillServiceModel;
import com.simplon.easyportfolio.api.services.skills.SkillServiceRequestModel;
import com.simplon.easyportfolio.api.services.skills.SkillServiceResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/skills")
public class SkillController {
    @Autowired
    PortfolioService portfolioService;
    private final EasyfolioMapper mapper = EasyfolioMapper.INSTANCE;

    @GetMapping("/{id}")  //  GET BY ID   *****
    public ResponseEntity<SkillGetDTO> findById(@PathVariable Long id){
        // TODO *** finish the method, not working yet ***
        try{
           SkillServiceResponseModel responseModel = portfolioService.findSkillById(id);

           SkillGetDTO DTO = mapper.skillSvcToGetDTO(responseModel);
           return new ResponseEntity<>( DTO, HttpStatus.OK);
        }catch (PortfolioNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getReason());
        }
    }

    // addSkill
    @PostMapping
    public SkillGetDTO add(@RequestBody SkillDTO DTO){

        SkillServiceRequestModel skillServiceRequestModel = mapper.skillDtoToServiceRequestModel(DTO);

        SkillServiceResponseModel addedSkill = portfolioService.addSkill( skillServiceRequestModel );
        return mapper.skillSvcToGetDTO(addedSkill);

    }

    // delete Skill
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){

        try {
            portfolioService.deleteSkill(id);
            return ResponseEntity.noContent().build(); // Statut 204 No Content
        } catch(DataAccessException e) {
            return ResponseEntity.status(502).build();
        } catch(Exception e){
            return ResponseEntity.notFound().build(); // Statut 404 No Content
        }


    }




}


