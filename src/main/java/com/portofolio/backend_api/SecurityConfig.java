package com.portofolio.backend_api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. ATURAN IZIN AKSES
            .authorizeHttpRequests((requests) -> requests
                // DAFTAR HALAMAN YANG BOLEH DIAKSES TANPA LOGIN:
                .requestMatchers(
                    "/",                // Halaman Utama
                    "/daftar",          // Halaman Daftar (kalau ada)
                    "/css/**",          // File CSS
                    "/js/**",           // File JavaScript
                    "/foto-tamu/**",    // Foto Upload User
                    "/api/**",          // API JSON (untuk Mobile/Frontend lain)
                    
                    // --- TAMBAHAN UNTUK SWAGGER UI ---
                    "/v3/api-docs/**",  // Data mentah dokumentasi
                    "/swagger-ui/**",   // Tampilan web dokumentasi
                    "/swagger-ui.html"  // Alamat alternatif
                ).permitAll()
                
                // Sisanya WAJIB LOGIN
                .anyRequest().authenticated()
            )
            
            // 2. SETTING FORM LOGIN
            .formLogin((form) -> form
                .permitAll()                  // Pakai halaman login bawaan Spring Boot
                .defaultSuccessUrl("/", true) // Kalau sukses, masuk ke Home
            )

            // 3. SETTING LOGOUT
            .logout((logout) -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout") // Kembali ke login bawaan
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )

            // 4. MATIKAN CSRF (Supaya upload & API lancar)
            .csrf(csrf -> csrf.disable());

        return http.build();
    }

    // ALAT PENGUNCI PASSWORD (WAJIB ADA!)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}