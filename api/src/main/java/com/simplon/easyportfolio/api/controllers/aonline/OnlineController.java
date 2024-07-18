package com.simplon.easyportfolio.api.controllers.aonline;

import com.simplon.easyportfolio.api.controllers.portfolios.PortfolioFullDTO;
import com.simplon.easyportfolio.api.domain.User;
import com.simplon.easyportfolio.api.exceptions.PortfolioNotFoundException;
import com.simplon.easyportfolio.api.mappers.EasyfolioMapper;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioService;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("items")
public class OnlineController {
    @Autowired
    PortfolioService portfolioService;

    private final EasyfolioMapper mapper = EasyfolioMapper.INSTANCE;

    /** getById portfolio - online template **/
    @GetMapping("/{id}")  //  GET BY ID   *****
    public ResponseEntity<PortfolioFullDTO> findByIdOnline(@PathVariable Long id){
        try{
            PortfolioServiceModel serviceModel = portfolioService.findById(id);
            User user = new User(); // we pass an empty user so as not to give sensitive information
            serviceModel.setUser(user);

            return new ResponseEntity<>( mapper.portfolioSvcToFullDTO(serviceModel), HttpStatus.OK);

            //PortfolioGetDTO DTO =  mapper.portfolioSvcToGetDTO( portfolioService.findById(id));
        }catch (PortfolioNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getReason());
        }
    }


}
