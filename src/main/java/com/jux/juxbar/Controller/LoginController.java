package com.jux.juxbar.Controller;

import com.jux.juxbar.Model.JuxBarUser;
import com.jux.juxbar.Service.JuxBarUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin

public class LoginController {

    @Autowired
    JuxBarUserService juxBarUserService;
    @Autowired
    AuthenticationManager authenticationManager;

    @RequestMapping("/login")
    public ResponseEntity<?> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username, password
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return ResponseEntity.ok().body("User authenticated successfully");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
    }

    @GetMapping("/user")
    public JuxBarUser getUser(HttpServletRequest request) {
        return  juxBarUserService.getJuxBarUserByUsername(request.getUserPrincipal().getName());
    }
//
//    @GetMapping("/admin")
//    public String getAdmin() {
//        return "Admin";
//    }

}

