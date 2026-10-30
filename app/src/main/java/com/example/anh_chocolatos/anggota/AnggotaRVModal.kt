package com.example.anh_chocolatos.anggota

// Tutorial: data class CourseRVModal(courseName, courseImg)
data class AnggotaRVModal(
    var namaAnggota: String,
    var nikAnggota: String,     // TAMBAHAN: agar bisa dicari lewat NIK
    var statusAnggota: String   // TAMBAHAN: Aktif / Pasif / Keluar
)