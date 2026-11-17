package com.example.anh_chocolatos.sikuda

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.anh_chocolatos.AuthActivity
import com.example.anh_chocolatos.R
import com.example.anh_chocolatos.databinding.ActivityOnboardingBinding
import com.example.anh_chocolatos.sikuda.onboarding.Onboarding1Fragment
import com.example.anh_chocolatos.sikuda.onboarding.Onboarding2Fragment
import com.example.anh_chocolatos.sikuda.onboarding.Onboarding3Fragment
import com.example.anh_chocolatos.sikuda.onboarding.OnboardingFragmentAdapter

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding

    // true jika dibuka dari Profil -> "Bantuan & Panduan" (bukan saat pertama buka aplikasi)
    private var dariProfil = false

    companion object {
        const val EXTRA_DARI_PROFIL = "extra_dari_profil"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Latar halaman ini terang, jadi ikon status bar dibuat gelap
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true

        // Toolbar + tombol back (tanpa judul, sesuai Figma)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back_dark)
        }

        dariProfil = intent.getBooleanExtra(EXTRA_DARI_PROFIL, false)

        // Setup ViewPager dengan adapter
        val fragmentsList = listOf(Onboarding1Fragment(), Onboarding2Fragment(), Onboarding3Fragment())
        val adapter = OnboardingFragmentAdapter(this, fragmentsList)
        binding.onboardingViewPager.adapter = adapter

        // Hubungkan DotsIndicator dengan ViewPager2
        binding.dotIndicator.attachTo(binding.onboardingViewPager)

        // Teks tombol berubah di halaman terakhir
        binding.onboardingViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == fragmentsList.lastIndex) {
                    binding.btnLanjut.text = if (dariProfil) "Selesai" else "Mulai Sekarang"
                } else {
                    binding.btnLanjut.text = "Lanjut"
                }
            }
        })

        // Kunjungi Website → WebView
        binding.btnWebsite.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            startActivity(intent)
        }

        // Lanjut → geser ke halaman berikutnya, di halaman terakhir → selesai
        binding.btnLanjut.setOnClickListener {
            val posisi = binding.onboardingViewPager.currentItem
            if (posisi < fragmentsList.lastIndex) {
                binding.onboardingViewPager.currentItem = posisi + 1
            } else {
                selesaiOnboarding()
            }
        }

        // Lewati → langsung selesai
        binding.textLewati.setOnClickListener {
            selesaiOnboarding()
        }
    }

    // Dari Profil: kembali ke Profil. Saat pertama buka aplikasi: ke halaman Login
    private fun selesaiOnboarding() {
        if (dariProfil) {
            finish()
        } else {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
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
