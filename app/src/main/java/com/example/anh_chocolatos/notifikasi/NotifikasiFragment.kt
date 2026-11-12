package com.example.anh_chocolatos.notifikasi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.anh_chocolatos.R
import com.example.anh_chocolatos.databinding.FragmentNotifikasiBinding

class NotifikasiFragment : Fragment() {

    // Binding untuk mengakses komponen di fragment_notifikasi.xml
    private var _binding: FragmentNotifikasiBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: NotifikasiAdapter

    // List data notifikasi (dummy sesuai desain Figma)
    private val notifikasiList = listOf(
        // ===== Hari ini =====
        NotifikasiModel("Kas keluar besar tercatat", "Penyaluran dana Rp12.500.000 untuk 25 anggota oleh Admin Keuangan.",
            "10:24", "Hari ini", "Transaksi", R.drawable.ic_peringatan, R.color.sikuda_tint_merah, R.color.sikuda_kas_keluar, false),
        NotifikasiModel("Penjualan TBS besar", "Rudi Hartono setor 2.140 kg senilai Rp8,37 jt.",
            "09:15", "Hari ini", "Transaksi", R.drawable.ic_panah_masuk, R.color.sikuda_hijau_soft, R.color.sikuda_kas_masuk, false),
        NotifikasiModel("Harga TBS baru ditetapkan", "Rp3.912/kg untuk periode 30 Sep – 6 Okt 2026 (turun Rp18/kg).",
            "08:00", "Hari ini", "Harga TBS", R.drawable.ic_tag, R.color.sikuda_tint_oranye, R.color.sikuda_accent, false),
        NotifikasiModel("3 pendaftar baru", "Admin Anggota sedang memeriksa berkas KK, KTP & surat pernyataan.",
            "07:45", "Hari ini", "Anggota", R.drawable.ic_anggota, R.color.sikuda_tint_biru, R.color.sikuda_info, false),
        NotifikasiModel("Anggota baru diterima", "Dewi Lestari resmi menjadi anggota (KUD-1180).",
            "07:30", "Hari ini", "Anggota", R.drawable.ic_anggota_terima, R.color.sikuda_hijau_soft, R.color.sikuda_primary, true),

        // ===== Kemarin =====
        NotifikasiModel("Laporan September siap", "Laporan kas 1–29 Sep 2026 sudah bisa diunduh sebagai PDF.",
            "17:00", "Kemarin", "Transaksi", R.drawable.ic_dokumen, R.color.sikuda_hijau_soft, R.color.sikuda_primary, true),
        NotifikasiModel("Simpanan wajib masuk", "Rp2.400.000 dari 48 anggota tercatat hari ini.",
            "15:12", "Kemarin", "Transaksi", R.drawable.ic_koin, R.color.sikuda_hijau_soft, R.color.sikuda_primary, true),
        NotifikasiModel("Gaji karyawan dibayar", "Kas keluar Rp8.000.000 untuk 4 karyawan.",
            "11:00", "Kemarin", "Transaksi", R.drawable.ic_panah_keluar, R.color.sikuda_tint_merah, R.color.sikuda_kas_keluar, true),

        // ===== Minggu ini =====
        NotifikasiModel("Harga CPO melemah", "CPO Rp15.526/kg, harga TBS diperkirakan turun tipis.",
            "29 Sep", "Minggu ini", "Harga TBS", R.drawable.ic_tren_turun, R.color.sikuda_tint_oranye, R.color.sikuda_accent, true),
        NotifikasiModel("5 anggota jadi pasif", "Tidak menjual TBS lebih dari 3 bulan.",
            "28 Sep", "Minggu ini", "Anggota", R.drawable.ic_jam, R.color.sikuda_tint_oranye, R.color.sikuda_accent, true),
        NotifikasiModel("Anggota keluar", "Suparman keluar, simpanan Rp350.000 dikembalikan.",
            "28 Sep", "Minggu ini", "Anggota", R.drawable.ic_anggota_keluar, R.color.sikuda_tint_abu, R.color.sikuda_teks_2, true),

        // ===== Minggu lalu =====
        NotifikasiModel("Harga TBS baru ditetapkan", "Rp3.931/kg periode 23 – 29 Sep 2026 (naik Rp1/kg).",
            "23 Sep", "Minggu lalu", "Harga TBS", R.drawable.ic_tag, R.color.sikuda_tint_oranye, R.color.sikuda_accent, true),
        NotifikasiModel("Harga TBS baru ditetapkan", "Rp3.930/kg periode 16 – 22 Sep 2026 (turun Rp47/kg).",
            "16 Sep", "Minggu lalu", "Harga TBS", R.drawable.ic_tag, R.color.sikuda_tint_oranye, R.color.sikuda_accent, true)
    )

    // Dipanggil saat Fragment membuat tampilannya
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Hubungkan layout fragment_notifikasi.xml ke Fragment ini (versi binding)
        _binding = FragmentNotifikasiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi Toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
        }

        // Terapkan NotifikasiAdapter ke ListView
        adapter = NotifikasiAdapter(requireContext(), notifikasiList)
        binding.listNotifikasiItems.adapter = adapter

        // "Tandai dibaca" -> semua notifikasi jadi sudah dibaca
        binding.textTandaiDibaca.setOnClickListener {
            notifikasiList.forEach { it.sudahDibaca = true }
            adapter.notifyDataSetChanged()
            Toast.makeText(requireContext(), "Semua notifikasi ditandai dibaca", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
