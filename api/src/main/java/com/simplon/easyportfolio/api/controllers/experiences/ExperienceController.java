package com.simplon.easyportfolio.api.controllers.experiences;

import com.simplon.easyportfolio.api.controllers.skills.SkillGetDTO;
import com.simplon.easyportfolio.api.controllers.skills.SkillUpdateDTO;
import com.simplon.easyportfolio.api.exceptions.ExperienceNotFoundException;
import com.simplon.easyportfolio.api.exceptions.PortfolioNotFoundException;
import com.simplon.easyportfolio.api.mappers.EasyfolioMapper;
import com.simplon.easyportfolio.api.services.experiences.ExperienceServiceRequestModel;
import com.simplon.easyportfolio.api.services.experiences.ExperienceServiceRequestUpdateModel;
import com.simplon.easyportfolio.api.services.experiences.ExperienceServiceResponseModel;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioService;
import com.simplon.easyportfolio.api.services.skills.SkillServiceRequestUpdateModel;
import com.simplon.easyportfolio.api.services.skills.SkillServiceResponseModel;
import jakarta.validation.Valid;
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
            return new ResponseEntity<>( DTO, HttpStatus.OK);

        }catch (ExperienceNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    // addExperience
    @PostMapping
    public ExperienceGetDTO add(@RequestBody @Valid ExperienceDTO DTO){
        System.out.println(DTO);
        ExperienceServiceRequestModel experienceServiceRequestModel = mapper.experienceDtoToServiceRequestModelAdd(DTO);
        ExperienceServiceResponseModel addedExperience =
                portfolioService.saveExperience( experienceServiceRequestModel );
        return mapper.experienceSvcToGetDTO(addedExperience);
    }

    // update Experience
    @PutMapping("/{id}")
    public ExperienceGetDTO update(@RequestBody ExperienceUpdateDTO DTO){
        ExperienceServiceRequestUpdateModel experienceServiceRequestUpdateModel =
                mapper.experienceDtoToServiceRequestModel(DTO);

        ExperienceServiceResponseModel updatedExperience =
                portfolioService.updateExperience( experienceServiceRequestUpdateModel );
        return mapper.experienceSvcToGetDTO(updatedExperience);
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
