package com.example.anh_chocolatos.notifikasi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.anh_chocolatos.databinding.FragmentNotifikasiBinding

class NotifikasiFragment : Fragment() {

    // Binding untuk mengakses komponen di fragment_notifikasi.xml
    private var _binding: FragmentNotifikasiBinding? = null
    private val binding get() = _binding!!

    // Dipanggil saat Fragment membuat tampilannya
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Hubungkan layout fragment_notifikasi.xml ke Fragment ini (versi binding)
        _binding = FragmentNotifikasiBinding.inflate(inflater, container, false)
        return binding.root
    }
}
