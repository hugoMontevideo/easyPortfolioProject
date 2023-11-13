package com.simplon.easyportfolio.api.repositories.users;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface UserRepository extends CrudRepository<UserRepositoryModel, Long> {

    ArrayList<UserRepositoryModel> findAll();
}
