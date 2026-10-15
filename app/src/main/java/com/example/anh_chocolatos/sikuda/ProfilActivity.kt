package com.example.anh_chocolatos.sikuda

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.anh_chocolatos.AuthActivity
import com.example.anh_chocolatos.R
import com.example.anh_chocolatos.databinding.ActivityProfilBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class ProfilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Header hijau sampai ke balik status bar seperti di Figma
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            binding.appBar.setPadding(0, systemBars.top, 0, 0)
            insets
        }

        // Toolbar + tombol back (judul "Profil" ada di TextView tengah)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        // Ambil data dari SharedPreferences
        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
        val username = sharedPref.getString("username", "pengguna") ?: "pengguna"
        val lastLogin = sharedPref.getString("lastLogin", "-")

        binding.textNama.text = username
        binding.textInisial.text = username.take(2).uppercase()
        binding.textUsername.text = username
        binding.textLastLogin.text = lastLogin

        // Switch: simpan pilihan ke SharedPreferences
        binding.switchNotif.isChecked = sharedPref.getBoolean("notifTransaksi", true)
        binding.switchNotif.setOnCheckedChangeListener { _, isChecked ->
            val editor = sharedPref.edit()
            editor.putBoolean("notifTransaksi", isChecked)
            editor.apply()
            Toast.makeText(this, if (isChecked) "Notifikasi transaksi aktif" else "Notifikasi transaksi nonaktif", Toast.LENGTH_SHORT).show()
        }

        binding.switchSidikJari.isChecked = sharedPref.getBoolean("loginSidikJari", true)
        binding.switchSidikJari.setOnCheckedChangeListener { _, isChecked ->
            val editor = sharedPref.edit()
            editor.putBoolean("loginSidikJari", isChecked)
            editor.apply()
        }

        // Menu
        binding.rowUbahSandi.setOnClickListener {
            Snackbar.make(binding.root, "Fitur ubah kata sandi segera hadir", Snackbar.LENGTH_SHORT).show()
        }
        binding.rowBantuan.setOnClickListener {
            Snackbar.make(binding.root, "Bantuan & panduan segera hadir", Snackbar.LENGTH_SHORT).show()
        }
        binding.rowDeveloper.setOnClickListener {
            val intent = Intent(this, DeveloperActivity::class.java)
            startActivity(intent)
        }
        binding.rowTentang.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("SIKUDA Pantau")
                .setMessage("Versi 1.0.0\nAplikasi pemantauan keuangan & keanggotaan untuk Pemilik KUD Lubuk Karya.")
                .setPositiveButton("Tutup") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
        // Keluar → konfirmasi → hapus SharedPreferences → AuthActivity
        binding.btnKeluar.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Keluar dari akun?")
                .setMessage("Anda perlu masuk kembali untuk memantau data KUD Lubuk Karya.")
                .setPositiveButton("Keluar") { dialog, _ ->
                    val editor = sharedPref.edit()
                    editor.clear()
                    editor.apply()

                    dialog.dismiss()
                    val intent = Intent(this, AuthActivity::class.java)
                    startActivity(intent)
                    finishAffinity() // tutup Profil & Beranda
                }
                .setNegativeButton("Batal") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
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