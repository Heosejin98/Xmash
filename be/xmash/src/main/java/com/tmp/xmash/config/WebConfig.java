package com.tmp.xmash.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Log4j2
public class WebConfig {

    @Value("${cors.allowed.origins:http://localhost.com}")
    private String[] allowedOrigins;

    @Bean
    public WebMvcConfigurer corsConfigurer() {

        for (String allowedOrigin : allowedOrigins) {
            log.info("allowedOrigins : {}", allowedOrigin);
        }

        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(allowedOrigins)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true); // 자격증명 허용
            }
        };
    }
}
