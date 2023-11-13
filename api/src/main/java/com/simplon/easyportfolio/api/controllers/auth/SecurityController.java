package com.simplon.easyportfolio.api.controllers.auth;

import com.simplon.easyportfolio.api.exceptions.AccountExistsException;
import com.simplon.easyportfolio.api.exceptions.UnauthorizedException;
import com.simplon.easyportfolio.api.services.jwt.JwtUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("auth")
public class SecurityController {
    @Autowired
    private JwtUserService userService;

    //Remarque : ajouter un nouvel utilisateur et génère un JWT à la volée
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody AuthRequestDto dto) throws AccountExistsException {
        UserDetails user = userService.save(dto.getUsername(),
                dto.getPassword());
        String token = userService.generateJwtForUser(user);
        return ResponseEntity.ok(new AuthResponseDto(user,token));
    }

    @PostMapping("/authorize")
    public ResponseEntity<AuthResponseDto> authorize(@RequestBody AuthRequestDto requestDto) throws UnauthorizedException {
        Authentication authentication = null;
        try {
            System.out.println(requestDto);
            authentication = userService.authenticate(requestDto.getUsername(),
                    requestDto.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // Token generation
            UserDetails user = (UserDetails) authentication.getPrincipal();
            String token = userService.generateJwtForUser(user);
            return ResponseEntity.ok(new AuthResponseDto(user, token));
        } catch(AuthenticationException e) {
            throw new UnauthorizedException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //Remarque: authentifie le principal (le user) à partir du JWT.
}


