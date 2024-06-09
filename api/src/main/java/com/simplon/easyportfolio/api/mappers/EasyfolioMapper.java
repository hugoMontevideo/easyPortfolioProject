package com.simplon.easyportfolio.api.mappers;

import com.simplon.easyportfolio.api.controllers.auth.UserDTO;
import com.simplon.easyportfolio.api.controllers.auth.UserResponseDTO;
import com.simplon.easyportfolio.api.controllers.auth.UserResponseUpdateDTO;
import com.simplon.easyportfolio.api.controllers.auth.UserUpdateDTO;
import com.simplon.easyportfolio.api.controllers.educations.EducationDTO;
import com.simplon.easyportfolio.api.controllers.educations.EducationGetDTO;
import com.simplon.easyportfolio.api.controllers.educations.EducationUpdateDTO;
import com.simplon.easyportfolio.api.controllers.experiences.ExperienceDTO;
import com.simplon.easyportfolio.api.controllers.experiences.ExperienceGetDTO;
import com.simplon.easyportfolio.api.controllers.experiences.ExperienceUpdateDTO;
import com.simplon.easyportfolio.api.controllers.portfolios.PortfolioDTO;
import com.simplon.easyportfolio.api.controllers.portfolios.PortfolioFullDTO;
import com.simplon.easyportfolio.api.controllers.portfolios.PortfolioGetDTO;
import com.simplon.easyportfolio.api.controllers.portfolios.PortfolioUpdateDTO;
import com.simplon.easyportfolio.api.controllers.projects.*;
import com.simplon.easyportfolio.api.controllers.skills.SkillDTO;
import com.simplon.easyportfolio.api.controllers.skills.SkillGetDTO;
import com.simplon.easyportfolio.api.controllers.skills.SkillUpdateDTO;
import com.simplon.easyportfolio.api.controllers.socials.SocialGetDTO;
import com.simplon.easyportfolio.api.controllers.socials.SocialServiceRequestModel;
import com.simplon.easyportfolio.api.controllers.socials.SocialServiceRequestUpdateModel;
import com.simplon.easyportfolio.api.controllers.socials.SocialUpdateDTO;
import com.simplon.easyportfolio.api.domain.User;
import com.simplon.easyportfolio.api.repositories.educations.EducationRepositoryModel;
import com.simplon.easyportfolio.api.repositories.experiences.ExperienceRepositoryModel;
import com.simplon.easyportfolio.api.repositories.portfolios.PortfolioRepositoryModel;
import com.simplon.easyportfolio.api.repositories.projects.DocumentProjectRepositoryModel;
import com.simplon.easyportfolio.api.repositories.projects.ProjectRepositoryModel;
import com.simplon.easyportfolio.api.repositories.skills.SkillRepositoryModel;
import com.simplon.easyportfolio.api.repositories.socials.SocialRepositoryModel;
import com.simplon.easyportfolio.api.services.educations.EducationServiceRequestModel;
import com.simplon.easyportfolio.api.services.educations.EducationServiceRequestUpdateModel;
import com.simplon.easyportfolio.api.services.educations.EducationServiceResponseModel;
import com.simplon.easyportfolio.api.services.experiences.ExperienceServiceRequestModel;
import com.simplon.easyportfolio.api.services.experiences.ExperienceServiceRequestUpdateModel;
import com.simplon.easyportfolio.api.services.experiences.ExperienceServiceResponseModel;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceModel;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceRequestModel;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceRequestUpdateModel;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceResponseModel;
import com.simplon.easyportfolio.api.services.projects.*;
import com.simplon.easyportfolio.api.services.skills.SkillServiceRequestModel;
import com.simplon.easyportfolio.api.services.skills.SkillServiceRequestUpdateModel;
import com.simplon.easyportfolio.api.services.skills.SkillServiceResponseModel;
import com.simplon.easyportfolio.api.services.socials.SocialServiceResponseModel;
import com.simplon.easyportfolio.api.services.user.UserServiceModel;
import com.simplon.easyportfolio.api.services.user.UserServiceResponseModel;
import com.simplon.easyportfolio.api.services.user.UserServiceUpdateModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Optional;

@Mapper
public interface EasyfolioMapper {
    EasyfolioMapper INSTANCE = Mappers.getMapper(EasyfolioMapper.class);

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //            DTO  ->  Service  -->  Repository
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    @Mapping(target = "user", ignore = true)
    @Mapping(source="userId", target="userId", qualifiedByName = "typeToOptional")  //update
    PortfolioServiceRequestModel portfolioDtoToServiceRequestModel (PortfolioDTO portfolioDTO);
    PortfolioServiceRequestUpdateModel portfolioDtoToServiceRequestUpdateModel(PortfolioUpdateDTO dto);
    PortfolioRepositoryModel portfolioSvcToRepositoryModel (PortfolioServiceModel portfolioServiceModel);
    @Mapping(source="id", target="id", qualifiedByName = "optionalToType") // update portfolio
    @Mapping(target = "user", ignore = true)
    PortfolioRepositoryModel portfolioServiceRequestToRepositoryModelUpdate(PortfolioServiceRequestModel portfolioServiceModel);
    @Mapping(source="id", target="id", qualifiedByName = "optionalToType") // update portfolio *** TODO delete this class
    PortfolioRepositoryModel portfolioServiceRequestUpdToRepositoryModel(PortfolioServiceRequestUpdateModel portfolioServiceRequestUpdateModel);

    // PROJECT
    @Mapping(source="date", target="date", qualifiedByName = "optionalToType")
    @Mapping(source="id", target="id", qualifiedByName = "optionalToType") // update project
    @Mapping(source="portfolio", target="portfolio", qualifiedByName = "optionalToType")
    ProjectRepositoryModel projectServiceRequestToRepositoryModel(ProjectServiceRequestUpdateModel projectServiceRequestModel);
    @Mapping(source="date", target="date", qualifiedByName = "optionalToType")    // add project
    @Mapping(source="portfolio", target="portfolio", qualifiedByName = "optionalToType")
    ProjectRepositoryModel projectServiceRequestToRepositoryModelAdd(ProjectServiceRequestModel projectServiceRequestModel);
    @Mapping(source="date", target="date", qualifiedByName = "typeToOptional")  //update
    @Mapping(source="portfolioId", target="portfolioId", qualifiedByName = "typeToOptional")
    ProjectServiceRequestUpdateModel projectDtoToServiceRequestModel(ProjectUpdateDTO dto);
    @Mapping(source="date", target="date", qualifiedByName = "typeToOptional")     // add
    @Mapping(source="portfolioId", target="portfolioId", qualifiedByName = "typeToOptional")
    ProjectServiceRequestModel projectDtoToServiceRequestModelAdd(ProjectDTO projectDTO);
    @Mapping(source="filename", target="filename", qualifiedByName = "optionalToType") //add ignore project
    @Mapping(target = "project", ignore = true)
    DocumentProjectRepositoryModel documentProjectServiceRequestToRepositoryModelAdd
   (DocumentProjectServiceRequestModel documentProjectServiceRequestModel);

    @Mapping(source="projectId", target="projectId", qualifiedByName = "typeToOptional") // add
    DocumentProjectServiceRequestModel documentProjectDtoToServiceRequestModelAdd(DocumentProjectDTO DTO);

    @Named("optionalToType")
    default <T> T optionalToType(Optional<T> source) throws Exception {
        return source.orElse(null);
    }
    @Named("typeToOptional")
    default <T> Optional<T> typeToOptional(T source) throws Exception {
        return Optional.ofNullable(source);
    }

// EDUCATION

    @Mapping(source="startDate", target="startDate", qualifiedByName = "optionalToType")
    @Mapping(source="endDate", target="endDate", qualifiedByName = "optionalToType")
    @Mapping(source="id", target="id", qualifiedByName = "optionalToType") // updateEducation
    @Mapping(source="portfolio", target="portfolio", qualifiedByName = "optionalToType")
    EducationRepositoryModel educationServiceRequestToRepositoryModel(EducationServiceRequestUpdateModel educationServiceRequesUpdatetModel);
    @Mapping(source="startDate", target="startDate", qualifiedByName = "optionalToType")    // add
    @Mapping(source="endDate", target="endDate", qualifiedByName = "optionalToType")    // add
    @Mapping(source="portfolio", target="portfolio", qualifiedByName = "optionalToType")// add
    EducationRepositoryModel educationServiceRequestToRepositoryModelAdd(EducationServiceRequestModel experienceServiceRequestModel);
    //@Mapping(source="portfolioId", target="portfolioId", qualifiedByName = "typeToOptional")//update
    //EducationServiceRequestUpdateModel educationDtoToServiceRequestModel(EducationUpdateDTO dto);
    @Mapping(source="startDate", target="startDate", qualifiedByName = "typeToOptional")  //update
    @Mapping(source="endDate", target="endDate", qualifiedByName = "typeToOptional")  //update
    @Mapping(source="portfolioId", target="portfolioId", qualifiedByName = "typeToOptional")//update
    EducationServiceRequestUpdateModel educationDtoToServiceRequestModel(EducationUpdateDTO dto);
    @Mapping(source="startDate", target="startDate", qualifiedByName = "typeToOptional")  //add
    @Mapping(source="endDate", target="endDate", qualifiedByName = "typeToOptional")  //add
    @Mapping(source="portfolioId", target="portfolioId", qualifiedByName = "typeToOptional")//add
    EducationServiceRequestModel educationDtoToServiceRequestModelAdd(EducationDTO dto);

// EXPERIENCE
    @Mapping(source="startDate", target="startDate", qualifiedByName = "optionalToType")
    @Mapping(source="endDate", target="endDate", qualifiedByName = "optionalToType")
    @Mapping(source="id", target="id", qualifiedByName = "optionalToType") // updateExp
    @Mapping(source="portfolio", target="portfolio", qualifiedByName = "optionalToType")
    ExperienceRepositoryModel experienceServiceRequestToRepositoryModel(ExperienceServiceRequestUpdateModel experienceServiceRequestModel);
    @Mapping(source="startDate", target="startDate", qualifiedByName = "optionalToType")    // add
    @Mapping(source="endDate", target="endDate", qualifiedByName = "optionalToType")    // add
    @Mapping(source="portfolio", target="portfolio", qualifiedByName = "optionalToType")// add
    ExperienceRepositoryModel experienceServiceRequestToRepositoryModelAdd(ExperienceServiceRequestModel experienceModel);
    @Mapping(source="startDate", target="startDate", qualifiedByName = "typeToOptional")  //update
    @Mapping(source="endDate", target="endDate", qualifiedByName = "typeToOptional")  //update
    @Mapping(source="portfolioId", target="portfolioId", qualifiedByName = "typeToOptional")//update
    ExperienceServiceRequestUpdateModel experienceDtoToServiceRequestModel(ExperienceUpdateDTO dto);
    @Mapping(source="startDate", target="startDate", qualifiedByName = "typeToOptional")  //add
    @Mapping(source="endDate", target="endDate", qualifiedByName = "typeToOptional")  //add
    @Mapping(source="portfolioId", target="portfolioId", qualifiedByName = "typeToOptional")//add
    ExperienceServiceRequestModel experienceDtoToServiceRequestModelAdd(ExperienceDTO dto);

// SKILL
    @Mapping(source="id", target="id", qualifiedByName = "optionalToType")// updateSkill
    @Mapping(source="portfolio", target="portfolio", qualifiedByName = "optionalToType")
    SkillRepositoryModel skillServiceRequestToRepositoryModel(SkillServiceRequestUpdateModel skillServiceModel);
    @Mapping(source="portfolio", target="portfolio", qualifiedByName = "optionalToType")// add
    SkillRepositoryModel skillServiceRequestToRepositoryModelAdd(SkillServiceRequestModel skillServiceModel);

    @Mapping(source="portfolioId", target="portfolioId", qualifiedByName = "typeToOptional")//update
    SkillServiceRequestUpdateModel skillDtoToServiceRequestModel(SkillUpdateDTO dto);

    @Mapping(source="portfolioId", target="portfolioId", qualifiedByName = "typeToOptional")// add
    SkillServiceRequestModel skillDtoToServiceRequestModelAdd(SkillDTO dto);

// SOCIAL
    @Mapping(source="portfolio", target="portfolio", qualifiedByName = "optionalToType")// add
    SocialRepositoryModel socialServiceRequestToRepositoryModelAdd(SocialServiceRequestModel serviceModel);
    @Mapping(source="portfolioId", target="portfolioId", qualifiedByName = "typeToOptional")//update
    SocialServiceRequestUpdateModel socialGetDtoToServiceRequestModel(SocialUpdateDTO dto);
    @Mapping(source="id", target="id", qualifiedByName = "optionalToType")// updateSocial
    @Mapping(source="portfolio", target="portfolio", qualifiedByName = "optionalToType")
    SocialRepositoryModel socialServiceRequestToRepositoryModel(SocialServiceRequestUpdateModel requestUpdModel);

 /**   @Mapping(source="id", target="id", qualifiedByName = "optionalToType")
    @Mapping(target = "portfolio", ignore = true)
    ExperienceServiceModel experienceDtoToServiceModel(ExperienceDTO experienceDTO);
    @Mapping(target = "portfolio", ignore = true)
    ExperienceRepositoryModel experienceServiceToRepositoryModel(ExperienceServiceModel experienceServiceModel); **/



    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //           List<Repository>  ->  List<Service>  -->  List<GetDTO>
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    List<PortfolioServiceModel> listPortolioRepositoryToSvcModel(List<PortfolioRepositoryModel> addedPortfolio);
    List<PortfolioUpdateDTO> listPortolioSvcToUpdateDTO(List<PortfolioServiceModel> addedPortfolio);
    // portfolios => descendant
    List<PortfolioGetDTO> listPortolioSvcToGetDTO(List<PortfolioServiceModel> addedPortfolio);
    List<PortfolioFullDTO> listPortfolioSvcToFullDTO(List<PortfolioServiceModel> portfolioServiceModels);

    List<ProjectServiceResponseModel> listProjectRepoToSvc (List<ProjectRepositoryModel> projectRepositoryModels);
    List<ProjectGetDTO> listProjectSvcToGetDTO (List<ProjectServiceResponseModel> projectRepositoryModels);

    List<DocumentProjectServiceResponseModel> listDocumentProjectRepoToSvc(List<DocumentProjectRepositoryModel> documentProjects);
    List<DocumentProjectGetDTO> listDocumentProjectSvcToGetDTO(List<DocumentProjectServiceResponseModel> documentProjectServices);

    List<EducationServiceResponseModel> listEducationRepoToSvc(List<EducationRepositoryModel> educations);
    List<EducationGetDTO> listEducationSvcToGetDTO(List<EducationServiceResponseModel> educationServices);

    List<ExperienceServiceResponseModel> listExperienceRepoToSvc(List<ExperienceRepositoryModel> experiences);
    List<ExperienceGetDTO> listExperienceSvcToGetDTO(List<ExperienceServiceResponseModel> experienceServices);

    List<SkillServiceResponseModel> listSkillRepoToSvc(List<SkillRepositoryModel> skills);
    List<SkillGetDTO> listSkillSvcToGetDTO(List<SkillServiceResponseModel> skillsServices);
    List<SkillGetDTO> listSkillRepoToGetDTO(List<SkillRepositoryModel> skillModels);
    List<SocialGetDTO> listSocialRepoToGetDTO(List<SocialRepositoryModel> socialModels);



    /** +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                 Repository  ->  Service  -->  GetDTO
        +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ **/
    @Mapping(target = "user.password", ignore = true) // findById
    PortfolioFullDTO portfolioSvcToFullDTO(PortfolioServiceModel portfolioSvc);
    @Mapping(target = "user.password", ignore = true) // findById
    PortfolioFullDTO portfolioSvcResponseToFullDTO(PortfolioServiceResponseModel serviceModel);
    //@Mapping(target = "skills", ignore = true)
    @Mapping(source="id", target="id", qualifiedByName = "typeToOptional")  //update
    PortfolioUpdateDTO portfolioServiceToUpdateDTO (PortfolioServiceModel serviceModel);
    PortfolioServiceResponseModel portfolioRepositoryToResponseSvc (PortfolioRepositoryModel portfolioRepositoryModel);
    PortfolioServiceModel portfolioRepositoryToServiceModel( PortfolioRepositoryModel portfolio );

    PortfolioGetDTO portfolioSvcToGetDTO (PortfolioServiceResponseModel portfolioServiceResponseModel);

    ExperienceServiceResponseModel experienceRepositoryToResponseSvc(ExperienceRepositoryModel experienceRepositoryModel);
    ExperienceGetDTO experienceSvcToGetDTO(ExperienceServiceResponseModel experienceServiceResponseModel);

    SkillServiceResponseModel skillRepositoryToResponseSvc(SkillRepositoryModel skillRepositoryModel);
    SkillGetDTO skillSvcToGetDTO(SkillServiceResponseModel skillServiceResponseModel);
    SkillGetDTO skillRepoToGetDTO(SkillRepositoryModel skillRepositoryModel);
    EducationServiceResponseModel educationRepositoryToResponseSvc(EducationRepositoryModel educationRepositoryModel);
    EducationGetDTO educationSvcToGetDTO(EducationServiceResponseModel educationServiceResponseModel);
    SocialServiceResponseModel socialRepositoryToResponseSvc(SocialRepositoryModel socialRepositoryModel);
    SocialGetDTO socialSvcToGetDTO(SocialServiceResponseModel addedSocial);


    ProjectServiceModel projectRepositoryToServiceModel (ProjectRepositoryModel projectRepositoryModel);
    ProjectServiceResponseModel projectRepositoryToResponseSvc(ProjectRepositoryModel projectRepositoryModel);
    ProjectGetDTO projectSvcToGetDTO(ProjectServiceResponseModel projectServiceResponseModel);

    DocumentProjectServiceResponseModel documentProjectRepositoryToResponseSvc(DocumentProjectRepositoryModel projectRepositoryModel);
    DocumentProjectGetDTO documentProjectSvcToGetDTO(DocumentProjectServiceResponseModel docProjectServiceResponseModel);


  /*********************************************************/

    DocumentProjectServiceModel documentProjectRepositoryToSvc (DocumentProjectRepositoryModel document);


    /****  USERS  ********************************************/

    UserDTO userServiceToDTO(UserServiceModel updatedUser);
    UserServiceUpdateModel userUpDtoToServiceUpdateModel(UserUpdateDTO dto);
    User userServiceUpdateToUser(UserServiceUpdateModel serviceModel);

    UserServiceModel userToServiceModel(User updatedUser);
    @Mapping(target = "password", ignore = true)
    UserResponseUpdateDTO userServiceToUpdateDTO(UserServiceModel userServiceModel);
    UserResponseDTO userServicResponseToFullDTO(UserServiceResponseModel serviceModel);
    UserServiceUpdateModel userServiceToServiceUpdate(UserServiceModel userServiceModel);
    UserServiceModel userRepositoryToSvcModel(User user);



}



// @Mapping(source="file", target="file", qualifiedByName = "optionalToType")