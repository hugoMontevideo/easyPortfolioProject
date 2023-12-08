package com.simplon.easyportfolio.api.controllers.educations;

import com.simplon.easyportfolio.api.controllers.skills.SkillGetDTO;
import com.simplon.easyportfolio.api.controllers.skills.SkillUpdateDTO;
import com.simplon.easyportfolio.api.exceptions.EducationNotFoundException;
import com.simplon.easyportfolio.api.exceptions.ExperienceNotFoundException;
import com.simplon.easyportfolio.api.exceptions.PortfolioNotFoundException;
import com.simplon.easyportfolio.api.mappers.EasyfolioMapper;
import com.simplon.easyportfolio.api.services.educations.EducationServiceRequestModel;
import com.simplon.easyportfolio.api.services.educations.EducationServiceRequestUpdateModel;
import com.simplon.easyportfolio.api.services.educations.EducationServiceResponseModel;
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
@RequestMapping("api/educations")
public class EducationController {
    @Autowired
    PortfolioService portfolioService;
    private final EasyfolioMapper mapper = EasyfolioMapper.INSTANCE;

    // add Education
    @PostMapping
    public EducationGetDTO add(@RequestBody @Valid EducationDTO DTO){
        EducationServiceRequestModel educationServiceRequestModel =
                mapper.educationDtoToServiceRequestModelAdd(DTO);

        EducationServiceResponseModel addedEducation = portfolioService.saveEducation( educationServiceRequestModel );
        return mapper.educationSvcToGetDTO(addedEducation);
    }

    @GetMapping("/{id}")  //  GET BY ID   *****
    public ResponseEntity<EducationGetDTO> findById(@PathVariable Long id){
        try{
            EducationServiceResponseModel responseModel = portfolioService.findEducationById(id);

            EducationGetDTO DTO = mapper.educationSvcToGetDTO(responseModel);
            return new ResponseEntity<>( DTO, HttpStatus.OK);
        }catch (EducationNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    // delete Education
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            portfolioService.deleteEducation(id);
            return ResponseEntity.noContent().build(); // Statut 204 No Content
        } catch (DataAccessException e) {
            return ResponseEntity.status(502).build(); // Statut
        } catch (Exception e) {
            return ResponseEntity.notFound().build(); // Statut 404 Not Found
        }
    }
    // update Education
    @PutMapping("/{id}")
    public EducationGetDTO update(@RequestBody EducationUpdateDTO DTO){
        EducationServiceRequestUpdateModel educationServiceRequestUpdModel =
                mapper.educationDtoToServiceRequestModel(DTO);

        EducationServiceResponseModel updatedEducation =
                portfolioService.updateEducation( educationServiceRequestUpdModel );
        return mapper.educationSvcToGetDTO(updatedEducation);
    }



}
