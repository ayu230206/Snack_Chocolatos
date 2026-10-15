package com.example.anh_chocolatos

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.example.anh_chocolatos.databinding.ActivityMainBinding
import com.example.anh_chocolatos.sikuda.ProfilActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Latar halaman terang → ikon status bar gelap
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true

        // Ambil username dari SharedPreferences
        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
        val username = sharedPref.getString("username", "Pengguna")
        binding.textNama.text = username

        // Bar navigasi bawah
        binding.navLaporan.setOnClickListener {
            Toast.makeText(this, "Halaman Laporan segera hadir", Toast.LENGTH_SHORT).show()
        }
        binding.navNotifikasi.setOnClickListener {
            Toast.makeText(this, "Halaman Notifikasi segera hadir", Toast.LENGTH_SHORT).show()
        }
        binding.navProfil.setOnClickListener {
            val intent = Intent(this, ProfilActivity::class.java)
            startActivity(intent)
        }
    }
}