package com.example.anh_chocolatos.latihan

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.anh_chocolatos.R
import com.example.anh_chocolatos.databinding.ActivityLatihanBinding
import kotlin.jvm.java

class latihanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLatihanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLatihanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnkomposisi.setOnClickListener {
            val intent = Intent(this, KomposisiActivity::class.java)
            startActivity(intent)
        }

    }
}