package com.simplon.easyportfolio.api.mappers;

import com.simplon.easyportfolio.api.controllers.portfolios.PortfolioDTO;
import com.simplon.easyportfolio.api.controllers.portfolios.PortfolioGetDTO;
import com.simplon.easyportfolio.api.controllers.skills.SkillGetDTO;
import com.simplon.easyportfolio.api.repositories.portfolios.PortfolioRepositoryModel;
import com.simplon.easyportfolio.api.repositories.skills.SkillRepositoryModel;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceRequestModel;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceResponseModel;
import com.simplon.easyportfolio.api.services.skills.SkillServiceResponseModel;
import jakarta.persistence.ManyToOne;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EasyfolioMapper {
    EasyfolioMapper INSTANCE = Mappers.getMapper(EasyfolioMapper.class);



    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //            DTO  ->  Service  -->  Repository
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    PortfolioServiceRequestModel portfolioDtoToServiceModel (PortfolioDTO portfolioDTO);



    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //           List<Repository>  ->  List<Service>  -->  List<GetDTO>
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //                Repository  ->  Service  -->  GetDTO
    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


    //@Mapping(target = "skills", ignore = true)
    PortfolioServiceResponseModel portfolioRepositoryToResponseSvc (PortfolioRepositoryModel portfolioRepositoryModel);

    PortfolioGetDTO portfolioSvcToGetDTO (PortfolioServiceResponseModel portfolioServiceResponseModel);


    SkillServiceResponseModel skillRepositoryToResponseSvc(SkillRepositoryModel skillRepositoryModel);

    SkillGetDTO skillSvcToGetDTO(SkillServiceResponseModel skillServiceResponseModel);
}
