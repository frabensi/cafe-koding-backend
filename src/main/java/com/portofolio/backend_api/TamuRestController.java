package com.portofolio.backend_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController // <--- Ini kuncinya! Dia akan membalas dengan DATA (JSON), bukan HTML.
@RequestMapping("/api") // Semua alamat di file ini berawalan "/api"
public class TamuRestController {

    @Autowired
    private BukuTamuRepository bukuTamuRepository;

    // ALAMAT AKSES: localhost:8080/api/tamu
    @GetMapping("/tamu")
    public List<BukuTamu> ambilSemuaTamu() {
        // Spring Boot otomatis mengubah List Java menjadi JSON
        return bukuTamuRepository.findAll();
    }
}