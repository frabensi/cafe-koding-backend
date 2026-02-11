package com.portofolio.backend_api; // <--- SAMA, TANPA .repository

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BukuTamuRepository extends JpaRepository<BukuTamu, Long> {
    List<BukuTamu> findByNamaContainingIgnoreCase(String nama);
}