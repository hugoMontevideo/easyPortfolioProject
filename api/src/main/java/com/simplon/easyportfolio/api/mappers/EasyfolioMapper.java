package com.simplon.easyportfolio.api.mappers;

import com.simplon.easyportfolio.api.controllers.educations.EducationDTO;
import com.simplon.easyportfolio.api.controllers.educations.EducationGetDTO;
import com.simplon.easyportfolio.api.controllers.experiences.ExperienceDTO;
import com.simplon.easyportfolio.api.controllers.experiences.ExperienceGetDTO;
import com.simplon.easyportfolio.api.controllers.portfolios.PortfolioDTO;
import com.simplon.easyportfolio.api.controllers.portfolios.PortfolioGetDTO;
import com.simplon.easyportfolio.api.controllers.projects.ProjectDTO;
import com.simplon.easyportfolio.api.controllers.projects.ProjectGetDTO;
import com.simplon.easyportfolio.api.controllers.skills.SkillDTO;
import com.simplon.easyportfolio.api.controllers.skills.SkillGetDTO;
import com.simplon.easyportfolio.api.repositories.educations.EducationRepositoryModel;
import com.simplon.easyportfolio.api.repositories.experiences.ExperienceRepositoryModel;
import com.simplon.easyportfolio.api.repositories.portfolios.PortfolioRepositoryModel;
import com.simplon.easyportfolio.api.repositories.projects.ProjectRepositoryModel;
import com.simplon.easyportfolio.api.repositories.skills.SkillRepositoryModel;
import com.simplon.easyportfolio.api.services.educations.EducationServiceModel;
import com.simplon.easyportfolio.api.services.educations.EducationServiceRequestModel;
import com.simplon.easyportfolio.api.services.educations.EducationServiceResponseModel;
import com.simplon.easyportfolio.api.services.experiences.ExperienceServiceModel;
import com.simplon.easyportfolio.api.services.experiences.ExperienceServiceResponseModel;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceModel;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceRequestModel;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceResponseModel;
import com.simplon.easyportfolio.api.services.projects.ProjectServiceModel;
import com.simplon.easyportfolio.api.services.projects.ProjectServiceRequestModel;
import com.simplon.easyportfolio.api.services.projects.ProjectServiceResponseModel;
import com.simplon.easyportfolio.api.services.skills.SkillServiceModel;
import com.simplon.easyportfolio.api.services.skills.SkillServiceRequestModel;
import com.simplon.easyportfolio.api.services.skills.SkillServiceResponseModel;
import jakarta.persistence.ManyToOne;
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

    PortfolioServiceRequestModel portfolioDtoToServiceModel (PortfolioDTO portfolioDTO);
    PortfolioRepositoryModel portfolioSvcToRepositoryModel (PortfolioServiceModel portfolioServiceModel);

    @Mapping(source="portfolio", target="portfolio", qualifiedByName = "optionalToType")
    ProjectRepositoryModel projectServiceRequestToRepositoryModel(ProjectServiceRequestModel projectServiceRequestModel);
    @Mapping(source="portfolioId", target="portfolioId", qualifiedByName = "typeToOptional")
    //@Mapping(target = "portfolio", ignore = true)
    ProjectServiceRequestModel projectDtoToServiceRequestModel(ProjectDTO projectDTO);


    @Named("optionalToType")
    default <T> T optionalToType(Optional<T> source) throws Exception {
        return source.orElse(null);
    }
    @Named("typeToOptional")
    default <T> Optional<T> typeToOptional(T source) throws Exception {
        return Optional.ofNullable(source);
    }


    EducationServiceRequestModel educationDtoToServiceRequestModel(EducationDTO educationDTO);
    EducationRepositoryModel educationServiceToRepositoryModel(EducationServiceModel educationServiceModel);

    @Mapping(target = "portfolio", ignore = true)
    SkillServiceModel skillDtoToServiceModel(SkillDTO skillDTO);
    @Mapping(target = "portfolio", ignore = true)
    SkillRepositoryModel skillServiceToRepositoryModel(SkillServiceModel skillServiceModel);

    @Mapping(target = "portfolio", ignore = true)
    ExperienceServiceModel experienceDtoToServiceModel(ExperienceDTO experienceDTO);
    @Mapping(target = "portfolio", ignore = true)
    ExperienceRepositoryModel experienceServiceToRepositoryModel(ExperienceServiceModel experienceServiceModel);



    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //           List<Repository>  ->  List<Service>  -->  List<GetDTO>
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    //List<SkillServiceResponseModel> listSkillRepoToSvc (List<SkillRepositoryModel> skillRepositoryModels);

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //                Repository  ->  Service  -->  GetDTO
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


    //@Mapping(target = "skills", ignore = true)
    PortfolioServiceResponseModel portfolioRepositoryToResponseSvc (PortfolioRepositoryModel portfolioRepositoryModel);
    PortfolioServiceModel portfolioRepositoryToServiceModel( PortfolioRepositoryModel portfolio );

    PortfolioGetDTO portfolioSvcToGetDTO (PortfolioServiceResponseModel portfolioServiceResponseModel);

    ExperienceServiceResponseModel experienceRepositoryToResponseSvc(ExperienceRepositoryModel experienceRepositoryModel);
    ExperienceGetDTO experienceSvcToGetDTO(ExperienceServiceResponseModel experienceServiceResponseModel);

    SkillServiceResponseModel skillRepositoryToResponseSvc(SkillRepositoryModel skillRepositoryModel);
    SkillGetDTO skillSvcToGetDTO(SkillServiceResponseModel skillServiceResponseModel);

    EducationServiceResponseModel educationRepositoryToResponseSvc(EducationRepositoryModel educationRepositoryModel);
    EducationGetDTO educationSvcToGetDTO(EducationServiceResponseModel educationServiceResponseModel);


    ProjectGetDTO projectSvcToGetDTO(ProjectServiceResponseModel educationServiceResponseModel);



}
