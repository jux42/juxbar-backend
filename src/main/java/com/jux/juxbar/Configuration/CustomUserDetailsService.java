package com.jux.juxbar.Configuration;

import com.jux.juxbar.Model.JuxBarUser;
import com.jux.juxbar.Service.JuxBarUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    JuxBarUserService juxBarUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        JuxBarUser juxBarUser = juxBarUserService.getJuxBarUserByUsername(username);
        return new User(juxBarUser.getUsername(), juxBarUser.getPassword(), getAuthorities(juxBarUser.getRole()) );
    }

    private List<GrantedAuthority> getAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return authorities;
    }
}
