package com.jux.juxbar.Service;


import com.jux.juxbar.Configuration.CustomUserDetailsService;
import com.jux.juxbar.Model.JuxBarUser;
import com.jux.juxbar.Repository.JuxBarUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JuxBarUserService {

    @Autowired
    private JuxBarUserRepository juxBarUserRepository;



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
