package com.simplon.easyportfolio.api.controllers.skills;

import com.simplon.easyportfolio.api.exceptions.SkillNotFoundException;
import com.simplon.easyportfolio.api.mappers.EasyfolioMapper;
import com.simplon.easyportfolio.api.repositories.skills.CategorySkillRepository;
import com.simplon.easyportfolio.api.repositories.skills.CategorySkillRepositoryModel;
import com.simplon.easyportfolio.api.services.educations.EducationServiceRequestModel;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioService;
import com.simplon.easyportfolio.api.services.skills.SkillServiceRequestModel;
import com.simplon.easyportfolio.api.services.skills.SkillServiceRequestUpdateModel;
import com.simplon.easyportfolio.api.services.skills.SkillServiceResponseModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/skills")
public class SkillController {
    @Autowired
    PortfolioService portfolioService;
    private final EasyfolioMapper mapper = EasyfolioMapper.INSTANCE;

    @GetMapping("/{id}")  //  GET BY ID   *****
    public ResponseEntity<SkillGetDTO> findById(@PathVariable Long id) {
        try{
           SkillServiceResponseModel responseModel = portfolioService.findSkillById(id);

           SkillGetDTO DTO = mapper.skillSvcToGetDTO(responseModel);
           return new ResponseEntity<>( DTO, HttpStatus.OK);
        } catch (SkillNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // addSkill
    @PostMapping
    public SkillGetDTO add(@RequestBody @Valid SkillAddDTO DTO){
        SkillServiceRequestModel serviceModel = new SkillServiceRequestModel(DTO.getTitle(),
                DTO.getPortfolioId());

        SkillServiceResponseModel addedSkill = portfolioService.saveSkill( serviceModel );
        return mapper.skillSvcToGetDTO(addedSkill);
    }
    /** update Skill **/
    @PutMapping("/{id}")
    public SkillGetDTO update(@RequestBody @Valid SkillUpdateDTO DTO){
        SkillServiceRequestUpdateModel skillServiceRequestUpdModel = mapper.skillDtoToServiceRequestModel(DTO);
        System.out.println(skillServiceRequestUpdModel);
        SkillServiceResponseModel updatedSkill =
                portfolioService.updateSkill( skillServiceRequestUpdModel );
        return mapper.skillSvcToGetDTO(updatedSkill);
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


    @GetMapping("/categories")  //  GET BY ID   *****
    public ResponseEntity<List<CategorySkillRepositoryModel>> findAllCategorySkills() {
        return new ResponseEntity<>( portfolioService.getCategorySkills(), HttpStatus.OK);
    }





}


