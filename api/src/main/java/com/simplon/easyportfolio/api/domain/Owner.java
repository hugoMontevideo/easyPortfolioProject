package com.simplon.easyportfolio.api.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Setter
@Getter
public class Owner implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, length = 32)// security: avoid conceptual duplicates to avoid unexpected behaviors
    private String login;

    private  String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    // Implementing methods for security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
        //return (Collection<? extends GrantedAuthority>) roles;  // ***** todo ** revoir cette ligne
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return login;
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
