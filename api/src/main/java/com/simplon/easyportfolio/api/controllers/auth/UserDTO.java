package com.simplon.easyportfolio.api.controllers.auth;

import com.simplon.easyportfolio.api.controllers.portfolios.PortfolioUpdateDTO;
import com.simplon.easyportfolio.api.domain.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private int id;
    private String email;
    private String password;
    private String name;
    private String firstname;
    private LocalDate inscriptionDate;
    private LocalDate connectionDate;
    private String profileImgPath;

    private List<PortfolioUpdateDTO> portfolios;

    //@OneToMany(mappedBy = "user", orphanRemoval = true)
    //private List<CvRepositoryModel> cvs = new ArrayList<>();

    private List<Role> roles;
}
