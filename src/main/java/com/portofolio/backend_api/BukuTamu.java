package com.portofolio.backend_api;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
// IMPORT BARU: Ini adalah Pasal-Pasal Hukumnya 👇
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class BukuTamu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // PASAL 1: Nama Wajib Diisi!
    @NotBlank(message = "Waduh, Nama tidak boleh kosong dong!") 
    private String nama;

    // PASAL 2: Email Wajib Diisi & Harus Format Email (ada @ dan .)
    @NotBlank(message = "Email harus diisi ya!")
    @Email(message = "Format email salah! Contoh: nama@gmail.com")
    private String email;

    // PASAL 3: Pesan Minimal 5 Huruf
    @NotBlank(message = "Pesan tidak boleh kosong.")
    @Size(min = 5, message = "Pesan terlalu pendek, minimal 5 huruf ya!")
    private String pesan;

    private String foto;

    // Constructor Kosong (Wajib)
    public BukuTamu() {}

    // Getter dan Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPesan() { return pesan; }
    public void setPesan(String pesan) { this.pesan = pesan; }

    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }
}