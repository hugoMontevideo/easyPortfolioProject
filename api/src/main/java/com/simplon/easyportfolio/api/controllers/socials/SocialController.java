package com.simplon.easyportfolio.api.controllers.socials;

import com.simplon.easyportfolio.api.mappers.EasyfolioMapper;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioService;
import com.simplon.easyportfolio.api.services.socials.SocialServiceResponseModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api/socials")
public class SocialController {
    @Autowired
    PortfolioService portfolioService;
    private final EasyfolioMapper mapper = EasyfolioMapper.INSTANCE;
    // addSkill

    @PostMapping
    public SocialGetDTO add(@RequestBody @Valid SocialAddDTO DTO){
        SocialServiceRequestModel serviceModel = new SocialServiceRequestModel(DTO.getLink(), DTO.getCategorySocialId(), Optional.of( DTO.getPortfolioId()));

        SocialServiceResponseModel addedSocial = portfolioService.saveSocial( serviceModel );
        return mapper.socialSvcToGetDTO(addedSocial);
    }




}
