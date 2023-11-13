package com.simplon.easyportfolio.api.repositories.portfolios;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface PortfolioRepository extends CrudRepository<PortfolioRepositoryModel, Long> {
   // @Override
    ArrayList<PortfolioRepositoryModel> findAll();

}
