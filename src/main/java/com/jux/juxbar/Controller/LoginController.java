package com.jux.juxbar.Controller;

import com.jux.juxbar.Configuration.CustomUserDetailsService;
import com.jux.juxbar.Service.JWTService;
import com.jux.juxbar.Service.JuxBarUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin

public class LoginController {

    @Autowired
    JuxBarUserService juxBarUserService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    public JWTService jwtService;

     @Autowired
    public CustomUserDetailsService customUserDetailsService;


    @PostMapping("/login")
    public String getToken(@RequestParam("username") String username, @RequestParam("password") String password) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username, password
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println(password);
            customUserDetailsService.loadUserByUsername(username);
            return jwtService.generateToken(authentication);
        } catch (AuthenticationException e) {
            return "BAAAD";
        }
    }



    @GetMapping("/user")
    public String getUsername(Principal principal) {
        System.out.println(jwtService.getClass().getName());
        System.out.println(principal.getName());
        return   principal.getName();
    }
//
//    @GetMapping("/admin")
//    public String getAdmin() {
//        return "Admin";
//    }

}

