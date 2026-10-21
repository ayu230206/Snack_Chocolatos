package com.example.anh_chocolatos.profil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.anh_chocolatos.databinding.FragmentProfilBinding

class ProfilFragment : Fragment() {

    // Binding untuk mengakses komponen di fragment_profil.xml
    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!

    // Dipanggil saat Fragment membuat tampilannya
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Hubungkan layout fragment_profil.xml ke Fragment ini (versi binding)
        _binding = FragmentProfilBinding.inflate(inflater, container, false)
        return binding.root
    }
}
