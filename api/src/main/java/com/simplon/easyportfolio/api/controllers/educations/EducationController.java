package com.simplon.easyportfolio.api.controllers.educations;

import com.simplon.easyportfolio.api.exceptions.PortfolioNotFoundException;
import com.simplon.easyportfolio.api.mappers.EasyfolioMapper;
import com.simplon.easyportfolio.api.services.educations.EducationServiceRequestModel;
import com.simplon.easyportfolio.api.services.educations.EducationServiceResponseModel;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioService;
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
    public boolean add(@RequestBody EducationDTO educationDTO){
        EducationServiceRequestModel educationServiceRequestModel =
                mapper.educationDtoToServiceRequestModel(educationDTO);

             return portfolioService.addEducation( educationServiceRequestModel );
    }

    @GetMapping("/{id}")  //  GET BY ID   *****
    public ResponseEntity<EducationGetDTO> findById(@PathVariable Long id){
        try{
            EducationServiceResponseModel responseModel = portfolioService.findEducationById(id);

            EducationGetDTO DTO = mapper.educationSvcToGetDTO(responseModel);
            // todo  ***  creer un byIddto qui a objet portfolio sans l'array de skills
            return new ResponseEntity<>( DTO, HttpStatus.OK);
        }catch (PortfolioNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getReason());
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





}
