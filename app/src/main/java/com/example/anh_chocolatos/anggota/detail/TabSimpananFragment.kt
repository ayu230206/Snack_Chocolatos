package com.example.anh_chocolatos.anggota.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anh_chocolatos.R
import com.example.anh_chocolatos.databinding.FragmentTabSimpananBinding

class TabSimpananFragment : Fragment() {

    private var _binding: FragmentTabSimpananBinding? = null
    private val binding get() = _binding!!

    companion object {
        // List data riwayat (dummy sesuai desain Figma)
        val riwayatList = listOf(
            RiwayatModel("Simpanan Wajib", "22 Sep 2026", "+Rp50.000", R.drawable.ic_koin, R.color.sikuda_hijau_soft, R.color.sikuda_primary, R.color.sikuda_kas_masuk),
            RiwayatModel("Simpanan Wajib", "22 Agu 2026", "+Rp50.000", R.drawable.ic_koin, R.color.sikuda_hijau_soft, R.color.sikuda_primary, R.color.sikuda_kas_masuk),
            RiwayatModel("Pengambilan", "5 Agu 2026", "−Rp150.000", R.drawable.ic_panah_keluar, R.color.sikuda_tint_merah, R.color.sikuda_kas_keluar, R.color.sikuda_kas_keluar),
            RiwayatModel("Simpanan Wajib", "22 Jul 2026", "+Rp50.000", R.drawable.ic_koin, R.color.sikuda_hijau_soft, R.color.sikuda_primary, R.color.sikuda_kas_masuk),
            RiwayatModel("Simpanan Pokok", "12 Mar 2019", "+Rp100.000", R.drawable.ic_koin, R.color.sikuda_tint_biru, R.color.sikuda_info, R.color.sikuda_info)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabSimpananBinding.inflate(inflater, container, false)
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
