package com.example.gestimmob.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig {


        @Autowired
        private UserDetailsService uds;

        @Autowired
        private BCryptPasswordEncoder encoder;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/webjars/**", "/images/**", "/annonces", "/filter").permitAll()
                                                .anyRequest().authenticated())
                                .formLogin(form -> form
                                                .loginPage("/login")
                                                .permitAll()
                                                .successHandler((request, response, authentication) -> {
                                                        log.info("Connexion réussie pour l'utilisateur : {}", authentication.getName());
                                                        if (authentication.getAuthorities().stream()
                                                                        .anyMatch(authority -> authority.getAuthority()
                                                                                        .equals("ADMIN"))) {
                                                                response.sendRedirect("/immobilier/list");
                                                        } else {
                                                                response.sendRedirect("/annonces");
                                                        }
                                                }).failureHandler((request, response, exception) -> {
                                                        log.error("Erreur de connexion : {}", exception.getMessage());
                                                        response.sendRedirect("/login?error=true");
                                                })
                                                
                                                )
                                .exceptionHandling(exceptionHandling -> exceptionHandling
                                                .accessDeniedPage("/access-denied"))
                                .logout(logout -> logout.logoutSuccessHandler((request, response, authentication) -> {
                                        if (authentication != null) {
                                            log.info("Déconnexion réussie pour l'utilisateur : {}", authentication.getName());
                                        }
                                        response.sendRedirect("/login?logout=true");
                                    })
                                    .permitAll());
                return http.build();
        }

        @Bean
        public AuthenticationProvider authenticationProvider() {
                DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
                authenticationProvider.setUserDetailsService(uds);
                authenticationProvider.setPasswordEncoder(encoder);
                return authenticationProvider;
        }
}