package com.jux.juxbar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication (exclude = {UserDetailsServiceAutoConfiguration.class})
@ConfigurationPropertiesScan

public class JuxBarApplication {

    public static void main(String[] args) {
        SpringApplication.run(JuxBarApplication.class, args);
    }

}
