package com.jux.juxbar.Configuration;

import com.jux.juxbar.Service.JuxBarUserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
public class SuperAdminInitializer {

    @Autowired
    JuxBarUserService juxBarUserService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Value("${superadmin.username}")
    private String superAdminUsername;

    //TODO la valeur en dur est OK en dev, pour la prod il vaut mieux utiliser un Secrets Manager (ex: AWS Secrets Manager)
    @Value("${superadmin.password}")
    private String hashedPassword;

    @PostConstruct
    public void init() {


        if (hashedPassword == null || hashedPassword.isEmpty()) {
            throw new IllegalStateException("Superadmin password not set in environment variables");
        }

        try {
            customUserDetailsService.loadUserByUsername(superAdminUsername);
        } catch (UsernameNotFoundException e) {
            customUserDetailsService.createSuperAdmin(superAdminUsername, hashedPassword);
        }
    }
}
