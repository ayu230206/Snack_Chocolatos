package com.example.anh_chocolatos.anggota.detail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.anh_chocolatos.R
import com.example.anh_chocolatos.databinding.ActivityDetailAnggotaBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetailAnggotaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailAnggotaBinding

    companion object {
        // Kunci data yang dikirim dari Data Anggota lewat Intent
        const val EXTRA_NAMA = "extra_nama"
        const val EXTRA_NIK = "extra_nik"
        const val EXTRA_STATUS = "extra_status"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailAnggotaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Toolbar + tombol back
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.btnKembali.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        // Data anggota yang diklik di Data Anggota
        val nama = intent.getStringExtra(EXTRA_NAMA) ?: "Budi Santoso"
        val nik = intent.getStringExtra(EXTRA_NIK) ?: "1406 0318 7905 0457"
        val status = intent.getStringExtra(EXTRA_STATUS) ?: "Aktif"
        tampilkanProfil(nama, nik, status)

        // 1. Inisialisasi Adapter
        val tabsAdapter = DetailAnggotaTabsAdapter(this)

        // 2. Set adapter ke ViewPager2
        binding.viewPager.adapter = tabsAdapter

        // 3. Hubungkan TabLayout & ViewPager2 menggunakan Adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            // Atur judul untuk setiap tab
            when (position) {
                0 -> tab.text = "Simpanan"
                1 -> tab.text = "Penjualan TBS"
                2 -> tab.text = "Pembelian"
            }
        }.attach()
    }

    // Isi kartu profil sesuai anggota yang dipilih
    private fun tampilkanProfil(nama: String, nik: String, status: String) {
        val nikAngka = nik.replace(" ", "")
        val empatAkhir = nikAngka.takeLast(4)

        binding.textNama.text = nama
        binding.textAvatar.text = nama.split(" ")
            .filter { it.isNotEmpty() }
            .take(2)
            .joinToString("") { it.first().uppercase() }
        binding.textNoAnggota.text = "KUD-$empatAkhir"
        binding.textNik.text = "NIK ${nikAngka.take(4)} •••• •••• $empatAkhir"
        binding.textTelepon.text = "0812-7xxx-$empatAkhir"
        binding.textAlamat.text = "Desa Lubuk Karya, Kec. Tapung"

        // Warna badge status sama seperti di Data Anggota
        binding.textStatus.text = status
        when (status) {
            "Aktif" -> {
                binding.textStatus.setBackgroundResource(R.drawable.bg_chip_hijau)
                binding.textStatus.setTextColor(ContextCompat.getColor(this, R.color.sikuda_kas_masuk))
            }
            "Pasif" -> {
                binding.textStatus.setBackgroundResource(R.drawable.bg_ikon_oranye)
                binding.textStatus.setTextColor(ContextCompat.getColor(this, R.color.sikuda_accent))
            }
            else -> {
                binding.textStatus.setBackgroundResource(R.drawable.bg_kotak_keluar)
                binding.textStatus.setTextColor(ContextCompat.getColor(this, R.color.sikuda_kas_keluar))
            }
        }
    }
}
