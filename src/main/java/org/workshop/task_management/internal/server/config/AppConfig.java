package org.workshop.task_management.internal.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.workshop.task_management.pkg.middleware.security.JwtUtil;

@Configuration
public class AppConfig {

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }
}