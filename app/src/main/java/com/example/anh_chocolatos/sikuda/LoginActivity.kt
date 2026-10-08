package com.example.anh_chocolatos.sikuda

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.anh_chocolatos.R
import com.example.anh_chocolatos.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Header hijau dibiarkan sampai ke balik status bar seperti di Figma,
            // jadi jarak atas hanya diberikan ke Toolbar
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
                Toast.makeText(this, "Nama pengguna dan kata sandi wajib diisi", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Login berhasil, selamat datang $username", Toast.LENGTH_SHORT).show()
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