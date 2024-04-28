package com.jux.juxbar.Service;


import com.jux.juxbar.Model.JuxBarUser;
import com.jux.juxbar.Repository.JuxBarUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JuxBarUserService {

    @Autowired
    private JuxBarUserRepository juxBarUserRepository;


    public JuxBarUser getJuxBarUserByUsername(String username) {
        return juxBarUserRepository.findByUsername(username);
    }
}
