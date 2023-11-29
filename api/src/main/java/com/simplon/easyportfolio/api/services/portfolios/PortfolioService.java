package com.simplon.easyportfolio.api.services.portfolios;

import com.github.slugify.Slugify;
import com.simplon.easyportfolio.api.mappers.EasyfolioMapper;
import com.simplon.easyportfolio.api.repositories.educations.EducationRepository;
import com.simplon.easyportfolio.api.repositories.educations.EducationRepositoryModel;
import com.simplon.easyportfolio.api.repositories.experiences.ExperienceRepository;
import com.simplon.easyportfolio.api.repositories.experiences.ExperienceRepositoryModel;
import com.simplon.easyportfolio.api.repositories.portfolios.PortfolioRepository;
import com.simplon.easyportfolio.api.repositories.portfolios.PortfolioRepositoryModel;
import com.simplon.easyportfolio.api.repositories.projects.ProjectRepository;
import com.simplon.easyportfolio.api.repositories.projects.ProjectRepositoryModel;
import com.simplon.easyportfolio.api.repositories.skills.SkillRepository;
import com.simplon.easyportfolio.api.repositories.skills.SkillRepositoryModel;
import com.simplon.easyportfolio.api.services.educations.EducationServiceModel;
import com.simplon.easyportfolio.api.services.educations.EducationServiceRequestModel;
import com.simplon.easyportfolio.api.services.educations.EducationServiceResponseModel;
import com.simplon.easyportfolio.api.services.experiences.ExperienceServiceModel;
import com.simplon.easyportfolio.api.services.experiences.ExperienceServiceResponseModel;
import com.simplon.easyportfolio.api.services.projects.ProjectServiceRequestModel;
import com.simplon.easyportfolio.api.services.projects.ProjectServiceResponseModel;
import com.simplon.easyportfolio.api.services.skills.SkillServiceModel;
import com.simplon.easyportfolio.api.services.skills.SkillServiceResponseModel;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import org.apache.commons.io.FilenameUtils;

@Service
public class PortfolioService {


    @Autowired
    PortfolioRepository portfolioRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    EducationRepository educationRepository;
    @Autowired
    ExperienceRepository experienceRepository;
    @Autowired
    SkillRepository skillRepository;

    private final Slugify slug = Slugify.builder().build();
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

            // insertion de l'objet

            return mapper.portfolioRepositoryToResponseSvc(portfolioRepositoryModel.get());
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

    // Table : PROJECT   *****************
    // add Project
    public boolean addProject(ProjectServiceRequestModel projectServiceRequestModel) {
        if(!projectServiceRequestModel.getFile().isEmpty()){
            // naming pictures : in project = project_ + projectName + (timeInMilli)
            long timestamp = System.currentTimeMillis();
            String pictureName = "project " + projectServiceRequestModel.getTitle() + "-" +timestamp ;
            String pictureName2 = uploadPicture(projectServiceRequestModel.getFile(), pictureName);
            projectServiceRequestModel.setFileName(pictureName2);
        }

        Optional<PortfolioRepositoryModel> portfolio = portfolioRepository.findById(projectServiceRequestModel.getPortfolioId().get());
        
        PortfolioServiceModel portfolioServiceModel = mapper.portfolioRepositoryToServiceModel(portfolio.get());

        projectServiceRequestModel.setPortfolio( Optional.ofNullable(portfolioServiceModel));

        System.out.println(projectServiceRequestModel);
        ProjectRepositoryModel project = mapper.projectServiceRequestToRepositoryModel(projectServiceRequestModel);

        return projectRepository.save(project) != null;
    }

    public ProjectServiceResponseModel findProjectById(Long id) {
        // TODO *** finish the method, not working yet ***
        Optional<ProjectRepositoryModel> projectRepositoryModel = projectRepository.findById(id);

        if (projectRepositoryModel.isEmpty()){
            return null;
        }else{
            //SkillServiceResponseModel item = mapper.skillRepositoryToResponseSvc(skillRepositoryModel.get());
            //return item;
            return new ProjectServiceResponseModel();

        }
    }

    // delete Project
    public boolean deleteProject(Long id) {

        try{
        //if(projectRepository.findById(id).isPresent()){
            projectRepository.deleteById(id);
            return true;
        }catch(Exception e){
            return false;
        }
    }



    // Table : EDUCATION   *****************
    // add Education
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

    public EducationServiceResponseModel findEducationById(Long id) {
        // TODO *** finish the method, not working yet ***
        Optional<EducationRepositoryModel> experienceRepositoryModel = educationRepository.findById(id);

        if (experienceRepositoryModel.isEmpty()){
            return null;
        }else{
            //SkillServiceResponseModel item = mapper.skillRepositoryToResponseSvc(skillRepositoryModel.get());

            //return item;
            return new EducationServiceResponseModel();
        }

    }

    // delete Education
    public boolean deleteEducation(Long id) {

        try{
            educationRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Table : EXPERIENCE   *****************

    public ExperienceRepositoryModel addExperience(ExperienceServiceModel experienceServiceModel) {
        ExperienceRepositoryModel repositoryModel = mapper.experienceServiceToRepositoryModel(experienceServiceModel);
        // adding portfolio manually
        Optional<PortfolioRepositoryModel> portfolioRepositoryModel =
                portfolioRepository.findById( experienceServiceModel.getPortfolio().getId() );

        portfolioRepositoryModel.ifPresent(repositoryModel::setPortfolio);
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
    // delete Experience
    public boolean deleteExperience(Long id) {
        try {
            experienceRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }

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



    // UTILS  *****************************************
    public String uploadPicture(MultipartFile file, String pictureName) throws IOError{
        try{
            String uploadDirectory = "/public/upload/pictures"; // pictures upload folder
            String filename = file.getOriginalFilename(); // upload file

            String extension = FilenameUtils.getExtension(filename);
            filename = slug.slugify(pictureName) + "." + extension;
            Path path = Paths.get(".", uploadDirectory).toAbsolutePath(); // absolute path
            File targetFile = new File(path.toString(), filename);
            if(!targetFile.getParentFile().exists()){
                targetFile.getParentFile().mkdirs();
            }
            file.transferTo(targetFile);
            return filename;
        }catch (IOError e){
            e.printStackTrace();

        } catch (IOException e) {
            throw new RuntimeException(e);

        }
        return "Error";
    }




}




//  getById  return new PortfolioServiceResponseModel(portfolioRepositoryModel.get().getId(),
//  portfolioRepositoryModel.get().getTitle(), portfolioRepositoryModel.get().getName(),             portfolioRepositoryModel.get().getFirstname(), portfolioRepositoryModel.get().getEmail());


// findAll portfolioRepositoryModels.forEach((item)->portfolioServiceModels.add(new PortfolioServiceResponseModel(
// item.getId(), item.getTitle(), item.getName(), item.getFirstname(), item.getEmail() )));