package com.portofolio.backend_api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // 1. AREA PUBLIK (Bisa diakses siapa saja)
                .requestMatchers("/", "/tamu/baru", "/tamu/simpan", "/foto-tamu/**", "/css/**", "/js/**").permitAll()
                
                // 2. AREA RAHASIA (Harus Login: Edit & Hapus)
                .anyRequest().authenticated()
            )
            .formLogin(login -> login
                .defaultSuccessUrl("/", true) // Kalau sukses login, balik ke home
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/") // Kalau logout, balik ke home
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}