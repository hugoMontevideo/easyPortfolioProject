package com.simplon.easyportfolio.api.controllers.portfolios;

import com.simplon.easyportfolio.api.exceptions.PortfolioNotFoundException;
import com.simplon.easyportfolio.api.mappers.EasyfolioMapper;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioService;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceRequestModel;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.Optional;
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
            return new ResponseEntity<>( mapper.portfolioSvcToGetDTO(portfolioService.findById(id)), HttpStatus.OK);

           //PortfolioGetDTO DTO =  mapper.portfolioSvcToGetDTO( portfolioService.findById(id));
            //return new ResponseEntity<>( DTO, HttpStatus.OK);
        }catch (PortfolioNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getReason());
        }
    }

    @GetMapping     //  GET ALL
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

    try {
            portfolioService.delete(id);
            return ResponseEntity.noContent().build(); // Statut 204 No Content
        } catch(
            DataAccessException e) {
            return ResponseEntity.status(502).build(); // Statut
        } catch(Exception e){
            return ResponseEntity.notFound().build(); // Statut 404 Not Found
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
