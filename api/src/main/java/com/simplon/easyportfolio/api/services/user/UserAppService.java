package com.simplon.easyportfolio.api.services.user;

import com.github.slugify.Slugify;
import com.simplon.easyportfolio.api.domain.User;
import com.simplon.easyportfolio.api.exceptions.ProjectNotFoundException;
import com.simplon.easyportfolio.api.exceptions.UserNotFoundException;
import com.simplon.easyportfolio.api.mappers.EasyfolioMapper;
import com.simplon.easyportfolio.api.repositories.security.OwnerRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

        return mapper.userToServiceModel(ownerRepository.save(user));
    }

    public UserServiceModel updateUserPicture(Integer id, MultipartFile file) throws Exception  {
        try {
            Optional<User> userRepoModel = ownerRepository.findById(id);
            /* deleting file on folder **/
            deleteProfilePicture(userRepoModel.get().getProfileImgPath());
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
