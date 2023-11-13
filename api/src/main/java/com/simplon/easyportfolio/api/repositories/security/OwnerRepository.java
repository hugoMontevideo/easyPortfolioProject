package com.simplon.easyportfolio.api.repositories.security;

import com.simplon.easyportfolio.api.domain.Owner;
import org.springframework.data.repository.CrudRepository;

public interface OwnerRepository extends CrudRepository<Owner, Integer> {

    Owner findByLogin(String login);
}
