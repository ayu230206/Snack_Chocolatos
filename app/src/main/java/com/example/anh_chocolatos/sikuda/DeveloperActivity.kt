package com.example.anh_chocolatos.sikuda

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.anh_chocolatos.R
import com.example.anh_chocolatos.databinding.ActivityDeveloperBinding

class DeveloperActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeveloperBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDeveloperBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Toolbar + tombol back
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Profil Developer"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        // Teks sosmed diambil dari strings.xml
        binding.textInstagram.text = "📸  Instagram · " + getString(R.string.dev_instagram)
        binding.textGithub.text = "💻  GitHub · " + getString(R.string.dev_github)
        binding.textLinkedin.text = "💼  LinkedIn · " + getString(R.string.dev_linkedin)

        // Ketuk sosmed → buka di WebView (kirim url lewat putExtra)
        binding.textInstagram.setOnClickListener {
            bukaWeb("Instagram", getString(R.string.dev_instagram_url))
        }
        binding.textGithub.setOnClickListener {
            bukaWeb("GitHub", getString(R.string.dev_github_url))
        }
        binding.textLinkedin.setOnClickListener {
            bukaWeb("LinkedIn", getString(R.string.dev_linkedin_url))
        }
    }

    private fun bukaWeb(judul: String, url: String) {
        val intent = Intent(this, WebViewActivity::class.java)
        intent.putExtra("judul", judul)
        intent.putExtra("url", url)
        startActivity(intent)
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