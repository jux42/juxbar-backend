package com.jux.juxbar.Configuration;

import com.jux.juxbar.Model.JuxBarUser;
import com.jux.juxbar.Service.JuxBarUserService;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    JuxBarUserService juxBarUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        JuxBarUser juxBarUser = juxBarUserService.getJuxBarUserByUsername(username);
        if (juxBarUser == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        log.info("User found: {} with role: {}", username, juxBarUser.getRole());
        return new User(juxBarUser.getUsername(), juxBarUser.getPassword(), this.getAuthorities(juxBarUser.getRole()) );

    }

    private List<GrantedAuthority> getAuthorities(String role) {
        log.info("in the getAuthorities with role : {}", role);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }
}
