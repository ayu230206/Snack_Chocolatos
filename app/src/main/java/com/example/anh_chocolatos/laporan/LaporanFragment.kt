package com.example.anh_chocolatos.laporan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.anh_chocolatos.databinding.FragmentLaporanBinding
import androidx.appcompat.app.AppCompatActivity

class LaporanFragment : Fragment() {

    // Binding untuk mengakses komponen di fragment_laporan.xml
    private var _binding: FragmentLaporanBinding? = null
    private val binding get() = _binding!!

    // Dipanggil saat Fragment membuat tampilannya
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Hubungkan layout fragment_laporan.xml ke Fragment ini (versi binding)
        _binding = FragmentLaporanBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
        }
    }
}
