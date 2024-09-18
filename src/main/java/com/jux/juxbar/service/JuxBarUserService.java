package com.jux.juxbar.service;


import com.jux.juxbar.configuration.CustomUserDetailsService;
import com.jux.juxbar.model.JuxBarUser;
import com.jux.juxbar.repository.JuxBarUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor

public class JuxBarUserService {

    private final JuxBarUserRepository juxBarUserRepository;
    private final CustomUserDetailsService customUserDetailsService;


    public JuxBarUser getJuxBarUserByUsername(String username) {
        return juxBarUserRepository.findByUsername(username);
    }

    public List<JuxBarUser> getAllJuxBarUsers() {
        return juxBarUserRepository.findAll();
    }

    // signature de méthode pour les modifs en BDD
    public void saveJuxBarUser(JuxBarUser juxBarUser) {
        juxBarUserRepository.save(juxBarUser);
    }

    // surcharge pour la création d'un user via formulaire
    public void saveJuxBarUser(String username, String password) {
        customUserDetailsService.createUser(username, password);

    }



    public String reactivateUser(String username) {
        JuxBarUser userToReactivate = juxBarUserRepository.findByUsername(username);
        if(userToReactivate == null) {
            return "user not found";
        }
        if (userToReactivate.isActive()) {
            return "user already activated";
        }
        userToReactivate.setActive(true);
        juxBarUserRepository.save(userToReactivate);
        return "user reactivated successfully";
    }

    public String disableUser(String username) {
        JuxBarUser userToDisable = juxBarUserRepository.findByUsername(username);
        if(userToDisable == null) {
            return "user not found";
        }
        if (!userToDisable.isActive()) {
            return "user already inactive";
        }
        userToDisable.setActive(false);
        juxBarUserRepository.save(userToDisable);
        return "user disabled successfully";
    }
}
