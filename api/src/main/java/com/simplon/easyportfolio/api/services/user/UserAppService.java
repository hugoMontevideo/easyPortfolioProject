package com.simplon.easyportfolio.api.services.user;

import com.github.slugify.Slugify;
import com.simplon.easyportfolio.api.controllers.auth.UserUpdatePasswordDTO;
import com.simplon.easyportfolio.api.domain.User;
import com.simplon.easyportfolio.api.email.EmailService;
import com.simplon.easyportfolio.api.exceptions.ProjectNotFoundException;
import com.simplon.easyportfolio.api.exceptions.UserNotFoundException;
import com.simplon.easyportfolio.api.mappers.EasyfolioMapper;
import com.simplon.easyportfolio.api.repositories.portfolios.PortfolioRepositoryModel;
import com.simplon.easyportfolio.api.repositories.security.OwnerRepository;
import com.simplon.easyportfolio.api.security.ValidationRepositoryModel;
import com.simplon.easyportfolio.api.security.ValidationResponseDTO;
import com.simplon.easyportfolio.api.security.VerifyCodeDTO;
import com.simplon.easyportfolio.api.services.Validation.ValidationService;
import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceModel;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserAppService {
    @Autowired
    OwnerRepository ownerRepository;
    @Autowired
    ValidationService validationService;
    @Autowired
    EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final EasyfolioMapper mapper = EasyfolioMapper.INSTANCE;
    private final Slugify slug = Slugify.builder().build();

    public UserServiceModel findById(Integer id) throws UserNotFoundException {
        try {
            Optional<User> userRepository = ownerRepository.findById(id);
            return mapper.userRepositoryToSvcModel(userRepository.get());
        }catch(Exception exception){
            throw new UserNotFoundException("User not found with id : " + id);
        }
    }
    public List<PortfolioServiceModel> getPortfoliosByUserEmail(String email) {
        User user = ownerRepository.findByEmail(email);

        List<PortfolioRepositoryModel> portfolioRepositoryModels = user.getPortfolios();
        return mapper.listPortolioRepositoryToSvcModel(portfolioRepositoryModels);
    }

   // change password
    public ValidationResponseDTO requestUserByEmail(String email) throws UserNotFoundException {
        try {
            User user = ownerRepository.findByEmail(email);
            System.out.println(user);
            if(user != null ){
                Optional<ValidationRepositoryModel> validationModel = validationService.findValidationByEmail(email);
                validationModel.ifPresent(validationRepositoryModel -> validationService.delete(validationRepositoryModel));

                ValidationRepositoryModel validation = validationService.saveCode(email);
                ValidationResponseDTO DTO = ValidationResponseDTO.builder()
                        .expires(validation.getExpires())
                        .email(validation.getEmail())
                        .build();
                // envoi d'un email mailtrap en dev
                // todo faire un template de mail
                emailService.sendEmail(
                        "test@example.com",
                        "Code pour renouvellement du mot de passe",
                        "Le code pour renouveler le mot de passe est "+validation.getCode());
                return DTO;
            }else{
                throw new UserNotFoundException("");
            }

        }catch(Exception exception){
            throw new UserNotFoundException("Il n'y a pas d'utilisateur avec cet email : " + email);
        }
    }

    public VerifyCodeDTO verifyCodePassword(UserUpdatePasswordDTO passwordDTO) {
        VerifyCodeDTO verifyCodeDTO = VerifyCodeDTO.builder()
                .code(false)
                .expires(true)
                .build();
        // todo gerer le cas ou on ne trouve pas validation by email
        Optional<ValidationRepositoryModel> validationModel = validationService.findValidationByEmail(passwordDTO.getEmail());
        if (validationModel.isPresent()){
            if ( validationModel.get().getExpires().isAfter(Instant.now()) ) {
                verifyCodeDTO.setExpires(false);
                if( Objects.equals(passwordDTO.getCode(), validationModel.get().getCode()) ){
                    verifyCodeDTO.setCode(true);
                }
            }
        }
        return verifyCodeDTO;
    }

    /** update password's user (using email) **/
    public UserServiceModel updatePassword(UserUpdatePasswordDTO dto) throws UserNotFoundException {
        try {
            User userByEmail = ownerRepository.findByEmail(dto.getEmail());
            if(userByEmail != null ){
                String encodedPassword = passwordEncoder.encode(dto.getPassword());
                userByEmail.setPassword(encodedPassword);
                User user = ownerRepository.save(userByEmail);

                return mapper.userToServiceModel(user);
            }else{
                throw new UserNotFoundException("");
            }

        }catch(Exception exception){
            throw new UserNotFoundException("Il n'y a pas d'utilisateur avec cet email : " + dto.getEmail());
        }
    }


    public UserServiceModel findByEmail(String email) {
        User user = ownerRepository.findByEmail(email);

       UserServiceModel userModel = new UserServiceModel(
                user.getId(), user.getEmail(),"", user.getName(), user.getFirstname(), user.getInscriptionDate(), user.getConnectionDate(), user.getProfileImgPath(), null, null
        );
        return userModel;
    }
    // todo verifier cette methode : possible probleme de mapping
    public UserServiceModel updateUser(@NotNull UserServiceUpdateModel serviceModel) {
        User userByEmail = ownerRepository.findByEmail(serviceModel.getEmail());
        serviceModel.setPassword(userByEmail.getPassword());
        User user = mapper.userServiceUpdateToUser( serviceModel);

        return mapper.userToServiceModel(ownerRepository.save(user));
    }



    public UserServiceModel updateUserPicture(Integer id, MultipartFile file) throws Exception  {
        try {
            Optional<User> userRepoModel = ownerRepository.findById(id);
            /* deleting file on folder **/
            if (!StringUtils.isBlank(userRepoModel.get().getProfileImgPath())){
                deleteProfilePicture(userRepoModel.get().getProfileImgPath());
            };
           // System.out.println(userRepoModel.get().getProfileImgPath()+"ùùù");
           // deleteProfilePicture(userRepoModel.get().getProfileImgPath());
            /* file saved on server **/
            String pictureName2 = uploadPicture(file);
            /* filling userRepositoryModel manually **/
            userRepoModel.get().setProfileImgPath(pictureName2);
            /* updatedUser in db    *** User **/
            User savedUser = ownerRepository.save(userRepoModel.get());

            return mapper.userToServiceModel(savedUser);
        }catch (Exception e){
           throw new Exception() ;
        }

    }

  /** UTILS  ***************************************** **/
    private String uploadPicture(MultipartFile file) throws IOError, IOException {
        try{
            String uploadDirectory = "/public/upload/pictures/users"; // pictures upload folder
            // naming pictures  = profile-img- + projectName + (timeInMilli)
            long timestamp = System.currentTimeMillis();
            String pictureName = "profile-img-" + timestamp ;
            String filename = file.getOriginalFilename(); // upload file

            String extension = FilenameUtils.getExtension(filename);
            filename = slug.slugify(pictureName) + "." + extension;

            Path path = Paths.get(".", uploadDirectory).toAbsolutePath(); // absolute path
            File targetFile = new File(path.toString(), filename);
            /* creating folder if doesn't exists **/
            if(!targetFile.getParentFile().exists()){
                targetFile.getParentFile().mkdirs();
            }
            file.transferTo(targetFile);

            return filename;
        } catch (IOException e) {
            System.out.println("error*** ");
            throw new IOException("Error uploading picture - UploadPicture userAppService", e);

   /**     } catch (IOException e) {
            throw new RuntimeException(e);*/
        }
        //return "Error uploading picture - UploadPicture userAppService";
    }

    public boolean deleteProfilePicture(String profileImgPath) throws IOException {
        try{
            String pictureName = profileImgPath;
            Path picturePath = Paths.get(".", "/public/upload/pictures/users", pictureName);
            if(Files.exists(picturePath)){
                // deleting file on public folder
                Files.delete(picturePath);
                return true;
            }
        } catch (IOException e) {
            throw new IOException("Error deleting profile picture", e);
        }
        return false;
    }



}
