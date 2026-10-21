package com.example.anh_chocolatos

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.example.anh_chocolatos.databinding.ActivityBaseBinding

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

        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.beranda -> {
                    Toast.makeText(this, "Beranda Clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.laporan -> {
                    Toast.makeText(this, "Laporan Clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.notifikasi -> {
                    Toast.makeText(this, "Notifikasi Clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.profil -> {
                    Toast.makeText(this, "Profil Clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }
}