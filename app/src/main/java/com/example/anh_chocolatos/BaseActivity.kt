package com.example.anh_chocolatos

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.anh_chocolatos.beranda.BerandaFragment
import com.example.anh_chocolatos.databinding.ActivityBaseBinding
import com.example.anh_chocolatos.laporan.LaporanFragment
import com.example.anh_chocolatos.notifikasi.NotifikasiFragment
import com.example.anh_chocolatos.profil.ProfilFragment

class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // bottom = 0 agar tidak ada jarak di bawah BottomNavigation (sesuai modul)
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        // Latar terang → ikon status bar gelap
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true

        /** BerandaFragment sebagai fragment default */
        replaceFragment(BerandaFragment())

        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.beranda -> {
                    replaceFragment(BerandaFragment())
                    true
                }
                R.id.laporan -> {
                    replaceFragment(LaporanFragment())
                    true
                }
                R.id.notifikasi -> {
                    replaceFragment(NotifikasiFragment())
                    true
                }
                R.id.profil -> {
                    replaceFragment(ProfilFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            //.addToBackStack(null) -> dinonaktifkan agar saat back langsung keluar aplikasi
            .commit()
    }
}