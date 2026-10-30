package com.example.anh_chocolatos

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.anh_chocolatos.databinding.ActivityRegistrasiBerhasilBinding

class RegistrasiBerhasilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrasiBerhasilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrasiBerhasilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnKeLogin.setOnClickListener {
            startActivity(Intent(this, AuthActivity::class.java))
            finishAffinity()
        }
    }
}