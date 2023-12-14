package com.simplon.easyportfolio.api.services.user;

import com.github.slugify.Slugify;
import com.simplon.easyportfolio.api.domain.User;
import com.simplon.easyportfolio.api.exceptions.ProjectNotFoundException;
import com.simplon.easyportfolio.api.exceptions.UserNotFoundException;
import com.simplon.easyportfolio.api.mappers.EasyfolioMapper;
import com.simplon.easyportfolio.api.repositories.projects.DocumentProjectRepositoryModel;
import com.simplon.easyportfolio.api.repositories.projects.ProjectRepositoryModel;
import com.simplon.easyportfolio.api.repositories.security.OwnerRepository;
import org.apache.commons.io.FilenameUtils;
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
import java.util.Optional;

@Service
public class UserAppService {
    @Autowired
    OwnerRepository ownerRepository;
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

    public UserServiceModel updateUserPicture(Integer id, MultipartFile file) throws UserNotFoundException  {
        try {
            Optional<User> userRepository = ownerRepository.findById(id);
            /** deleting file on folder **/
            deleteProfilePicture(userRepository.get().getProfileImgPath());
            // naming pictures : in project = project_ + projectName + (timeInMilli)
            long timestamp = System.currentTimeMillis();
            String pictureName = "profileImg-" + timestamp ;
            /** file saved on server **/
            String pictureName2 = uploadPicture(file , pictureName);
            /** filling userRepositoryModel manually **/
            userRepository.get().setProfileImgPath(pictureName2);
            /** updatedUser in db    *** User **/
            User savedUser = ownerRepository.save(userRepository.get());

           return mapper.userRepositoryToSvcModel(savedUser);

        }catch(Exception exception){
            throw new UserNotFoundException("User not found with id : " + id);
        }




    }

  /** UTILS  ***************************************** **/
    private String uploadPicture(MultipartFile file, String pictureName) throws IOError {
        try{
            String uploadDirectory = "/public/upload/pictures/users"; // pictures upload folder
            String filename = file.getOriginalFilename(); // upload file

            String extension = FilenameUtils.getExtension(filename);
            filename = slug.slugify(pictureName) + "." + extension;
            Path path = Paths.get(".", uploadDirectory).toAbsolutePath(); // absolute path
            File targetFile = new File(path.toString(), filename);
            if(!targetFile.getParentFile().exists()){
                targetFile.getParentFile().mkdirs();
            }
            file.transferTo(targetFile);
            return filename;
        }catch (IOError e){
            e.printStackTrace();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "Error uploading picture - UploadPicture userAppService";
    }

    public void deleteProfilePicture(String profileImgPath) throws Exception {
        try{
            String pictureName = profileImgPath;
            Path picturePath = Paths.get(".", "/public/upload/pictures/users", pictureName);
            System.out.println(picturePath.toString());
            if(Files.exists(picturePath)){
                // deleting file on public folder
                Files.delete(picturePath);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
