package com.simplon.easyportfolio.api.security;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ValidationRepository extends CrudRepository<ValidationRepositoryModel, Long> {
    Optional<ValidationRepositoryModel> findByEmail(String email);


}
