package com.jux.juxbar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class JuxBarApplication {

    public static void main(String[] args) {
        SpringApplication.run(JuxBarApplication.class, args);
    }

}
