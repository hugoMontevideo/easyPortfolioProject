package com.simplon.easyportfolio.api.repositories.portfolios;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface CategoryPortfolioRepository extends CrudRepository<CategoryPortfolioRepositoryModel, Long> {

    List<CategoryPortfolioRepositoryModel> findAll();

}
