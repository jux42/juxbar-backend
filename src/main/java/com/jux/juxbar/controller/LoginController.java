package com.jux.juxbar.controller;

import com.jux.juxbar.service.JWTService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
public class LoginController {

    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

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
            String token = jwtService.generateToken(authentication);
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @GetMapping(value = "/user", produces = "text/plain")
    public String getUsername(Principal principal) {
        log.info(jwtService.getClass().getName());
        log.info(principal.getName());
        return principal.getName();
    }

    @GetMapping("/admin")
    public String getAdmin() {
        return "Admin is OK";
    }

}

