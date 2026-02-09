package com.portofolio.backend_api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(PenggunaRepository repo, PasswordEncoder encoder) {
        return args -> {
            // 1. Cek apakah user 'admin' sudah ada di database?
            if (repo.findByUsername("admin").isEmpty()) {
                
                // 2. Kalau belum ada, kita buat baru
                Pengguna admin = new Pengguna();
                admin.setUsername("admin");
                
                // 3. Password "admin123" kita ENKRIPSI dulu biar aman
                admin.setPassword(encoder.encode("admin123")); 
                
                admin.setRole("ADMIN");

                // 4. Simpan ke Database
                repo.save(admin);
                
                System.out.println("---------------------------------------------");
                System.out.println("✅ SUKSES: User 'admin' berhasil dibuat di Database MariaDB!");
                System.out.println("---------------------------------------------");
            } else {
                // Info saja kalau user sudah ada
                System.out.println("ℹ️ INFO: User 'admin' sudah ada di database. Skip.");
            }
        };
    }
}