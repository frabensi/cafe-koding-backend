package com.portofolio.backend_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
public class SapaanController {

    @Autowired
    private BukuTamuRepository bukuTamuRepository;

    // Lokasi folder foto (sejajar dengan file pom.xml)
    public static String UPLOAD_DIRECTORY = "./foto-tamu/";

    // 1. HALAMAN UTAMA (LIST TAMU)
    @GetMapping("/")
    public String halamanUtama(Model model) {
        model.addAttribute("daftarTamu", bukuTamuRepository.findAll());
        return "index";
    }

    // 2. FORM TAMBAH BARU
    @GetMapping("/tamu/baru")
    public String halamanTambah(Model model) {
        model.addAttribute("bukuTamu", new BukuTamu());
        return "tambah-tamu";
    }

    // 3. FORM EDIT (Perhatikan URL-nya: /tamu/edit/{id})
    @GetMapping("/tamu/edit/{id}")
    public String halamanEdit(@PathVariable Long id, Model model) {
        BukuTamu tamu = bukuTamuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID Tamu salah: " + id));
        
        model.addAttribute("bukuTamu", tamu);
        return "tambah-tamu";
    }

    // 4. HAPUS DATA (Perhatikan URL-nya: /tamu/hapus/{id})
    @GetMapping("/tamu/hapus/{id}")
    public String hapusTamu(@PathVariable Long id) {
        bukuTamuRepository.deleteById(id);
        return "redirect:/";
    }

    // 5. PROSES SIMPAN (CREATE & UPDATE)
    @PostMapping("/tamu")
    public String simpanTamu(@Valid @ModelAttribute BukuTamu bukuTamu,
                             BindingResult result,
                             @RequestParam("fileFoto") MultipartFile file,
                             Model model) throws IOException {

        // Jika ada error validasi (misal nama kosong), kembalikan ke form
        if (result.hasErrors()) {
            return "tambah-tamu";
        }

        // --- LOGIKA UPLOAD FOTO ---
        if (!file.isEmpty()) {
            // A. Kalau user upload foto baru
            String namaFileUnik = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path pathFolder = Paths.get(UPLOAD_DIRECTORY);
            
            if (!Files.exists(pathFolder)) {
                Files.createDirectories(pathFolder);
            }

            Path pathFile = pathFolder.resolve(namaFileUnik);
            Files.copy(file.getInputStream(), pathFile, StandardCopyOption.REPLACE_EXISTING);
            
            bukuTamu.setFoto(namaFileUnik);
        } else {
            // B. Kalau user TIDAK upload foto (sedang Edit)
            // Kita cari data lama di database, ambil nama foto lamanya
            if (bukuTamu.getId() != null) {
                BukuTamu tamuLama = bukuTamuRepository.findById(bukuTamu.getId()).orElse(null);
                if (tamuLama != null) {
                    bukuTamu.setFoto(tamuLama.getFoto());
                }
            }
        }

        bukuTamuRepository.save(bukuTamu);
        return "redirect:/";
    }
}