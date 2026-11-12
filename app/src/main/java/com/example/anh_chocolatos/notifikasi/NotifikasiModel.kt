package com.example.anh_chocolatos.notifikasi

// Data class sebagai model data 1 notifikasi (seperti MessageModel di materi)
data class NotifikasiModel(
    val judul: String,        // judul notifikasi
    val pesan: String,        // isi singkat notifikasi
    val waktu: String,        // jam / tanggal di kanan atas
    val grup: String,         // "Hari ini", "Kemarin", "Minggu ini", "Minggu lalu"
    val kategori: String,     // "Transaksi", "Harga TBS", "Anggota"
    val ikon: Int,            // gambar ikon (dimuat dengan Glide)
    val warnaLatar: Int,      // warna kotak ikon
    val warnaIkon: Int,       // warna ikon
    var sudahDibaca: Boolean  // false = garis hijau + titik merah
)
