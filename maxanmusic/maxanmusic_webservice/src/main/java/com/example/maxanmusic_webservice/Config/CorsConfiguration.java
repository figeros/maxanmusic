package com.example.maxanmusic_webservice.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080")
                .allowCredentials(true)
                .allowedMethods("GET", "POST","DELETE","PUT","PATCH");
    }
}

// web tarayıcılarının CORS saldırısına karşı erişimi engellediğinden
// sadece yerel web sunucusuna cevap verecek şekilde ayarlandı

//.allowedOrigins("http://ec2-3-66-170-217.eu-central-1.compute.amazonaws.com")
//.allowedOrigins("http://localhost:8080")