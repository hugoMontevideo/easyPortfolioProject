package com.simplon.easyportfolio.api.repositories.skills;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface CategorySkillRepository extends CrudRepository<CategorySkillRepositoryModel, Long> {

    List<CategorySkillRepositoryModel> findAll();
}
