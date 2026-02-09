package com.portofolio.backend_api;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BukuTamuRepository extends JpaRepository<BukuTamu, Long> {
    
    // INI YANG HILANG TADI:
    // "Tolong cari data yang namanya mirip (Containing) dan jangan pedulikan huruf besar/kecil (IgnoreCase)"
    List<BukuTamu> findByNamaContainingIgnoreCase(String nama);
}