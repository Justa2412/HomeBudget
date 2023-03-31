package com.example.homebudget.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
class AppSecurityConfig {


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    AuthenticationManager customAuthenticationManager(
            HttpSecurity http,
            UserDetailsService userDetailsService,
            PasswordEncoder encoder
    ) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder)
                .and()
                .build();
    }


    @Bean
    SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        return security
                .cors()
                .disable()
                .csrf()
                .disable()
                .authorizeRequests(
                        // Permit all traffic for /users
                        securityCustomizer -> securityCustomizer.antMatchers("/users").permitAll()
                )
                .authorizeRequests(
                        // Require authentication for any other endpoint
                        securityCustomizer -> securityCustomizer.antMatchers("/**").authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
