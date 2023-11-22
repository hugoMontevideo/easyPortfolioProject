package com.simplon.easyportfolio.api.services.skills;

import com.simplon.easyportfolio.api.mappers.EasyfolioMapper;
import com.simplon.easyportfolio.api.repositories.portfolios.PortfolioRepository;
import com.simplon.easyportfolio.api.repositories.portfolios.PortfolioRepositoryModel;
import com.simplon.easyportfolio.api.repositories.skills.SkillRepository;
import com.simplon.easyportfolio.api.repositories.skills.SkillRepositoryModel;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioService;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SkillService {
    @Autowired
    SkillRepository skillRepository;

    @Autowired
    PortfolioRepository portfolioRepository;


    private final EasyfolioMapper mapper = EasyfolioMapper.INSTANCE;

    public SkillServiceResponseModel findById(Long id) {

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

    public SkillRepositoryModel add(SkillServiceModel skillServiceModel) {

        SkillRepositoryModel repositoryModel = mapper.skillServiceToRepositoryModel(skillServiceModel);
        // adding portfolio manually
        Optional<PortfolioRepositoryModel> portfolioRepositoryModel =
                portfolioRepository.findById( skillServiceModel.getPortfolio().getId() );

        if( portfolioRepositoryModel.isPresent() )  {
            repositoryModel.setPortfolio(portfolioRepositoryModel.get());
        }


        return skillRepository.save(repositoryModel);

    }
}
