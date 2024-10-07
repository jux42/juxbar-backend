package com.jux.juxbar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;



@SpringBootApplication
@ConfigurationPropertiesScan
@EnableCaching
public class JuxBarApplication {

    public static void main(String[] args) {
        SpringApplication.run(JuxBarApplication.class, args);

    }
}
