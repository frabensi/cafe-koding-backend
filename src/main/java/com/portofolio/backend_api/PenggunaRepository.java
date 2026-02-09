package com.portofolio.backend_api;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PenggunaRepository extends JpaRepository<Pengguna, Long> {
    // Kita butuh fitur khusus: Mencari berdasarkan USERNAME
    Optional<Pengguna> findByUsername(String username);
}