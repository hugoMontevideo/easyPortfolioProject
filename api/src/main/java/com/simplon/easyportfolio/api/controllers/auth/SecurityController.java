package com.simplon.easyportfolio.api.controllers.auth;

import com.simplon.easyportfolio.api.domain.User;
import com.simplon.easyportfolio.api.exceptions.AccountExistsException;
import com.simplon.easyportfolio.api.exceptions.UnauthorizedException;
import com.simplon.easyportfolio.api.exceptions.UserNotFoundException;
import com.simplon.easyportfolio.api.mappers.EasyfolioMapper;
import com.simplon.easyportfolio.api.services.impl.JwtUserServiceImpl;
import com.simplon.easyportfolio.api.services.jwt.JwtUserService;
import com.simplon.easyportfolio.api.services.user.UserAppService;
import com.simplon.easyportfolio.api.services.user.UserServiceModel;
import com.simplon.easyportfolio.api.services.user.UserServiceUpdateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("auth")
public class SecurityController {
    @Autowired
    private JwtUserService userService;
    @Autowired
    private UserAppService userAppService;
    private final EasyfolioMapper mapper = EasyfolioMapper.INSTANCE;

    //Remarque : ajouter un nouvel utilisateur et génère un JWT à la volée
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody AuthRequestDto Dto) throws AccountExistsException {
        // user registration
        UserDetails user = userService.save(Dto.getEmail(),
                Dto.getPassword());
        String token = userService.generateJwtForUser(user);
        // setting inscription date
        UserServiceModel userServiceModel = userAppService.findByEmail(Dto.getEmail());
        UserServiceUpdateModel updateModel = mapper.userServiceToServiceUpdate(userServiceModel);
        updateModel.setInscriptionDate(LocalDate.now());
        updateModel.setConnectionDate(LocalDate.now());
        userAppService.updateUser(updateModel);

        return ResponseEntity.ok(new AuthResponseDto(token));
    }
    @PostMapping("/authorize")
    public ResponseEntity<LoginResponseDTO> authorize(@RequestBody AuthRequestDto requestDto) throws UnauthorizedException {
        Authentication authentication = null;
        try {
            authentication = userService.authenticate(requestDto.getEmail(),
                    requestDto.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // Token generation
            UserDetails user = (UserDetails) authentication.getPrincipal();
            String token = userService.generateJwtForUser(user);
            // setting connection date
            UserServiceModel userServiceModel = userAppService.findByEmail(requestDto.getEmail());
            UserServiceUpdateModel updateModel = mapper.userServiceToServiceUpdate(userServiceModel);
            updateModel.setConnectionDate(LocalDate.now());
            userAppService.updateUser(updateModel);
            //Here, the response can be configured, user has many other properties
            return ResponseEntity.ok(new LoginResponseDTO( token ));
        } catch(AuthenticationException e) {
            throw new UnauthorizedException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //Remarque: authentifie le principal (le user) à partir du JWT.
    @GetMapping("/users/{email}")
    public ResponseEntity<UserResponseUpdateDTO> getUserByEmail(@PathVariable String email) throws ResponseStatusException {
        try {
            //Here, the response can be configured, user has many other properties
            UserServiceModel userServiceModel = userAppService.findByEmail(email);

            UserResponseUpdateDTO responseDTO =  mapper.userServiceToUpdateDTO(userServiceModel);
            return ResponseEntity.ok(responseDTO);
        } catch(ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** update user **/
    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponseUpdateDTO>updateUser(@RequestBody UserUpdateDTO DTO) throws ResponseStatusException {
        try {
            UserServiceUpdateModel serviceModel = mapper.userUpDtoToServiceUpdateModel(DTO);
            UserServiceModel updatedUser = userAppService.updateUser(serviceModel);

            UserResponseUpdateDTO responseDTO =  mapper.userServiceToUpdateDTO(updatedUser);

            return ResponseEntity.ok(responseDTO);
        } catch(ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** update profileImgPath property **/
    @PutMapping("/users/{id}/profile_picture")
    public ResponseEntity<UserDTO>updateUserPicture(@PathVariable Integer id, @RequestParam ("file") MultipartFile file) {
        try {
            UserServiceModel updatedUser = userAppService.updateUserPicture(id, file);
            UserDTO userDTO =  mapper.userServiceToDTO(updatedUser);
            return ResponseEntity.ok(userDTO);
        } catch(ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }





}


