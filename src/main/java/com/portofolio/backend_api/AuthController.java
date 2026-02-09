package com.portofolio.backend_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller; // <--- INI WAJIB ADA
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller // <--- JANGAN SAMPAI LUPA INI!
public class AuthController {

    @Autowired
    private PenggunaRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    // 1. Tampilkan Halaman Daftar
    @GetMapping("/daftar")
    public String halamanDaftar() {
        return "daftar"; // Mengarah ke file daftar.html
    }

    // 2. Proses Data Pendaftaran
    @PostMapping("/daftar")
    public String prosesDaftar(@RequestParam String username, 
                               @RequestParam String password, 
                               Model model) {
        
        // Cek apakah username sudah ada?
        if (repo.findByUsername(username).isPresent()) {
            model.addAttribute("error", "Username sudah terpakai!");
            return "daftar";
        }

        // Simpan user baru
        Pengguna userBaru = new Pengguna();
        userBaru.setUsername(username);
        userBaru.setPassword(encoder.encode(password)); // Enkripsi password
        userBaru.setRole("USER");

        repo.save(userBaru);

        return "redirect:/login"; // Kalau sukses, suruh login
    }
}