package com.portofolio.backend_api; // <--- INI KUNCINYA (Tanpa .model)

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "buku_tamu")
public class BukuTamu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Nama tidak boleh kosong")
    private String nama;

    @NotEmpty(message = "Email harus diisi")
    @Email(message = "Format email salah")
    private String email;

    @Size(min = 5, message = "Pesan minimal 5 karakter")
    private String pesan;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String foto;

    public BukuTamu() {}

    public BukuTamu(String nama, String email, String pesan, String foto) {
        this.nama = nama;
        this.email = email;
        this.pesan = pesan;
        this.foto = foto;
    }

    // GETTER SETTER
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