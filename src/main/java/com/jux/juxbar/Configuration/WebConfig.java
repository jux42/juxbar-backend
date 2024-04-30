package com.jux.juxbar.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Autorise les requêtes CORS pour toutes les routes
                .allowedOrigins("http://localhost:4200", "http://192.168.1.49:4200") // Remplacez par l'URL de votre front-end Angular
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true).maxAge(3600);
    }
}
