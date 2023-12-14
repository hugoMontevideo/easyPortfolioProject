package com.simplon.easyportfolio.api.domain;

import com.simplon.easyportfolio.api.repositories.cvs.CvRepositoryModel;
import com.simplon.easyportfolio.api.repositories.portfolios.PortfolioRepositoryModel;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="email", unique = true, length = 32)// security: avoid conceptual duplicates to avoid unexpected behaviors
    private String email;

    @Column(name = "password")
    private  String password;

    @Column(name = "name")
    private String name;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "inscription_date")
    private LocalDate inscriptionDate;
    @Column(name = "connection_date")
    private LocalDate connectionDate;
    @Column(name = "profile_img_path")
    private String profileImgPath;

    @OneToMany(mappedBy = "user")
    private List<PortfolioRepositoryModel> portfolios = new ArrayList<>();

    //@OneToMany(mappedBy = "user", orphanRemoval = true)
    //private List<CvRepositoryModel> cvs = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    // Implementing methods for security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
        //return (Collection<? extends GrantedAuthority>) roles;  // ***** TODO ** revoir cette ligne
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }




}
