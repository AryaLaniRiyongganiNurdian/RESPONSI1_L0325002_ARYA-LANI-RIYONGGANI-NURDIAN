/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package responsi1;

// [1a] Kelas BarangBantuan
public class BarangBantuan {

    // [1b] Atribut: kodeBarang, namaBarang, kategori, beratKg
    String kodeBarang;
    String namaBarang;
    String kategori;
    double beratKg;

    // [1c] Konstruktor yang menginisialisasi seluruh atribut
    public BarangBantuan(String kodeBarang, String namaBarang, String kategori, double beratKg) {
        this.kodeBarang = kodeBarang;
        this.namaBarang = namaBarang;
        this.kategori = kategori;
        this.beratKg = beratKg;
    }

    public void tampilkan() {
        System.out.println(kodeBarang + " | " + namaBarang + " | " + kategori + " | " + beratKg + " kg");
    }
}