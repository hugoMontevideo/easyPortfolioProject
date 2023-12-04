package com.simplon.easyportfolio.api.controllers.projects;

import com.simplon.easyportfolio.api.controllers.skills.SkillGetDTO;
import com.simplon.easyportfolio.api.exceptions.PortfolioNotFoundException;
import com.simplon.easyportfolio.api.mappers.EasyfolioMapper;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioService;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceModel;
import com.simplon.easyportfolio.api.services.projects.ProjectServiceRequestModel;
import com.simplon.easyportfolio.api.services.projects.ProjectServiceResponseModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;
@Validated
@CrossOrigin
@RestController
@RequestMapping("api/projects")
public class ProjectController {
    @Autowired
    PortfolioService portfolioService;
    private final EasyfolioMapper mapper = EasyfolioMapper.INSTANCE;

    @PostMapping
    public ProjectGetDTO add(
            @RequestParam("title") @Size(min =2, message = "Le titre doit avoir entre 2 et 60 caractères") String title,
            @RequestParam ("description")String description,
            @RequestParam ("date")LocalDate date,
            @RequestParam ("fileName")String fileName,
            @RequestParam ("file")Optional<MultipartFile> file,
            @RequestParam ("portfolioId")Long portfolioId

            ){

        // crating DTO with received parameters
        ProjectDTO DTO = new ProjectDTO(title, description, date, fileName, file, portfolioId);

        ProjectServiceRequestModel projectServiceRequestModel =
                mapper.projectDtoToServiceRequestModelAdd(DTO);

        ProjectServiceResponseModel addedProject = portfolioService.saveProject(projectServiceRequestModel);
        return mapper.projectSvcToGetDTO( addedProject );
    }
    /** public boolean add(@RequestBody ProjectDTO projectDTO){
        System.out.println(projectDTO);
        ProjectServiceRequestModel projectServiceRequestModel =
                mapper.projectDtoToServiceRequestModel(projectDTO);
        return portfolioService.addProject( projectServiceRequestModel );
    }**/



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
