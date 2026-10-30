package com.example.anh_chocolatos

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.anh_chocolatos.databinding.ActivityRegistrasiBinding

class RegistrasiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrasiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Email dari EmailActivity, otomatis terisi dan tidak bisa diubah
        val emailDariGmail = intent.getStringExtra("email") ?: ""
        binding.inputEmail.setText(emailDariGmail)
        binding.inputEmail.isEnabled = false

        binding.btnRegistrasi.setOnClickListener {
            val nama = binding.inputNama.text.toString().trim()
            val email = binding.inputEmail.text.toString().trim()
            val username = binding.inputUsername.text.toString().trim()
            val password = binding.inputPassword.text.toString()

            if (nama.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                // Semua field wajib diisi
                Toast.makeText(this, "Semua field wajib diisi", Toast.LENGTH_SHORT).show()
            } else if (password.length < 6) {
                // Password minimal 6 karakter
                Toast.makeText(this, "Password minimal 6 karakter", Toast.LENGTH_SHORT).show()
            } else if (username.contains(" ")) {
                // Username tidak boleh mengandung spasi
                Toast.makeText(this, "Username tidak boleh mengandung spasi", Toast.LENGTH_SHORT).show()
            } else {
                // Lolos validasi → simpan ke SharedPreferences
                val sharedPref = getSharedPreferences("user_register", MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("nama", nama)
                editor.putString("email", email)
                editor.putString("username", username)
                editor.putString("password", password)
                editor.apply()

                // Tampilkan halaman informasi "Registrasi Berhasil"
                startActivity(Intent(this, RegistrasiBerhasilActivity::class.java))
                finish()
            }
        }
    }
}