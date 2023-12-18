package com.simplon.easyportfolio.api.services.user;

import com.simplon.easyportfolio.api.domain.Role;
import com.simplon.easyportfolio.api.repositories.portfolios.PortfolioRepositoryModel;

import com.simplon.easyportfolio.api.services.portfolios.PortfolioServiceModel;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserServiceModel {
    private int id;
    private String email;
    private  String password;
    private String name;
    private String firstname;
    private LocalDate inscriptionDate;
    private LocalDate connectionDate;
    private String profileImgPath;
    private List<PortfolioServiceModel> portfolios;

    //@OneToMany(mappedBy = "user", orphanRemoval = true)
    //private List<CvRepositoryModel> cvs = new ArrayList<>();

    private List<Role> roles;
}
