package com.simplon.easyportfolio.api.mappers;

import com.simplon.easyportfolio.api.controllers.educations.EducationGetDTO;
import com.simplon.easyportfolio.api.controllers.portfolios.PortfolioDTO;
import com.simplon.easyportfolio.api.controllers.portfolios.PortfolioGetDTO;
import com.simplon.easyportfolio.api.controllers.skills.SkillDTO;
import com.simplon.easyportfolio.api.controllers.skills.SkillGetDTO;
import com.simplon.easyportfolio.api.repositories.educations.EducationRepositoryModel;
import com.simplon.easyportfolio.api.repositories.portfolios.PortfolioRepositoryModel;
import com.simplon.easyportfolio.api.repositories.skills.SkillRepositoryModel;
import com.simplon.easyportfolio.api.services.educations.EducationServiceResponseModel;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceModel;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceRequestModel;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceResponseModel;
import com.simplon.easyportfolio.api.services.skills.SkillServiceModel;
import com.simplon.easyportfolio.api.services.skills.SkillServiceRequestModel;
import com.simplon.easyportfolio.api.services.skills.SkillServiceResponseModel;
import jakarta.persistence.ManyToOne;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface EasyfolioMapper {
    EasyfolioMapper INSTANCE = Mappers.getMapper(EasyfolioMapper.class);



    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //            DTO  ->  Service  -->  Repository
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    PortfolioServiceRequestModel portfolioDtoToServiceModel (PortfolioDTO portfolioDTO);
    PortfolioRepositoryModel portfolioSvcToRepositoryModel (PortfolioServiceModel portfolioServiceModel);

    @Mapping(target = "portfolio", ignore = true)
    SkillServiceModel skillDtoToServiceModel(SkillDTO skillDTO);
    @Mapping(target = "portfolio", ignore = true)
    SkillRepositoryModel skillServiceToRepositoryModel(SkillServiceModel skillServiceModel);


    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //           List<Repository>  ->  List<Service>  -->  List<GetDTO>
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    //List<SkillServiceResponseModel> listSkillRepoToSvc (List<SkillRepositoryModel> skillRepositoryModels);

    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //                Repository  ->  Service  -->  GetDTO
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


    //@Mapping(target = "skills", ignore = true)
    PortfolioServiceResponseModel portfolioRepositoryToResponseSvc (PortfolioRepositoryModel portfolioRepositoryModel);

    PortfolioGetDTO portfolioSvcToGetDTO (PortfolioServiceResponseModel portfolioServiceResponseModel);


    SkillServiceResponseModel skillRepositoryToResponseSvc(SkillRepositoryModel skillRepositoryModel);
    SkillGetDTO skillSvcToGetDTO(SkillServiceResponseModel skillServiceResponseModel);

    EducationServiceResponseModel educationRepositoryToResponseSvc(EducationRepositoryModel educationRepositoryModel);
    EducationGetDTO educationSvcToGetDTO(EducationServiceResponseModel educationServiceResponseModel);



}
