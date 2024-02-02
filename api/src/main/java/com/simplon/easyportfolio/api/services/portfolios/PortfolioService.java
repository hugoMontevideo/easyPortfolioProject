package com.simplon.easyportfolio.api.services.portfolios;

import com.github.slugify.Slugify;
import com.simplon.easyportfolio.api.controllers.socials.SocialServiceRequestModel;
import com.simplon.easyportfolio.api.domain.User;
import com.simplon.easyportfolio.api.exceptions.*;
import com.simplon.easyportfolio.api.mappers.EasyfolioMapper;
import com.simplon.easyportfolio.api.repositories.educations.EducationRepository;
import com.simplon.easyportfolio.api.repositories.educations.EducationRepositoryModel;
import com.simplon.easyportfolio.api.repositories.experiences.ExperienceRepository;
import com.simplon.easyportfolio.api.repositories.experiences.ExperienceRepositoryModel;
import com.simplon.easyportfolio.api.repositories.portfolios.PortfolioRepository;
import com.simplon.easyportfolio.api.repositories.portfolios.PortfolioRepositoryModel;
import com.simplon.easyportfolio.api.repositories.projects.DocumentProjectRepository;
import com.simplon.easyportfolio.api.repositories.projects.DocumentProjectRepositoryModel;
import com.simplon.easyportfolio.api.repositories.projects.ProjectRepository;
import com.simplon.easyportfolio.api.repositories.projects.ProjectRepositoryModel;
import com.simplon.easyportfolio.api.repositories.security.OwnerRepository;
import com.simplon.easyportfolio.api.repositories.skills.CategorySkillRepository;
import com.simplon.easyportfolio.api.repositories.skills.CategorySkillRepositoryModel;
import com.simplon.easyportfolio.api.repositories.skills.SkillRepository;
import com.simplon.easyportfolio.api.repositories.skills.SkillRepositoryModel;
import com.simplon.easyportfolio.api.repositories.socials.SocialRepository;
import com.simplon.easyportfolio.api.repositories.socials.SocialRepositoryModel;
import com.simplon.easyportfolio.api.services.educations.EducationServiceRequestModel;
import com.simplon.easyportfolio.api.services.educations.EducationServiceRequestUpdateModel;
import com.simplon.easyportfolio.api.services.educations.EducationServiceResponseModel;
import com.simplon.easyportfolio.api.services.experiences.ExperienceServiceRequestModel;
import com.simplon.easyportfolio.api.services.experiences.ExperienceServiceRequestUpdateModel;
import com.simplon.easyportfolio.api.services.experiences.ExperienceServiceResponseModel;
import com.simplon.easyportfolio.api.services.projects.*;
import com.simplon.easyportfolio.api.services.skills.SkillServiceRequestModel;
import com.simplon.easyportfolio.api.services.skills.SkillServiceRequestUpdateModel;
import com.simplon.easyportfolio.api.services.skills.SkillServiceResponseModel;
import com.simplon.easyportfolio.api.services.socials.SocialServiceResponseModel;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

@Service
public class PortfolioService {
    private static final LocalDate DATE_VIDE = LocalDate.of(1970,1,2);
    @Autowired
    OwnerRepository ownerRepository;
    @Autowired
    PortfolioRepository portfolioRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    DocumentProjectRepository documentProjectRepository;
    @Autowired
    EducationRepository educationRepository;
    @Autowired
    ExperienceRepository experienceRepository;
    @Autowired
    SkillRepository skillRepository;
    @Autowired
    CategorySkillRepository categorySkillRepository;
    @Autowired
    SocialRepository socialRepository;
    private final Slugify slug = Slugify.builder().build();
    private final EasyfolioMapper mapper = EasyfolioMapper.INSTANCE;

    /** Table : PORTFOLIO   ***************** **/
    //update
    public PortfolioServiceResponseModel updatePortfolio(@NotNull PortfolioServiceRequestModel o) {
        Optional<User> user = ownerRepository.findById(o.getUserId().get());
        PortfolioRepositoryModel portfolio = mapper.portfolioServiceRequestToRepositoryModelUpdate(o);
        /**     PortfolioRepositoryModel port1 = new PortfolioRepositoryModel(
                o.getId().get(), o.getTitle(),o.getDescription(),o.getName(), o.getFirstname(),o.getEmail(),o.getCity(), o.getProfileImgPath(), o.getAboutMe(),user.get()

        );**/
        portfolio.setUser(user.get());
        PortfolioRepositoryModel updatedPortfolio = portfolioRepository.save(portfolio);

        return mapper.portfolioRepositoryToResponseSvc(updatedPortfolio);
    }
    /** updateProfileImgPath  **/
    public PortfolioServiceResponseModel updateProfileImgPath(Long id, MultipartFile file) throws IOException {
        Optional<PortfolioRepositoryModel> portfolioRepoModel = portfolioRepository.findById(id);
        /* deleting file on folder **/
        String uploadDirectory = "/public/upload/pictures/portfolios"; // pictures upload folder
        if (!StringUtils.isBlank(portfolioRepoModel.get().getProfileImgPath())){
            deleteProfileImgPath(portfolioRepoModel.get().getProfileImgPath(), uploadDirectory);
        };
        /* naming pictures  = profile-img- + projectName + (timeInMilli) **/
        long timestamp = System.currentTimeMillis();
        String pictureName = "about-me-img-" + timestamp ;
        /* file saved on server **/
        String pictureName2 = uploadPicture(file, pictureName, uploadDirectory);
        /* filling userRepositoryModel manually **/
        portfolioRepoModel.get().setProfileImgPath(pictureName2);
        /* updatedUser in db    *** User **/
        PortfolioRepositoryModel savedPortfolio = portfolioRepository.save(portfolioRepoModel.get());

        return mapper.portfolioRepositoryToResponseSvc(savedPortfolio);
    }

    private String uploadPicture(MultipartFile file, String pictureName, String directory) throws IOError{
        try{
            String filename = file.getOriginalFilename(); // upload file
            String extension = FilenameUtils.getExtension(filename); // getting file extension
            filename = slug.slugify(pictureName) + "." + extension;

            Path path = Paths.get(".", directory).toAbsolutePath(); // absolute path
            File targetFile = new File(path.toString(), filename);
            if(!targetFile.getParentFile().exists()){
                targetFile.getParentFile().mkdirs();
            }
            file.transferTo(targetFile);
            System.out.println(filename+"****");
            return filename;
        }catch (IOError e){
            e.printStackTrace();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "Error";
    }

    //add portfolio
    public boolean add(@NotNull PortfolioServiceRequestModel portfolioServiceModel) {
        PortfolioRepositoryModel portfolioRepositoryModel =
                new PortfolioRepositoryModel(portfolioServiceModel.getTitle(),
                        portfolioServiceModel.getName(), portfolioServiceModel.getFirstname(),
                        portfolioServiceModel.getEmail());
        PortfolioRepositoryModel portfolioRepositoryReturn = portfolioRepository.save(portfolioRepositoryModel);

         return portfolioRepositoryReturn != null;
    }
    public PortfolioServiceResponseModel addPortfolio(PortfolioServiceRequestModel serviceModel) {
        Optional<User> user = ownerRepository.findById(serviceModel.getUserId().get());

        PortfolioRepositoryModel repositoryModel = PortfolioRepositoryModel.builder()
                .title(serviceModel.getTitle())
                .user(user.get())
                .build();
        PortfolioRepositoryModel portfolioRepositoryadded = portfolioRepository.save(repositoryModel);
        return mapper.portfolioRepositoryToResponseSvc(portfolioRepositoryadded);
    }

    // get Portfolio by Id
    public PortfolioServiceModel findById(Long id) {
        Optional<PortfolioRepositoryModel> portfolioRepositoryModel = portfolioRepository.findById(id);
        return mapper.portfolioRepositoryToServiceModel(portfolioRepositoryModel.get());
    }

    // get projects by PortfolioId
    public List<ProjectServiceResponseModel> getProjectsByPortfolioId(Long id) throws PortfolioNotFoundException {
        try {
            Optional<PortfolioRepositoryModel> portfolioRepositoryModel = portfolioRepository.findById(id);
            List<ProjectRepositoryModel> projects = portfolioRepositoryModel.get().getProjects();

            return mapper.listProjectRepoToSvc(projects);
        }catch(Exception exception){
            throw new PortfolioNotFoundException(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public List<PortfolioServiceModel> findAll() {
        //ArrayList<PortfolioServiceResponseModel> portfolioServiceModels = new ArrayList<>();

        //ArrayList<PortfolioRepositoryModel> portfolioRepositoryModels = portfolioRepository.findAll();
        List<PortfolioRepositoryModel> portfolioRepositoryModels = portfolioRepository.findAll();

        //portfolioRepositoryModels.forEach((PortfolioRepositoryModel item)->portfolioServiceModels.add( mapper.portfolioRepositoryToResponseSvc(item) ));


        return mapper.listPortolioRepositoryToSvcModel(portfolioRepositoryModels);
    }

    public void deletePortfolio(Long id) {
        portfolioRepository.deleteById(id);
    }

    /** Table : PROJECT   ***************** **/

    /** add Project **/
    public ProjectServiceResponseModel addProject(@NotNull ProjectServiceRequestModel projectServiceRequestModel ) {
       // find portfolio by id and ...
        Optional<PortfolioRepositoryModel> portfolio = portfolioRepository.findById(projectServiceRequestModel.getPortfolioId().get());
        PortfolioServiceModel portfolioServiceModel = mapper.portfolioRepositoryToServiceModel(portfolio.get());
        // adding portfolio manually
        projectServiceRequestModel.setPortfolio( Optional.ofNullable(portfolioServiceModel));
        Optional<LocalDate> noDate = Optional.empty();
        projectServiceRequestModel.setDate(noDate);

        ProjectRepositoryModel project = mapper.projectServiceRequestToRepositoryModelAdd(projectServiceRequestModel);
        ProjectRepositoryModel addedProject = projectRepository.save(project);  //

        return mapper.projectRepositoryToResponseSvc(addedProject);
    }
    public ProjectServiceResponseModel updateProject( ProjectServiceRequestUpdateModel projectServiceRequestModel ) {
    Optional<PortfolioRepositoryModel> portfolio = portfolioRepository.findById(projectServiceRequestModel.getPortfolioId().get());
    PortfolioServiceModel portfolioServiceModel = mapper.portfolioRepositoryToServiceModel(portfolio.get());
    // adding portfolio manually
    projectServiceRequestModel.setPortfolio( Optional.ofNullable(portfolioServiceModel));
    // verifing date - if date == 1970 01 01 we set it to empty ** projectModel
    if(!isValidDate(projectServiceRequestModel.getDate().get())){
        Optional<LocalDate> noDate = Optional.empty();
        projectServiceRequestModel.setDate(noDate);
    }
    ProjectRepositoryModel project = mapper.projectServiceRequestToRepositoryModel(projectServiceRequestModel);

    if(!projectServiceRequestModel.getFile().isEmpty()){
        String uploadDirectory = "/public/upload/pictures"; // pictures upload folder
        // naming pictures : in project = project_ + projectName + (timeInMilli)
        long timestamp = System.currentTimeMillis();
        String pictureName = "project " + projectServiceRequestModel.getTitle() + "-" +timestamp ;
        /** picture saved on server **/
        String pictureName2 = uploadPicture(projectServiceRequestModel.getFile().get(), pictureName, uploadDirectory);
        // filling documentModel manually
        DocumentProjectRepositoryModel document = new DocumentProjectRepositoryModel();
        document.setFilename(pictureName2);
        document.setProject(project);
        /** saving documentProjectModel in db    *** documentProjectModel **/
        documentProjectRepository.save(document);
    }
    ProjectRepositoryModel addedProject = projectRepository.save(project);

    return mapper.projectRepositoryToResponseSvc(addedProject);
}

    // find by id project
    public ProjectServiceResponseModel findProjectById(Long id) throws ProjectNotFoundException {
        try {
            Optional<ProjectRepositoryModel> projectRepositoryModel = projectRepository.findById(id);
            return mapper.projectRepositoryToResponseSvc(projectRepositoryModel.get());
        }catch(Exception exception){
            throw new ProjectNotFoundException("Project not found with id : " + id);
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

/** Table : EDUCATION   ***************** **/
    // add Education
    public EducationServiceResponseModel saveEducation(EducationServiceRequestModel educationRequest) {
        //getting the portfolioRepositoryModel
        Optional<PortfolioRepositoryModel> portfolio = portfolioRepository.findById( educationRequest.getPortfolioId().get() );
        PortfolioServiceModel portfolioServiceModel = mapper.portfolioRepositoryToServiceModel(portfolio.get());
        // adding portfolio manually
        educationRequest.setPortfolio(Optional.ofNullable(portfolioServiceModel));
        Optional<LocalDate> noDate = Optional.empty();
        educationRequest.setStartDate(noDate);
        educationRequest.setEndDate(noDate);

        EducationRepositoryModel education =
                mapper.educationServiceRequestToRepositoryModelAdd(educationRequest);
        EducationRepositoryModel addedExperience = educationRepository.save(education);
        return mapper.educationRepositoryToResponseSvc(addedExperience);
    }
    //update education
    public EducationServiceResponseModel updateEducation(EducationServiceRequestUpdateModel educationServiceModel) {
        //getting the portfolioRepositoryModel
        Optional<PortfolioRepositoryModel> portfolio =
                portfolioRepository.findById( educationServiceModel.getPortfolioId().get() );
        PortfolioServiceModel portfolioServiceModel = mapper.portfolioRepositoryToServiceModel(portfolio.get());
        // adding portfolio manually
        educationServiceModel.setPortfolio(Optional.ofNullable(portfolioServiceModel));
        Optional<LocalDate> noDate = Optional.empty();
        if(educationServiceModel.getStartDate().isPresent()){
            if(!isValidDate(educationServiceModel.getStartDate().get())){
                educationServiceModel.setStartDate(noDate);
            }
        }
        if(educationServiceModel.getEndDate().isPresent()){
            if(!isValidDate(educationServiceModel.getEndDate().get())){
                educationServiceModel.setEndDate(noDate);
            }
        }

        EducationRepositoryModel education = mapper.educationServiceRequestToRepositoryModel(educationServiceModel);
        EducationRepositoryModel addedEducation = educationRepository.save(education);
        return mapper.educationRepositoryToResponseSvc(addedEducation);
    }

    // find by id education
    public EducationServiceResponseModel findEducationById(Long id) throws EducationNotFoundException {
        try {
            Optional<EducationRepositoryModel> educationRepositoryModel = educationRepository.findById(id);
            return mapper.educationRepositoryToResponseSvc(educationRepositoryModel.get());

        }catch(Exception exception){
            throw new EducationNotFoundException("Education not found with id : " + id);
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

/** Table : EXPERIENCE   *****************/
    // add Experience
    public ExperienceServiceResponseModel saveExperience(ExperienceServiceRequestModel experienceServiceRequestModel) {
        //getting the portfolioRepositoryModel
        Optional<PortfolioRepositoryModel> portfolio = portfolioRepository.findById( experienceServiceRequestModel.getPortfolioId().get() );
        PortfolioServiceModel portfolioServiceModel = mapper.portfolioRepositoryToServiceModel(portfolio.get());
        // adding portfolio manually
        experienceServiceRequestModel.setPortfolio(Optional.ofNullable(portfolioServiceModel));
        Optional<LocalDate> noDate = Optional.empty();
        experienceServiceRequestModel.setStartDate(noDate);
        experienceServiceRequestModel.setEndDate(noDate);

        ExperienceRepositoryModel experience =
                mapper.experienceServiceRequestToRepositoryModelAdd(experienceServiceRequestModel);
        ExperienceRepositoryModel addedExperience = experienceRepository.save(experience);
        return mapper.experienceRepositoryToResponseSvc(addedExperience);
    }

    //find by id Experience
    public ExperienceServiceResponseModel findExperienceById(Long id) throws ExperienceNotFoundException {
        try {
            Optional<ExperienceRepositoryModel> experienceRepositoryModel = experienceRepository.findById(id);
            return mapper.experienceRepositoryToResponseSvc(experienceRepositoryModel.get());
        } catch (Exception exception){
            throw new ExperienceNotFoundException("Experience not found with id : " + id);
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
    //update experience
    public ExperienceServiceResponseModel updateExperience(ExperienceServiceRequestUpdateModel experienceServiceModel) {
        //getting the portfolioRepositoryModel
        Optional<PortfolioRepositoryModel> portfolio =
                portfolioRepository.findById( experienceServiceModel.getPortfolioId().get() );
        PortfolioServiceModel portfolioServiceModel = mapper.portfolioRepositoryToServiceModel(portfolio.get());
        // adding portfolio manually
        experienceServiceModel.setPortfolio(Optional.ofNullable(portfolioServiceModel));
        Optional<LocalDate> noDate = Optional.empty();
        if(experienceServiceModel.getStartDate().isPresent()){
            if(!isValidDate(experienceServiceModel.getStartDate().get())){
                experienceServiceModel.setStartDate(noDate);
            }
        }
        if(experienceServiceModel.getEndDate().isPresent()){
            if(!isValidDate(experienceServiceModel.getEndDate().get())){
                experienceServiceModel.setEndDate(noDate);
            }
        }

        ExperienceRepositoryModel experience = mapper.experienceServiceRequestToRepositoryModel(experienceServiceModel);
        ExperienceRepositoryModel addedExperience = experienceRepository.save(experience);
        return mapper.experienceRepositoryToResponseSvc(addedExperience);
    }

/** Table : SKILL   *****************/
    //update skill
    public SkillServiceResponseModel updateSkill(SkillServiceRequestUpdateModel skillServiceRequestUpdateModel) {
        //getting the portfolioRepositoryModel
        Optional<PortfolioRepositoryModel> portfolio =
                portfolioRepository.findById( skillServiceRequestUpdateModel.getPortfolioId().get() );
        PortfolioServiceModel portfolioServiceModel = mapper.portfolioRepositoryToServiceModel(portfolio.get());
        // adding portfolio manually
        skillServiceRequestUpdateModel.setPortfolio(Optional.ofNullable(portfolioServiceModel));

        SkillRepositoryModel skill = mapper.skillServiceRequestToRepositoryModel(skillServiceRequestUpdateModel);

        SkillRepositoryModel addedSkill = skillRepository.save(skill);
        return mapper.skillRepositoryToResponseSvc(addedSkill);
    }
    // add skill
    public SkillServiceResponseModel saveSkill(SkillServiceRequestModel skillServiceRequestModel) {
        //getting the portfolioRepositoryModel
        Optional<PortfolioRepositoryModel> portfolio = portfolioRepository.findById( skillServiceRequestModel.getPortfolioId().get() );
        PortfolioServiceModel portfolioServiceModel = mapper.portfolioRepositoryToServiceModel(portfolio.get());
        // adding portfolio manually
        skillServiceRequestModel.setPortfolio(Optional.ofNullable(portfolioServiceModel));

        SkillRepositoryModel skill = mapper.skillServiceRequestToRepositoryModelAdd(skillServiceRequestModel);
        SkillRepositoryModel addedSkill = skillRepository.save(skill);
        return mapper.skillRepositoryToResponseSvc(addedSkill);
    }
    // findById Skill
    public SkillServiceResponseModel findSkillById(Long id) throws SkillNotFoundException {
        try{
        Optional<SkillRepositoryModel> skillRepositoryModel = skillRepository.findById(id);
        //if( skillRepositoryModel.isEmpty() ){
            return mapper.skillRepositoryToResponseSvc(skillRepositoryModel.get());

        }catch(Exception exception){
            throw new SkillNotFoundException("Skill not found with id : " + id);
        }
    }
    // delete Skill
    public boolean deleteSkill(Long id) {
        try {
            skillRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** Table: SOCIAL  ************************************** **/
    //  save social
    public SocialServiceResponseModel saveSocial(SocialServiceRequestModel serviceModel) {
        //getting the portfolioRepositoryModel
        Optional<PortfolioRepositoryModel> portfolio = portfolioRepository.findById( serviceModel.getPortfolioId().get() );
        PortfolioServiceModel portfolioServiceModel = mapper.portfolioRepositoryToServiceModel(portfolio.get());
        // adding portfolio manually
        serviceModel.setPortfolio(Optional.ofNullable(portfolioServiceModel));

        SocialRepositoryModel social = mapper.socialServiceRequestToRepositoryModelAdd(serviceModel);
        SocialRepositoryModel addedSocial = socialRepository.save(social);
        SocialServiceResponseModel socialResponse = mapper.socialRepositoryToResponseSvc(addedSocial);

        return socialResponse;
    }


    /** documents  ***************************************** **/
    //delete document project
    // save document project
    public DocumentProjectServiceResponseModel saveDocumentProject(DocumentProjectServiceRequestModel documentServiceRequestModel) {
        if(!documentServiceRequestModel.getFile().isEmpty()){
            String uploadDirectory = "/public/upload/pictures"; // pictures upload folder
            // naming pictures : in project = project_ + projectName + (timeInMilli)
            long timestamp = System.currentTimeMillis();
            String pictureName = "project " + documentServiceRequestModel.getTitle() + "-" +timestamp ;
            String pictureName2 = uploadPicture(documentServiceRequestModel.getFile().get(), pictureName,uploadDirectory);
            documentServiceRequestModel.setFilename(Optional.of(pictureName2));
        }
        DocumentProjectRepositoryModel documentProject =
                mapper.documentProjectServiceRequestToRepositoryModelAdd(documentServiceRequestModel);
        Optional<ProjectRepositoryModel> project =
                projectRepository.findById(documentServiceRequestModel.getProjectId().get());
        //adding project manually
        documentProject.setProject(project.get());
        DocumentProjectRepositoryModel addedDocProject = documentProjectRepository.save(documentProject);
        return mapper.documentProjectRepositoryToResponseSvc(addedDocProject);
    }
    private DocumentProjectServiceResponseModel pushDocumentProject(DocumentProjectServiceRequestModel documentRequestModel) {
        DocumentProjectRepositoryModel documentProjectRepositoryModel =
                mapper.documentProjectServiceRequestToRepositoryModelAdd(documentRequestModel);
        Optional<ProjectRepositoryModel> project =
                projectRepository.findById(documentRequestModel.getProjectId().get());
        System.out.println(project);
        //adding project manually
        documentProjectRepositoryModel.setProject(project.get());

        DocumentProjectRepositoryModel addedDocProject = documentProjectRepository.save(documentProjectRepositoryModel);
        return mapper.documentProjectRepositoryToResponseSvc(addedDocProject);
    }


    /** ************ getting Arrays By Id  ********************** **/
    public List<DocumentProjectServiceResponseModel> getDocumentProjectsByPortfolioId(Long id) throws ProjectNotFoundException {
        try {
            Optional<ProjectRepositoryModel> projectRepositoryModel = projectRepository.findById(id);
            List<DocumentProjectRepositoryModel> documentProjects = projectRepositoryModel.get().getDocuments();

            return mapper.listDocumentProjectRepoToSvc(documentProjects);
        }catch(Exception exception){
            throw new ProjectNotFoundException("Project not found with id : " + id);
        }
    }


    public List<EducationServiceResponseModel> getEducationsByPortfolioId(Long id) throws PortfolioNotFoundException {
        try {
            Optional<PortfolioRepositoryModel> portfolioRepositoryModel = portfolioRepository.findById(id);
            List<EducationRepositoryModel> educations = portfolioRepositoryModel.get().getEducations();

            return mapper.listEducationRepoToSvc(educations);
        }catch(Exception exception){
            throw new PortfolioNotFoundException(HttpStatus.NOT_FOUND);
        }
    }

    public List<SkillServiceResponseModel> getSkillsByPortfolioId(Long id) throws PortfolioNotFoundException {
        try {
            Optional<PortfolioRepositoryModel> portfolioRepositoryModel = portfolioRepository.findById(id);
            List<SkillRepositoryModel> skills = portfolioRepositoryModel.get().getSkills();

            return mapper.listSkillRepoToSvc(skills);
        }catch(Exception exception){
            throw new PortfolioNotFoundException(HttpStatus.NOT_FOUND);
        }
    }

    public List<ExperienceServiceResponseModel> getexperiencesByPortfolioId(Long id) throws PortfolioNotFoundException {
        try {
            Optional<PortfolioRepositoryModel> portfolioRepositoryModel = portfolioRepository.findById(id);
            List<ExperienceRepositoryModel> experiences = portfolioRepositoryModel.get().getExperiences();

            return mapper.listExperienceRepoToSvc(experiences);
        }catch(Exception exception){
            throw new PortfolioNotFoundException(HttpStatus.NOT_FOUND);
        }
    }

    /** UTILS  ***************************************** **/

    private boolean isValidDate (LocalDate date){
        return date.isAfter(DATE_VIDE);
    }

    public void deleteDocumentProjectById(Long docId) throws Exception {
        try{
            Optional<DocumentProjectRepositoryModel> document = documentProjectRepository.findById(docId);
            String pictureName = document.get().getFilename();
            Path picturePath = Paths.get(".", "/public/upload/pictures", pictureName);
            if(Files.exists(picturePath)){
                // deleting file on public folder
                Files.delete(picturePath);
            }
            // deleting on bdd
            documentProjectRepository.deleteById(docId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean deleteProfileImgPath(String profileImgPath, String directory) throws IOException {
        try{
            if (profileImgPath != null && !profileImgPath.isEmpty()) {
                //Path picturePath = Paths.get(".", "/public/upload/pictures/users", profileImgPath);
                Path picturePath = Paths.get(".", directory, profileImgPath);
                if (Files.exists(picturePath)) {
                    // deleting file on public folder
                    Files.delete(picturePath);
                    return true;
                }
            }
        } catch (IOException e) {
            throw new IOException("Error deleting profile picture", e);
        }
        return false;
    }
    public List<CategorySkillRepositoryModel> getCategorySkills() {

            return      categorySkillRepository.findAll();
            //return mapper.listDocumentProjectRepoToSvc(documentProjects);
    }



}




//  getById  return new PortfolioServiceResponseModel(portfolioRepositoryModel.get().getId(),
//  portfolioRepositoryModel.get().getTitle(), portfolioRepositoryModel.get().getName(),             portfolioRepositoryModel.get().getFirstname(), portfolioRepositoryModel.get().getEmail());


// findAll portfolioRepositoryModels.forEach((item)->portfolioServiceModels.add(new PortfolioServiceResponseModel(
// item.getId(), item.getTitle(), item.getName(), item.getFirstname(), item.getEmail() )));

/**  public ExperienceRepositoryModel addExperience(ExperienceServiceModel experienceServiceModel) {
 ExperienceRepositoryModel repositoryModel =
 mapper.experienceServiceToRepositoryModel(experienceServiceModel);
 // adding portfolio manually
 Optional<PortfolioRepositoryModel> portfolioRepositoryModel =
 portfolioRepository.findById( experienceServiceModel.getPortfolio().getId() );

 portfolioRepositoryModel.ifPresent(repositoryModel::setPortfolio);
 return experienceRepository.save(repositoryModel);
 } **/