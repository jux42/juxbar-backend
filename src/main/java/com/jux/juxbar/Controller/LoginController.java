package com.jux.juxbar.Controller;

import com.jux.juxbar.Configuration.CustomUserDetailsService;
import com.jux.juxbar.Service.JWTService;
import com.jux.juxbar.Service.JuxBarUserService;
import jakarta.annotation.security.RolesAllowed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
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
    public ResponseEntity<String> getToken(@RequestParam("username") String username, @RequestParam("password") String password) {
        log.info("Controller Step 1");
        try {
            log.info("Controller Step 2");
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username, password
                    )
            );
            log.info(password);
            SecurityContextHolder.getContext().setAuthentication(authentication);


//            customUserDetailsService.loadUserByUsername(username);
            String token =  jwtService.generateToken(authentication);
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @GetMapping(value = "/user", produces = "text/plain")
    public String getUsername(Principal principal) {
        log.info(jwtService.getClass().getName());
        log.info(principal.getName());
        return   principal.getName();
    }

    @GetMapping("/admin")
    public String getAdmin() {
        return "Admin is OK";
    }

}

