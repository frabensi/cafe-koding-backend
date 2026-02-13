package com.portofolio.backend_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired private PenggunaRepository repo;
    @Autowired private PasswordEncoder encoder;

    @Override
    public void run(String... args) {
        // Cek apakah user admin sudah ada?
        if (repo.findByUsername("admin").isEmpty()) {
            
            Pengguna admin = new Pengguna();
            admin.setUsername("admin");
            admin.setPassword(encoder.encode("admin123")); // Password: admin123
            admin.setRole("ADMIN");
            
            repo.save(admin);
            System.out.println("✅ SUKSES: User 'admin' berhasil dibuat! Password: admin123");
        }
    }
}