package com.simplon.easyportfolio.api.controllers.projects;

import com.simplon.easyportfolio.api.controllers.skills.SkillDTO;
import com.simplon.easyportfolio.api.exceptions.PortfolioNotFoundException;
import com.simplon.easyportfolio.api.exceptions.ProjectNotFoundException;
import com.simplon.easyportfolio.api.mappers.EasyfolioMapper;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioService;
import com.simplon.easyportfolio.api.services.projects.ProjectServiceRequestModel;
import com.simplon.easyportfolio.api.services.projects.ProjectServiceRequestUpdateModel;
import com.simplon.easyportfolio.api.services.projects.ProjectServiceResponseModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;
@Validated  // forms validation
@CrossOrigin
@RestController
@RequestMapping("api/projects")
public class ProjectController {
    @Autowired
    PortfolioService portfolioService;
    private final EasyfolioMapper mapper = EasyfolioMapper.INSTANCE;

    @PostMapping
    public ProjectGetDTO add( @RequestBody  ProjectAddDTO DTO) {
        // mapping DTO to service request model
        ProjectServiceRequestModel serviceModel = new ProjectServiceRequestModel(DTO.getTitle(), DTO.getPortfolioId());

        ProjectServiceResponseModel addedProject = portfolioService.addProject(serviceModel);
        return mapper.projectSvcToGetDTO( addedProject );
    }

    //update project
    @PutMapping("/{id}")
    public ProjectGetDTO update(
            @RequestParam("id") Optional<Long> id,
            @RequestParam("title") @Size(min =2, message = "Le titre doit avoir entre 2 et 60 caractères") String title,
            @RequestParam ("description")String description,
            @RequestParam ("date") LocalDate date,
            @RequestParam ("fileName")Optional<String> fileName,
            @RequestParam ("file")Optional<MultipartFile> file,
            @RequestParam ("portfolioId")Long portfolioId
    ){
        // creating DTO with received parameters
        ProjectUpdateDTO DTO = new ProjectUpdateDTO(id, title, description, date, fileName, file, portfolioId);
        ProjectServiceRequestUpdateModel projectServiceRequestUpdateModel =
                mapper.projectDtoToServiceRequestModel(DTO);


        ProjectServiceResponseModel addedProject = portfolioService.updateProject(projectServiceRequestUpdateModel);
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
            return new ResponseEntity<>( DTO, HttpStatus.OK);
        }catch (ProjectNotFoundException e){
            throw new RuntimeException(e);
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

    @DeleteMapping("/{id}/documents/{doc_id}")
    public ResponseEntity<Void> deletePictureOnProject(@PathVariable Long doc_id){
        try {
            portfolioService.getdDocumentProjectById(doc_id);
            return ResponseEntity.noContent().build(); // Statut 204 No Content
        } catch(DataAccessException e) {
            return ResponseEntity.status(502).build(); // Statut
        } catch(Exception e){
            return ResponseEntity.notFound().build(); // Statut 404 Not Found
        }
    }



}
