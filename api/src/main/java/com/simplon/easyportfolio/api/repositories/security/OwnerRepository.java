package com.simplon.easyportfolio.api.repositories.security;

import com.simplon.easyportfolio.api.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface OwnerRepository extends CrudRepository<User, Integer> {

    User findByLogin(String email);
}
