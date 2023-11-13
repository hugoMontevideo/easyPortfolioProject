package com.simplon.easyportfolio.api.services.skills;

import com.simplon.easyportfolio.api.mappers.EasyfolioMapper;
import com.simplon.easyportfolio.api.repositories.skills.SkillRepository;
import com.simplon.easyportfolio.api.repositories.skills.SkillRepositoryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SkillService {
    @Autowired
    SkillRepository skillRepository;
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
}
