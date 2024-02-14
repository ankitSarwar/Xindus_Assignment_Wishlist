package com.example.Xindus_Assignment_Ankit_Sarwar.config;

import com.example.Xindus_Assignment_Ankit_Sarwar.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UserInfoUserDetails implements UserDetails {


    private String name;
    private String password;
    private List<GrantedAuthority> authorities;

    public UserInfoUserDetails(User user) {
        name = user.getUsername();
        password = user.getPassword();
//        authorities = Collections.singletonList(
//                new SimpleGrantedAuthority(user.getRole())
//        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Assuming roles is a Set<String> field in your UserInfo class
        return authorities.stream()
                .map(role -> new SimpleGrantedAuthority( role.getAuthority().toUpperCase()))
                .collect(Collectors.toList());
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
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

