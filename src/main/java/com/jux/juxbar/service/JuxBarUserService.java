package com.jux.juxbar.service;


import com.jux.juxbar.configuration.CustomUserDetailsService;
import com.jux.juxbar.model.JuxBarUser;
import com.jux.juxbar.repository.JuxBarUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class JuxBarUserService {

    private final JuxBarUserRepository juxBarUserRepository;


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
    public void saveJuxBarUser(String username, String password, CustomUserDetailsService customUserDetailsService) {
        customUserDetailsService.createUser(username, password);

    }
}
