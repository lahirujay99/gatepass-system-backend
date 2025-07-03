package com.gatepass.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Enable CORS using the bean provided in WebConfig
                .cors(withDefaults())

                // 2. Disable CSRF, as we'll use stateless JWTs
                .csrf(csrf -> csrf.disable())

                // 3. Define authorization rules
                .authorizeHttpRequests(authz -> authz
                        // Make the health check endpoint public
                        .requestMatchers("/api/health").permitAll()

                        // All other requests must be authenticated
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}