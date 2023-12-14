package com.simplon.easyportfolio.api.services.user;

import com.simplon.easyportfolio.api.domain.User;
import com.simplon.easyportfolio.api.mappers.EasyfolioMapper;
import com.simplon.easyportfolio.api.repositories.security.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAppService {
    @Autowired
    OwnerRepository ownerRepository;
    private final EasyfolioMapper mapper = EasyfolioMapper.INSTANCE;

    public UserServiceModel findByEmail(String email) {
        User user = ownerRepository.findByEmail(email);

        UserServiceModel userModel = new UserServiceModel(
                user.getId(), user.getEmail(),"", user.getName(), user.getFirstname(), user.getInscriptionDate(), user.getConnectionDate(), user.getProfileImgPath(), null, null
        );

        return userModel;
    }

    public UserServiceModel updateUser(UserServiceUpdateModel serviceModel) {

        User userByEmail = ownerRepository.findByEmail(serviceModel.getEmail());
        serviceModel.setPassword(userByEmail.getPassword());
        User user = mapper.userServiceUpdateToUser( serviceModel);

       // User updatedUser = ownerRepository.save(user);
        return mapper.userToServiceModel(ownerRepository.save(user));
    }
}
