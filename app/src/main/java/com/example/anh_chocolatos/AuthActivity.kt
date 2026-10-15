package com.example.anh_chocolatos

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.anh_chocolatos.databinding.ActivityAuthBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Header hijau sampai ke balik status bar seperti di Figma
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            binding.appBar.setPadding(0, systemBars.top, 0, 0)
            insets
        }

        // Toolbar + tombol back
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Login"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        // Tombol Masuk
        binding.btnLogin.setOnClickListener {
            val username = binding.inputUsername.text.toString().trim()
            val password = binding.inputPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                // 1. Kolom kosong
                Toast.makeText(this, "Nama pengguna dan kata sandi wajib diisi", Toast.LENGTH_SHORT).show()

            } else if (username == password) {
                // 2. Autentikasi berhasil → simpan ke SharedPreferences
                val waktuLogin = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("id", "ID")).format(Date())

                val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putBoolean("isLogin", true)
                editor.putString("username", username)
                editor.putString("lastLogin", waktuLogin)
                editor.apply()

                Toast.makeText(this, "Login berhasil, selamat datang $username", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finishAffinity() // tutup Auth & Onboarding, back dari Beranda tidak kembali ke Login

            } else {
                // 3. Autentikasi gagal → AlertDialog
                MaterialAlertDialogBuilder(this)
                    .setTitle("Login Gagal")
                    .setMessage("Silahkan coba lagi")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }

        // Lupa kata sandi → Snackbar
        binding.textLupaSandi.setOnClickListener {
            Snackbar.make(binding.root, "Hubungi Admin SIKUDA untuk reset kata sandi", Snackbar.LENGTH_SHORT)
                .setAction("Tutup") { }
                .show()
        }

        // Sidik jari → Snackbar
        binding.btnSidikJari.setOnClickListener {
            Snackbar.make(binding.root, "Login sidik jari belum tersedia", Snackbar.LENGTH_SHORT).show()
        }
    }

    // Agar tombol back di Toolbar berfungsi
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}