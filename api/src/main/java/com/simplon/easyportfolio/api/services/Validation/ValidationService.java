package com.simplon.easyportfolio.api.services.Validation;
import com.simplon.easyportfolio.api.exceptions.UserNotFoundException;
import com.simplon.easyportfolio.api.exceptions.ValidationNotFoundException;
import com.simplon.easyportfolio.api.security.ValidationRepository;
import com.simplon.easyportfolio.api.security.ValidationRepositoryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;

@Service
public class ValidationService {
    @Autowired
    ValidationRepository validationRepository;


    public ValidationRepositoryModel saveCode(String email){
        Random random = new Random();
        int randomInt = random.nextInt(999999);
        ValidationRepositoryModel validationRepositoryModel = ValidationRepositoryModel.builder()
                .email(email)
                .expires(Instant.now().plus(10, ChronoUnit.MINUTES))
                .code(String.format("%06d", randomInt))
                .build();
        validationRepository.save(validationRepositoryModel);
        return validationRepositoryModel;
    }


    public Optional<ValidationRepositoryModel> findValidationByEmail(String email)  {

        Optional<ValidationRepositoryModel> validationRepositoryModel = validationRepository.findByEmail(email);
        return validationRepositoryModel;

    }


    public void delete(ValidationRepositoryModel validationRepositoryModel) {
        validationRepository.delete(validationRepositoryModel);
    }
}
