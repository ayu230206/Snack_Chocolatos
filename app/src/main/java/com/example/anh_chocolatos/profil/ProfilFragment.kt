package com.example.anh_chocolatos.profil

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.anh_chocolatos.AuthActivity
import com.example.anh_chocolatos.databinding.FragmentProfilBinding
import com.example.anh_chocolatos.sikuda.DeveloperActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class ProfilFragment : Fragment() {

    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Toolbar (judul "Profil" ada di TextView tengah, tanpa tombol back)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
        }

        // Ambil data dari SharedPreferences
        val sharedPref = requireContext().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
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
            Toast.makeText(requireContext(), if (isChecked) "Notifikasi transaksi aktif" else "Notifikasi transaksi nonaktif", Toast.LENGTH_SHORT).show()
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
            val intent = Intent(requireContext(), DeveloperActivity::class.java)
            startActivity(intent)
        }
        binding.rowTentang.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("SIKUDA Pantau")
                .setMessage("Versi 1.0.0\nAplikasi pemantauan keuangan & keanggotaan untuk Pemilik KUD Lubuk Karya.")
                .setPositiveButton("Tutup") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

        // Keluar → konfirmasi → hapus SharedPreferences → AuthActivity
        binding.btnKeluar.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Keluar dari akun?")
                .setMessage("Anda perlu masuk kembali untuk memantau data KUD Lubuk Karya.")
                .setPositiveButton("Keluar") { dialog, _ ->
                    val editor = sharedPref.edit()
                    editor.clear()
                    editor.apply()

                    dialog.dismiss()
                    val intent = Intent(requireContext(), AuthActivity::class.java)
                    startActivity(intent)
                    requireActivity().finishAffinity() // tutup BaseActivity
                }
                .setNegativeButton("Batal") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }
}