package com.example.anh_chocolatos.anggota.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anh_chocolatos.R
import com.example.anh_chocolatos.databinding.FragmentTabPenjualanBinding

class TabPenjualanFragment : Fragment() {

    private var _binding: FragmentTabPenjualanBinding? = null
    private val binding get() = _binding!!

    companion object {
        // List data riwayat (dummy sesuai desain Figma)
        val riwayatList = listOf(
            RiwayatModel("30 Sep 2026", "1.500 kg × Rp3.912", "Rp5.868.435", R.drawable.ic_daun, R.color.sikuda_tint_oranye, R.color.sikuda_accent, R.color.sikuda_kas_masuk),
            RiwayatModel("23 Sep 2026", "480 kg × Rp3.931", "Rp1.886.880", R.drawable.ic_daun, R.color.sikuda_tint_oranye, R.color.sikuda_accent, R.color.sikuda_kas_masuk),
            RiwayatModel("16 Sep 2026", "620 kg × Rp3.930", "Rp2.436.600", R.drawable.ic_daun, R.color.sikuda_tint_oranye, R.color.sikuda_accent, R.color.sikuda_kas_masuk),
            RiwayatModel("9 Sep 2026", "540 kg × Rp3.977", "Rp2.147.580", R.drawable.ic_daun, R.color.sikuda_tint_oranye, R.color.sikuda_accent, R.color.sikuda_kas_masuk),
            RiwayatModel("2 Sep 2026", "700 kg × Rp3.951", "Rp2.765.700", R.drawable.ic_daun, R.color.sikuda_tint_oranye, R.color.sikuda_accent, R.color.sikuda_kas_masuk)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabPenjualanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = RiwayatAdapter(riwayatList) { selectedItem ->
            Toast.makeText(requireContext(), "Anda memilih ${selectedItem.judul} (${selectedItem.nominal})", Toast.LENGTH_SHORT).show()
        }

        binding.rvRiwayat.apply {
            /** Mode Linear (daftar ke bawah, sesuai Figma) **/
            layoutManager = LinearLayoutManager(requireContext())

            /** Jika ingin model Grid **/
            //layoutManager = GridLayoutManager(requireContext(), 2)

            this.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
