package com.jux.juxbar.configuration;

import com.jux.juxbar.model.Cocktail;
import com.jux.juxbar.model.JuxBarUser;
import com.jux.juxbar.model.SoftDrink;
import com.jux.juxbar.service.JuxBarUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        return new User(juxBarUser.getUsername(), juxBarUser.getPassword(), this.getAuthorities(juxBarUser.getRole()));

    }


    public void createUser(String username, String password) {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        JuxBarUser juxBarUser = new JuxBarUser();
        juxBarUser.setUsername(username);
        juxBarUser.setPassword(bCryptPasswordEncoder.encode(password));
        juxBarUser.setRole("USER");
        juxBarUser.setFavourite_softdrinks(new ArrayList<SoftDrink>());
        juxBarUser.setFavourite_cocktails(new ArrayList<Cocktail>());
        juxBarUserService.saveJuxBarUser(juxBarUser);

    }

    public void createSuperAdmin(String username, String password) {
        JuxBarUser juxBarUser = new JuxBarUser();
        juxBarUser.setUsername(username);
        juxBarUser.setPassword(password);
        juxBarUser.setRole("SUPER ADMIN");
        juxBarUser.setFavourite_softdrinks(new ArrayList<SoftDrink>());
        juxBarUser.setFavourite_cocktails(new ArrayList<Cocktail>());
        juxBarUserService.saveJuxBarUser(juxBarUser);
    }


    private List<GrantedAuthority> getAuthorities(String role) {
        log.info("in the getAuthorities with role : {}", role);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }
}
