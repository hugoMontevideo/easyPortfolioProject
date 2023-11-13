package com.simplon.easyportfolio.api.services.users;

import com.simplon.easyportfolio.api.controllers.users.UserDTO;
import com.simplon.easyportfolio.api.repositories.users.UserRepository;
import com.simplon.easyportfolio.api.repositories.users.UserRepositoryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public boolean add(UserServiceModel userServiceModel) {
        UserRepositoryModel userRepositoryModel = new UserRepositoryModel(null, userServiceModel.getName(),
                userServiceModel.getFirstname(), userServiceModel.getEmail(), userServiceModel.getRole());
        UserRepositoryModel userRepositoryReturn = userRepository.save(userRepositoryModel);

        return userRepositoryReturn != null;
    }

    public ArrayList<UserServiceModel> findAll() {
        ArrayList<UserServiceModel> userServiceModels = new ArrayList<>();
        ArrayList<UserRepositoryModel> userRepositoryModels = userRepository.findAll();

        userRepositoryModels.forEach( (item)->userServiceModels.add( new UserServiceModel(Optional.ofNullable(item.getId()), item.getName(), item.getFirstname(), item.getEmail(), item.getRole() ) ));

        return userServiceModels;
    }

    public UserServiceModel findById(Long id) {
        Optional<UserRepositoryModel> userRepositoryModel=userRepository.findById(id);

        if(userRepositoryModel.isEmpty()){
            return null;
        } else {
            return new UserServiceModel(Optional.ofNullable(userRepositoryModel.get().getId()),
                    userRepositoryModel.get().getName(), userRepositoryModel.get().getFirstname(),
                    userRepositoryModel.get().getEmail(), userRepositoryModel.get().getRole());
        }
    }
}
