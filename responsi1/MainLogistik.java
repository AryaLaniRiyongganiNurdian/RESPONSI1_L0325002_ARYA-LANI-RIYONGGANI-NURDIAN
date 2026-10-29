/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package responsi1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MainLogistik {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // [1d] Koleksi dinamis ArrayList<BarangBantuan> untuk menyimpan barang yang didata
        ArrayList<BarangBantuan> daftarBarang = new ArrayList<>();
        String pilihan = "";

        // [2a] Perulangan while untuk menu interaktif, berhenti saat memilih menu "Selesai dan Cetak Manifes"
        while (!pilihan.equals("3")) {
            System.out.println("\n=== SISTEM LOGISTIK BANTUAN DAERAH 3T ===");
            System.out.println("1. Tambah barang");
            System.out.println("2. Lihat daftar barang");
            System.out.println("3. Selesai dan Cetak Manifes");
            System.out.print("Pilih menu: ");
            pilihan = input.nextLine();

            if (pilihan.equals("1")) {
                boolean valid = false;

                // [3b-iii] Perulangan agar user diminta mengulangi input sampai data valid
                while (!valid) {

                    // [3b] Exception Handling (try-catch-finally)
                    try {
                        System.out.print("Kode barang (diawali EDX-): ");
                        String kode = input.nextLine();

                        // [3b-ii] Validasi kode dengan String .startsWith(), lempar IllegalArgumentException
                        if (!kode.startsWith("EDX-")) {
                            throw new IllegalArgumentException("Kode barang harus diawali EDX-");
                        }

                        System.out.print("Nama barang: ");
                        String nama = input.nextLine();

                        System.out.print("Kategori (contoh: Elektronik, Buku): ");
                        String kategori = input.nextLine();

                        System.out.print("Berat (kg): ");
                        double berat = Double.parseDouble(input.nextLine());

                        // [3b-i] Jika berat <= 0, lempar BeratInvalidException
                        if (berat <= 0) {
                            throw new BeratInvalidException("Berat barang harus lebih dari 0 kg");
                        }

                        // [1d] Barang disimpan sebagai objek baru ke dalam ArrayList
                        daftarBarang.add(new BarangBantuan(kode, nama, kategori, berat));
                        System.out.println("Barang berhasil ditambahkan.");
                        valid = true;

                    // [3b-iii] Galat ditangkap agar program tidak crash, lalu input diulang
                    } catch (NumberFormatException e) {
                        System.out.println("Galat: Berat harus berupa angka. Silakan ulangi input.");
                    } catch (BeratInvalidException e) {
                        System.out.println("Galat: " + e.getMessage() + ". Silakan ulangi input.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Galat: " + e.getMessage() + ". Silakan ulangi input.");
                    } finally {
                        System.out.println("Pemeriksaan input selesai.");
                    }
                }

            } else if (pilihan.equals("2")) {
                // Menampilkan isi ArrayList dengan for-each
                if (daftarBarang.isEmpty()) {
                    System.out.println("Belum ada barang yang didata.");
                } else {
                    System.out.println("--- Daftar Muatan ---");
                    for (BarangBantuan b : daftarBarang) {
                        b.tampilkan();
                    }
                }

            } else if (!pilihan.equals("3")) {
                System.out.println("Pilihan tidak valid.");
            }
        }

        // [4a] Menghitung total barang dan total berat muatan dari ArrayList
        int totalBarang = daftarBarang.size();
        double totalBerat = 0;
        for (BarangBantuan b : daftarBarang) {
            totalBerat += b.beratKg;
        }

        // [2b] Percabangan if-else untuk label armada
        String armada;
        if (totalBerat < 50) {
            // [2b-i] Total berat < 50 kg
            armada = "Mobil Pickup";
        } else {
            // [2b-ii] Total berat >= 50 kg
            armada = "Truk Ekspedisi";
        }

        // [4b] File I/O: BufferedWriter dan FileWriter menulis daftar barang, total berat, dan label armada
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("manifest_pengiriman.txt"))) {
            bw.write("=== MANIFES PENGIRIMAN ===");
            bw.newLine();
            bw.write("Kode | Nama | Kategori | Berat");
            bw.newLine();
            for (BarangBantuan b : daftarBarang) {
                bw.write(b.kodeBarang + " | " + b.namaBarang + " | " + b.kategori + " | " + b.beratKg + " kg");
                bw.newLine();
            }
            bw.write("--------------------------");
            bw.newLine();
            bw.write("Total barang : " + totalBarang);
            bw.newLine();
            bw.write("Total berat  : " + totalBerat + " kg");
            bw.newLine();
            bw.write("Armada       : " + armada);

            // [4c] Notifikasi di konsol bahwa manifes berhasil diekspor
            System.out.println("Data manifes berhasil diekspor ke manifest_pengiriman.txt");
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat menulis file: " + e.getMessage());
        }

        input.close();
    }
}