package com.example.anh_chocolatos.beranda

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.anh_chocolatos.databinding.FragmentBerandaBinding

class BerandaFragment : Fragment() {

    // Binding untuk mengakses komponen di fragment_beranda.xml
    private var _binding: FragmentBerandaBinding? = null
    private val binding get() = _binding!!

    // Dipanggil saat Fragment membuat tampilannya
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Hubungkan layout fragment_beranda.xml ke Fragment ini (versi binding)
        _binding = FragmentBerandaBinding.inflate(inflater, container, false)
        return binding.root
    }
}
