package com.simplon.easyportfolio.api.controllers.portfolios;


import com.simplon.easyportfolio.api.exceptions.PortfolioNotFoundException;
import com.simplon.easyportfolio.api.mappers.EasyfolioMapper;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioService;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceRequestModel;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("api/portfolios")
public class PortfolioController {
    @Autowired
    PortfolioService portfolioService;

    private final EasyfolioMapper mapper = EasyfolioMapper.INSTANCE;

    @PostMapping
    public boolean add(@RequestBody PortfolioDTO portfolioDTO){
        return portfolioService.add(mapper.portfolioDtoToServiceModel(portfolioDTO));
    }

    @GetMapping("/{id}")  //  GET BY ID   *****
    public ResponseEntity<PortfolioGetDTO> findById(@PathVariable Long id){
        try{
           PortfolioServiceResponseModel responseModel =  portfolioService.findById(id);
            System.out.println(responseModel.getSkills());

           PortfolioGetDTO DTO =  mapper.portfolioSvcToGetDTO( responseModel);
            System.out.println(DTO.getSkills());
            // return new ResponseEntity<>( mapper.portfolioSvcToGetDTO(portfolioService.findById(id)), HttpStatus.OK);
            return new ResponseEntity<>( DTO, HttpStatus.OK);
        }catch (PortfolioNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getReason());
        }
    }

    @GetMapping     //  FIND ALL
    public ArrayList<PortfolioGetDTO> findAll(){
        ArrayList<PortfolioGetDTO> portfolioGetDTOs = new ArrayList<>();
        ArrayList<PortfolioServiceResponseModel> portfolioServiceModels = portfolioService.findAll();

        portfolioServiceModels.forEach((PortfolioServiceResponseModel item)->portfolioGetDTOs.add( mapper.portfolioSvcToGetDTO(item) ));

        return portfolioGetDTOs;
    }

    @PutMapping("/{id}")  //  UPDATE
    public ResponseEntity<String> updateFolio(@PathVariable("id") Long id,
                                              @RequestParam PortfolioGetDTO portfolioGetDTO){
        if(portfolioService.findById(id) != null){
            portfolioService.update(new PortfolioServiceRequestModel( Optional.ofNullable(id), portfolioGetDTO.getTitle(),
                    portfolioGetDTO.getName(),
                    portfolioGetDTO.getFirstname(), portfolioGetDTO.getEmail()));
            return ResponseEntity.ok("Le portfolio id: "+ id +" a été modifié");
        }else{
           throw new PortfolioNotFoundException(HttpStatus.NOT_FOUND, "Le folio n'a pas été trouvé.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        if(portfolioService.findById(id) != null){
            portfolioService.delete(id);
            return new ResponseEntity<>("Le portfolio id : "+ id +" a été supprimé.", HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>("Le portfolio id : "+ id +" n'a pas été trouvé.", HttpStatus.NOT_FOUND);
        }
    }



}

//  add  PortfolioServiceModel portfolioServiceModel = new PortfolioServiceModel( portfolioDTO.getTitle(),portfolioDTO
//  .getName(), portfolioDTO.getFirstname(), portfolioDTO.getEmail());
//PortfolioServiceModel portfolioServiceModel  = mapper.portfolioDtoToServiceModel(portfolioDTO);

// PortfolioServiceResponseModel portfolioServiceResponseModel=portfolioService.findById(id);

// byId    PortfolioGetDTO portfolioGetDTO=mapper.portfolioSvcToGetDTO(portfolioServiceResponseModel); ****
// PortfolioGetDTO portfolioGetDTO= new PortfolioGetDTO(portfolioServiceModel.getId().get(),
//       portfolioServiceModel.getTitle(), portfolioServiceModel.getName(),
//        portfolioServiceModel.getFirstname(), portfolioServiceModel.getEmail());
