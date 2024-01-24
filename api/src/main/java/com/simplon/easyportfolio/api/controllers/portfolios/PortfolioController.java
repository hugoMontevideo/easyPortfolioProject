package com.simplon.easyportfolio.api.controllers.portfolios;

import com.simplon.easyportfolio.api.controllers.educations.EducationGetDTO;
import com.simplon.easyportfolio.api.controllers.experiences.ExperienceGetDTO;
import com.simplon.easyportfolio.api.controllers.projects.ProjectGetDTO;
import com.simplon.easyportfolio.api.controllers.skills.SkillGetDTO;
import com.simplon.easyportfolio.api.exceptions.PortfolioNotFoundException;
import com.simplon.easyportfolio.api.mappers.EasyfolioMapper;
import com.simplon.easyportfolio.api.services.educations.EducationServiceResponseModel;
import com.simplon.easyportfolio.api.services.experiences.ExperienceServiceResponseModel;
import com.simplon.easyportfolio.api.services.portfolios.*;
import com.simplon.easyportfolio.api.services.projects.ProjectServiceResponseModel;
import com.simplon.easyportfolio.api.services.skills.SkillServiceResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Validated
@RestController
@RequestMapping("api/portfolios")
public class PortfolioController {
    @Autowired
    PortfolioService portfolioService;
    private final EasyfolioMapper mapper = EasyfolioMapper.INSTANCE;

    /** update PORTFOLIO **/
    @PutMapping("/{id}")
    public ResponseEntity<PortfolioFullDTO> update(@RequestBody PortfolioDTO DTO) throws ResponseStatusException{
        try {
        PortfolioServiceRequestModel portfolioServiceRequestModel = mapper.portfolioDtoToServiceRequestModel(DTO);

        PortfolioServiceResponseModel serviceModel =  portfolioService.updatePortfolio(portfolioServiceRequestModel);

        PortfolioFullDTO updatedDTO = mapper.portfolioSvcResponseToFullDTO(serviceModel);
        return ResponseEntity.ok(updatedDTO);

        } catch(ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** update profileImgPath property **/
    @PutMapping("/{id}/about_me_picture")
    public ResponseEntity<PortfolioFullDTO>updateProfileImgPath(@PathVariable Long id, @RequestParam ("file") MultipartFile file) throws ResponseStatusException {
        try {
            PortfolioServiceResponseModel serviceModel =    portfolioService.updateProfileImgPath(id, file);

            PortfolioFullDTO updatedDTO = mapper.portfolioSvcResponseToFullDTO(serviceModel);

            return  ResponseEntity.ok(updatedDTO);
        } catch(ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // add portfolio
    @PostMapping
    public PortfolioFullDTO add( @RequestBody PortfolioAddDTO DTO) {
        // mapping DTO to service request model
        PortfolioServiceRequestModel serviceModel = new PortfolioServiceRequestModel(DTO.getTitle(), DTO.getUserId());

        PortfolioServiceResponseModel addedPortfolio = portfolioService.addPortfolio(serviceModel);
        System.out.println(addedPortfolio);
        return mapper.portfolioSvcResponseToFullDTO( addedPortfolio );
    }

    /** getById portfolio **/
    @GetMapping("/{id}")  //  GET BY ID   *****
    public ResponseEntity<PortfolioFullDTO> findById(@PathVariable Long id){
        try{
            PortfolioServiceModel serv = portfolioService.findById(id);

            return new ResponseEntity<>( mapper.portfolioSvcToFullDTO(portfolioService.findById(id)), HttpStatus.OK);

           //PortfolioGetDTO DTO =  mapper.portfolioSvcToGetDTO( portfolioService.findById(id));
            //return new ResponseEntity<>( DTO, HttpStatus.OK);
        }catch (PortfolioNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getReason());
        }
    }

    /** getById portfolio - online template **/
    @GetMapping("/online/{id}")  //  GET BY ID   *****
    public ResponseEntity<PortfolioFullDTO> findByIdOnline(@PathVariable Long id){
        try{
            System.out.println("online");
            PortfolioServiceModel serv = portfolioService.findById(id);

            return new ResponseEntity<>( mapper.portfolioSvcToFullDTO(portfolioService.findById(id)), HttpStatus.OK);

            //PortfolioGetDTO DTO =  mapper.portfolioSvcToGetDTO( portfolioService.findById(id));
            //return new ResponseEntity<>( DTO, HttpStatus.OK);
        }catch (PortfolioNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getReason());
        }
    }
    @GetMapping("/{id}/projects")  //  GET PROJECTS BY ID PORTFOLIO  *****
    public List<ProjectGetDTO> getProjectsByPortfolioId(@PathVariable Long id){
        try{
            List<ProjectServiceResponseModel> projectServices = portfolioService.getProjectsByPortfolioId(id);
           return mapper.listProjectSvcToGetDTO( projectServices);
        }catch (PortfolioNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getReason());
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/{id}/educations")  //  GET EDUCATIONS BY ID PORTFOLIO  *****
    public List<EducationGetDTO> getEducationsByPortfolioId(@PathVariable Long id){
        try{
            List<EducationServiceResponseModel> educationServices = portfolioService.getEducationsByPortfolioId(id);
            return mapper.listEducationSvcToGetDTO( educationServices );
        }catch (PortfolioNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/{id}/skills")  //  GET skills BY ID PORTFOLIO  *****
    public List<SkillGetDTO> getSkillsByPortfolioId(@PathVariable Long id){
        try{
            List<SkillServiceResponseModel> skillsServices = portfolioService.getSkillsByPortfolioId(id);
            return mapper.listSkillSvcToGetDTO( skillsServices );
        }catch (PortfolioNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/{id}/experiences")  //  GET experiences BY ID PORTFOLIO  *****
    public List<ExperienceGetDTO> getExperiencesByPortfolioId(@PathVariable Long id){
        try{
            List<ExperienceServiceResponseModel> experienceServices = portfolioService.getexperiencesByPortfolioId(id);
            return mapper.listExperienceSvcToGetDTO( experienceServices );
        }catch (PortfolioNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    /** getAll portfolios **/
    @GetMapping     //  GET ALL
    public List<PortfolioGetDTO> findAll(){
        List<PortfolioServiceModel> portfolioServiceModels = portfolioService.findAll();

        return mapper.listPortolioSvcToGetDTO(portfolioServiceModels);
    }
/**
    @PutMapping("/{id}")  //  UPDATE
    public ResponseEntity<String> updateFolio(@PathVariable("id") Long id,
                                              @RequestParam PortfolioGetDTO portfolioGetDTO){
        if(portfolioService.findById(id) != null){
            portfolioService.update(new PortfolioServiceRequestModel( Optional.ofNullable(id), portfolioGetDTO.getTitle(),
                    portfolioGetDTO.getName(),
                    portfolioGetDTO.getFirstname(), portfolioGetDTO.getEmail()));
            return ResponseEntity.ok("Le portfolio id: "+ id +" a été modifié");
        }else{
           throw new PortfolioNotFoundException(HttpStatus.NOT_FOUND, "Le folio n'a pas été trouvé.");
        }
    } **/
    // delete Portfolio
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePortfolio(@PathVariable Long id){
    try {
            portfolioService.deletePortfolio(id);
            return new ResponseEntity(HttpStatus.OK); // Statut 204 No Content
        } catch(
            DataAccessException e) {
            return ResponseEntity.status(502).build(); // Statut
        } catch(Exception e){
            return ResponseEntity.notFound().build(); // Statut 404 Not Found
        }
    }






}

