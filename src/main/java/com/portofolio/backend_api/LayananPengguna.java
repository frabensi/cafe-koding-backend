package com.portofolio.backend_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LayananPengguna implements UserDetailsService {

    @Autowired
    private PenggunaRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Cari user di Database MariaDB
        Pengguna userKita = repo.findByUsername(username)
             .orElseThrow(() -> new UsernameNotFoundException("User tidak ditemukan: " + username));

        // 2. Terjemahkan data "Pengguna" jadi "UserDetails" (Bahasa Satpam)
        return org.springframework.security.core.userdetails.User
                .withUsername(userKita.getUsername())
                .password(userKita.getPassword()) // Password terenkripsi dari DB
                .roles(userKita.getRole())        // Peran (ADMIN/USER)
                .build();
    }
}