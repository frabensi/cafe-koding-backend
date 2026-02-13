package com.portofolio.backend_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletResponse; // Import Wajib untuk Excel

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Controller
public class SapaanController {

    @Autowired
    private BukuTamuRepository bukuTamuRepository;

    // 1. HALAMAN UTAMA (List Tamu)
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("daftarTamu", bukuTamuRepository.findAll());
        return "index";
    }

    // 2. FORM TAMBAH BARU
    @GetMapping("/tamu/baru")
    public String formTambah(Model model) {
        model.addAttribute("bukuTamu", new BukuTamu());
        return "tambah-tamu"; 
    }

    // 3. PROSES SIMPAN (CREATE & UPDATE)
    @PostMapping("/tamu/simpan")
    public String simpanTamu(@ModelAttribute BukuTamu bukuTamu,
                             @RequestParam("fileGambar") MultipartFile file) {
        try {
            // A. Cek apakah user upload foto baru?
            if (!file.isEmpty()) {
                String base64Image = Base64.getEncoder().encodeToString(file.getBytes());
                bukuTamu.setFoto(base64Image);
            } else {
                // B. Jika tidak upload, cek apakah edit data lama?
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

    // 4. FORM EDIT
    @GetMapping("/tamu/edit/{id}")
    public String editTamu(@PathVariable Long id, Model model) {
        BukuTamu bukuTamu = bukuTamuRepository.findById(id).orElse(null);
        model.addAttribute("bukuTamu", bukuTamu);
        return "tambah-tamu";
    }

    // 5. HAPUS DATA
    @GetMapping("/tamu/hapus/{id}")
    public String hapusTamu(@PathVariable Long id) {
        bukuTamuRepository.deleteById(id);
        return "redirect:/";
    }

    // --- 6. FITUR BARU: DOWNLOAD EXCEL ---
    @GetMapping("/tamu/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        
        // Nama file saat didownload
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Laporan_Tamu_VIP.xlsx";
        response.setHeader(headerKey, headerValue);

        // Ambil data dan cetak
        List<BukuTamu> listTamu = bukuTamuRepository.findAll();
        ExcelExporter excelExporter = new ExcelExporter(listTamu);
        excelExporter.export(response);
    }
}