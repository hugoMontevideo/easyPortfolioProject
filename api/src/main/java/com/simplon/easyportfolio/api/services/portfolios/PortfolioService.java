package com.simplon.easyportfolio.api.services.portfolios;

import com.simplon.easyportfolio.api.mappers.EasyfolioMapper;
import com.simplon.easyportfolio.api.repositories.portfolios.PortfolioRepository;
import com.simplon.easyportfolio.api.repositories.portfolios.PortfolioRepositoryModel;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sound.sampled.Port;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class PortfolioService {

    @Autowired
    PortfolioRepository portfolioRepository;

    private final EasyfolioMapper mapper = EasyfolioMapper.INSTANCE;

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
        System.out.println(portfolioRepositoryModel.get().getSkills());
        if (portfolioRepositoryModel.isEmpty()){
            return null;
        }else{
            PortfolioServiceResponseModel responseModel = mapper.portfolioRepositoryToResponseSvc(portfolioRepositoryModel.get());
            // insertion de l'objet

            return responseModel ;
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
        System.out.println(portfolioRepositoryModels.get(0));

        portfolioRepositoryModels.forEach((PortfolioRepositoryModel item)->portfolioServiceModels.add( mapper.portfolioRepositoryToResponseSvc(item) ));

        // todo *****  renseigner la arrayList skills

        return portfolioServiceModels;
    }

    public void delete(Long id) {
        portfolioRepository.deleteById(id);
    }
}




//  getById  return new PortfolioServiceResponseModel(portfolioRepositoryModel.get().getId(),
//  portfolioRepositoryModel.get().getTitle(), portfolioRepositoryModel.get().getName(),             portfolioRepositoryModel.get().getFirstname(), portfolioRepositoryModel.get().getEmail());


// findAll portfolioRepositoryModels.forEach((item)->portfolioServiceModels.add(new PortfolioServiceResponseModel(
// item.getId(), item.getTitle(), item.getName(), item.getFirstname(), item.getEmail() )));