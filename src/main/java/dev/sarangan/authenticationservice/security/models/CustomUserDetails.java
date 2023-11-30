package dev.sarangan.authenticationservice.security.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.sarangan.authenticationservice.models.Role;
import dev.sarangan.authenticationservice.models.User;
import dev.sarangan.authenticationservice.security.services.CustomUserDetailsService;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@JsonDeserialize
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {
    //    private User user;
    private String username;
    private String password;
    private List<GrantedAuthority> authorities;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;


    public CustomUserDetails(User user) {
        authorities = new ArrayList<>();
        for (Role role : user.getRoles()) {
            authorities.add(new CustomGrantedAuthority(role));
        }

        this.password = user.getPassword();
        this.username = user.getEmail();
        this.isAccountNonExpired = true;
        this.isAccountNonLocked = true;
        this.isCredentialsNonExpired = true;
        this.isEnabled = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        /*List<CustomGrantedAuthority> customGrantedAuthorities = new ArrayList<>();
        for(Role role: user.getRoles()){
            customGrantedAuthorities.add(new CustomGrantedAuthority(role));
        }*/
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
//        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.username;
//        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {

        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
