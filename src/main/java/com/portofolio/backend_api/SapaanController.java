package com.portofolio.backend_api; // <--- SAMA, TANPA .controller

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Controller
public class SapaanController {

    @Autowired
    private BukuTamuRepository bukuTamuRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("daftarTamu", bukuTamuRepository.findAll());
        return "index";
    }

    @GetMapping("/tamu/baru")
    public String formTambah(Model model) {
        model.addAttribute("bukuTamu", new BukuTamu());
        return "tambah-tamu"; 
    }

    @PostMapping("/tamu/simpan")
    public String simpanTamu(@ModelAttribute BukuTamu bukuTamu,
                             @RequestParam("fileGambar") MultipartFile file) {
        try {
            if (!file.isEmpty()) {
                String base64Image = Base64.getEncoder().encodeToString(file.getBytes());
                bukuTamu.setFoto(base64Image);
            } else {
                if (bukuTamu.getId() != null) {
                    BukuTamu dataLama = bukuTamuRepository.findById(bukuTamu.getId()).orElse(null);
                    if (dataLama != null) {
                        bukuTamu.setFoto(dataLama.getFoto());
                    }
                }
            }
            bukuTamuRepository.save(bukuTamu);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @GetMapping("/tamu/edit/{id}")
    public String editTamu(@PathVariable Long id, Model model) {
        BukuTamu bukuTamu = bukuTamuRepository.findById(id).orElse(null);
        model.addAttribute("bukuTamu", bukuTamu);
        return "tambah-tamu";
    }

    @GetMapping("/tamu/hapus/{id}")
    public String hapusTamu(@PathVariable Long id) {
        bukuTamuRepository.deleteById(id);
        return "redirect:/";
    }
}