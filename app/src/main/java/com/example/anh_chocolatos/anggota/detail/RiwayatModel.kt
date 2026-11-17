package com.example.anh_chocolatos.anggota.detail

// Data class model 1 baris riwayat (seperti ProductModel di materi)
data class RiwayatModel(
    val judul: String,        // contoh: "Simpanan Wajib", "30 Sep 2026", "Pupuk NPK 50 kg"
    val keterangan: String,   // contoh: tanggal, "1.500 kg × Rp3.912", "2 sak · 18 Sep 2026"
    val nominal: String,      // contoh: "+Rp50.000"
    val ikon: Int,            // gambar ikon (dimuat dengan Glide)
    val warnaLatar: Int,      // warna kotak ikon
    val warnaIkon: Int,       // warna ikon
    val warnaNominal: Int     // warna teks nominal
)
