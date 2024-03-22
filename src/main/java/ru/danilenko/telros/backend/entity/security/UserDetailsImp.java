package ru.danilenko.telros.backend.entity.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.danilenko.telros.backend.entity.User;
import ru.danilenko.telros.backend.service.security.AuthInfoService;

import java.util.Collection;
import java.util.Collections;


public class UserDetailsImp implements UserDetails{

    private User user;
    private AuthInfoService authInfoService;

    public UserDetailsImp(User user, AuthInfoService authInfoService) {
        this.user = user;
        this.authInfoService = authInfoService;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getRole()));
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return authInfoService.isNonExpired(this.user.getEmail());
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

    public  User getUser(){
        return  this.user;
    }
}
