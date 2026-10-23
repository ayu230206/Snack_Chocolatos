package com.example.anh_chocolatos

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.anh_chocolatos.databinding.ActivityEmailBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLanjutEmail.setOnClickListener {
            val email = binding.inputEmail.text.toString().trim()
            val formatGmail = Regex("^[A-Za-z0-9._%+-]+@gmail\\.com$")

            if (email.isEmpty()) {
                // Validasi 1: email kosong
                tampilkanError("Email tidak boleh kosong")
            } else if (!formatGmail.matches(email.lowercase())) {
                // Validasi 2: domain bukan @gmail.com
                tampilkanError("Email harus menggunakan domain @gmail.com")
            } else {
                // Valid → bawa email ke halaman Registrasi
                val intent = Intent(this, RegistrasiActivity::class.java)
                intent.putExtra("email", email)
                startActivity(intent)
            }
        }
    }

    private fun tampilkanError(pesan: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Email Tidak Valid")
            .setMessage(pesan)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}