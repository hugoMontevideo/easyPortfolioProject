package com.simplon.easyportfolio.api.services.portfolios;

import com.simplon.easyportfolio.api.mappers.EasyfolioMapper;
import com.simplon.easyportfolio.api.repositories.educations.EducationRepository;
import com.simplon.easyportfolio.api.repositories.educations.EducationRepositoryModel;
import com.simplon.easyportfolio.api.repositories.experiences.ExperienceRepository;
import com.simplon.easyportfolio.api.repositories.experiences.ExperienceRepositoryModel;
import com.simplon.easyportfolio.api.repositories.portfolios.PortfolioRepository;
import com.simplon.easyportfolio.api.repositories.portfolios.PortfolioRepositoryModel;
import com.simplon.easyportfolio.api.repositories.skills.SkillRepository;
import com.simplon.easyportfolio.api.repositories.skills.SkillRepositoryModel;
import com.simplon.easyportfolio.api.services.educations.EducationServiceModel;
import com.simplon.easyportfolio.api.services.educations.EducationServiceRequestModel;
import com.simplon.easyportfolio.api.services.educations.EducationServiceResponseModel;
import com.simplon.easyportfolio.api.services.experiences.ExperienceServiceModel;
import com.simplon.easyportfolio.api.services.experiences.ExperienceServiceResponseModel;
import com.simplon.easyportfolio.api.services.skills.SkillServiceModel;
import com.simplon.easyportfolio.api.services.skills.SkillServiceResponseModel;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sound.sampled.Port;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class PortfolioService {

    @Autowired
    PortfolioRepository portfolioRepository;
    @Autowired
    EducationRepository educationRepository;
    @Autowired
    ExperienceRepository experienceRepository;
    @Autowired
    SkillRepository skillRepository;

    private final EasyfolioMapper mapper = EasyfolioMapper.INSTANCE;

    // Table : PORTFOLIO   *****************
    public boolean add(PortfolioServiceRequestModel portfolioServiceModel) {
        PortfolioRepositoryModel portfolioRepositoryModel =
                new PortfolioRepositoryModel(portfolioServiceModel.getTitle(),
                        portfolioServiceModel.getName(), portfolioServiceModel.getFirstname(),
                        portfolioServiceModel.getEmail());
        PortfolioRepositoryModel portfolioRepositoryReturn = portfolioRepository.save(portfolioRepositoryModel);

         return portfolioRepositoryReturn != null;
    }

    public PortfolioServiceResponseModel findById(Long id) {
        Optional<PortfolioRepositoryModel> portfolioRepositoryModel = portfolioRepository.findById(id);
        if (portfolioRepositoryModel.isEmpty()){
            return null;
        }else{
            PortfolioServiceResponseModel responseModel = mapper.portfolioRepositoryToResponseSvc(portfolioRepositoryModel.get());
            // insertion de l'objet

            return responseModel ;
        }
    }

    public boolean update(PortfolioServiceRequestModel portfolioServiceModel) {
 // todo ******************

        return true;
    }
    @Transactional
    public ArrayList<PortfolioServiceResponseModel> findAll() {
        ArrayList<PortfolioServiceResponseModel> portfolioServiceModels = new ArrayList<>();

        ArrayList<PortfolioRepositoryModel> portfolioRepositoryModels = portfolioRepository.findAll();

        portfolioRepositoryModels.forEach((PortfolioRepositoryModel item)->portfolioServiceModels.add( mapper.portfolioRepositoryToResponseSvc(item) ));

        // todo *****  renseigner la arrayList skills

        return portfolioServiceModels;
    }

    public void delete(Long id) {
        portfolioRepository.deleteById(id);
    }

    // Table : EDUCATION   *****************
    public boolean addEducation(EducationServiceRequestModel educationRequest) {
        if (portfolioRepository.findById(educationRequest.getPortfolioId()).isPresent()) {
            PortfolioServiceModel portfolio = new PortfolioServiceModel(educationRequest.getPortfolioId());
            EducationServiceModel educationServiceModel = new EducationServiceModel(
                educationRequest.getTraining(),
                educationRequest.getSchool(),
                educationRequest.getDegree(),
                educationRequest.getStartDate(),
                educationRequest.getEndDate(),
                educationRequest.getDescription(),
                portfolio
            );

            EducationRepositoryModel education = mapper.educationServiceToRepositoryModel(educationServiceModel);

            educationRepository.save(education);

            return true;
        }
        return false;
    }

    // Table : EXPERIENCE   *****************
    public ExperienceRepositoryModel addExperience(ExperienceServiceModel experienceServiceModel) {
        ExperienceRepositoryModel repositoryModel = mapper.experienceServiceToRepositoryModel(experienceServiceModel);
        // adding portfolio manually
        Optional<PortfolioRepositoryModel> portfolioRepositoryModel =
                portfolioRepository.findById( experienceServiceModel.getPortfolio().getId() );

        if( portfolioRepositoryModel.isPresent() )  {
            repositoryModel.setPortfolio(portfolioRepositoryModel.get());
        }
        return experienceRepository.save(repositoryModel);
    }
    public ExperienceServiceResponseModel findExperienceById(Long id) {
        // TODO *** finish the method, not working yet ***
        Optional<ExperienceRepositoryModel> experienceRepositoryModel = experienceRepository.findById(id);

        if (experienceRepositoryModel.isEmpty()){
            return null;
        }else{
            //SkillServiceResponseModel item = mapper.skillRepositoryToResponseSvc(skillRepositoryModel.get());

            //return item;
            return new ExperienceServiceResponseModel();
        }

    }

    public void deleteExperience(Long id) {
        experienceRepository.deleteById(id);
    }


    // Table : SKILL   *****************

    public SkillRepositoryModel addSkill(SkillServiceModel skillServiceModel) {

        SkillRepositoryModel repositoryModel = mapper.skillServiceToRepositoryModel(skillServiceModel);
        // adding portfolio manually
        Optional<PortfolioRepositoryModel> portfolioRepositoryModel =
                portfolioRepository.findById( skillServiceModel.getPortfolio().getId() );

        if( portfolioRepositoryModel.isPresent() )  {
            repositoryModel.setPortfolio(portfolioRepositoryModel.get());
        }
        return skillRepository.save(repositoryModel);
    }

    public SkillServiceResponseModel findSkillById(Long id) {

        Optional<SkillRepositoryModel> skillRepositoryModel = skillRepository.findById(id);

        System.out.println(skillRepositoryModel.get());

        if (skillRepositoryModel.isEmpty()){
            return null;
        }else{
            //SkillServiceResponseModel item = mapper.skillRepositoryToResponseSvc(skillRepositoryModel.get());

            //return item;
            return new SkillServiceResponseModel();
        }
    }

    public void deleteSkill(Long id) {
        skillRepository.deleteById(id);
    }



}




//  getById  return new PortfolioServiceResponseModel(portfolioRepositoryModel.get().getId(),
//  portfolioRepositoryModel.get().getTitle(), portfolioRepositoryModel.get().getName(),             portfolioRepositoryModel.get().getFirstname(), portfolioRepositoryModel.get().getEmail());


// findAll portfolioRepositoryModels.forEach((item)->portfolioServiceModels.add(new PortfolioServiceResponseModel(
// item.getId(), item.getTitle(), item.getName(), item.getFirstname(), item.getEmail() )));