package com.simplon.easyportfolio.api.controllers.projects;

import com.simplon.easyportfolio.api.exceptions.PortfolioNotFoundException;
import com.simplon.easyportfolio.api.mappers.EasyfolioMapper;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioService;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceModel;
import com.simplon.easyportfolio.api.services.projects.ProjectServiceRequestModel;
import com.simplon.easyportfolio.api.services.projects.ProjectServiceResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin
@RestController
@RequestMapping("api/projects")
public class ProjectController {
    @Autowired
    PortfolioService portfolioService;

    private final EasyfolioMapper mapper = EasyfolioMapper.INSTANCE;

    @PostMapping
    public boolean add(@RequestBody ProjectDTO projectDTO){
        ProjectServiceRequestModel projectServiceRequestModel =
                mapper.projectDtoToServiceRequestModel(projectDTO);
        PortfolioServiceModel portfolio = new PortfolioServiceModel(projectDTO.getPortfolioId());

        // TODO: convertir portfolioId en Portfolio portfolio dans le requestModel
        projectServiceRequestModel.setPortfolio( portfolio );

        return portfolioService.addProject( projectServiceRequestModel );
    }

    @GetMapping("/{id}")  //  GET BY ID   *****
    public ResponseEntity<ProjectGetDTO> findById(@PathVariable Long id){
        try{
            ProjectServiceResponseModel responseModel = portfolioService.findProjectById(id);

            ProjectGetDTO DTO = mapper.projectSvcToGetDTO(responseModel);
            // TODO  ***  creer un byIddto qui a objet portfolio sans l'array de skills
            return new ResponseEntity<>( DTO, HttpStatus.OK);
        }catch (PortfolioNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getReason());
        }
    }

    // delete Project
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try {
            portfolioService.deleteProject(id);
            return ResponseEntity.noContent().build(); // Statut 204 No Content
        } catch(DataAccessException e) {
            return ResponseEntity.status(502).build(); // Statut
        } catch(Exception e){
            return ResponseEntity.notFound().build(); // Statut 404 Not Found
        }
    }



}
