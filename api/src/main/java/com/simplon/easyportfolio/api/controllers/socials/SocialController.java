package com.simplon.easyportfolio.api.controllers.socials;

import com.simplon.easyportfolio.api.controllers.skills.SkillGetDTO;
import com.simplon.easyportfolio.api.controllers.skills.SkillUpdateDTO;
import com.simplon.easyportfolio.api.mappers.EasyfolioMapper;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioService;
import com.simplon.easyportfolio.api.services.skills.SkillServiceRequestUpdateModel;
import com.simplon.easyportfolio.api.services.skills.SkillServiceResponseModel;
import com.simplon.easyportfolio.api.services.socials.SocialServiceResponseModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/socials")
public class SocialController {
    @Autowired
    PortfolioService portfolioService;
    private final EasyfolioMapper mapper = EasyfolioMapper.INSTANCE;

    /** add Social */
    @PostMapping
    public SocialGetDTO add(@RequestBody @Valid SocialAddDTO DTO){
        SocialServiceRequestModel serviceModel = new SocialServiceRequestModel(DTO.getLink(), DTO.getCategorySocialId(), DTO.getPortfolioId());

        SocialServiceResponseModel addedSocial = portfolioService.saveSocial( serviceModel );
        return mapper.socialSvcToGetDTO(addedSocial);
    }

    /** update Social */
    @PutMapping("/{id}")
    public SocialGetDTO update(@RequestBody @Valid SocialUpdateDTO DTO){
        //DTO.setId(Optional.of(1L));

        SocialServiceRequestUpdateModel requestUpdModel = mapper.socialGetDtoToServiceRequestModel(DTO);

        SocialServiceResponseModel updatedSocial = portfolioService.updateSocial( requestUpdModel );
        return mapper.socialSvcToGetDTO(updatedSocial);
    }




}
