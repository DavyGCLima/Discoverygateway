package com.example.eureka_gateway.eureka_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers(
                                "/api/authenticate",
                                        "/api/logout",
                                        "/api/register",
                                        "/api/activate",
                                        "/api/account/reset-password/init",
                                        "/api/account/reset-password/finish",
                                        "/management/health",
                                        "/management/info"
                                ).permitAll()
                                .requestMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
                                .anyRequest().authenticated()
                )
                .build();
    }

}
