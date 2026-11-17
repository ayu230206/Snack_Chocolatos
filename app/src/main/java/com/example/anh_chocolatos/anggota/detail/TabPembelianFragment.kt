package com.example.anh_chocolatos.anggota.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anh_chocolatos.R
import com.example.anh_chocolatos.databinding.FragmentTabPembelianBinding

class TabPembelianFragment : Fragment() {

    private var _binding: FragmentTabPembelianBinding? = null
    private val binding get() = _binding!!

    companion object {
        // List data riwayat (dummy sesuai desain Figma)
        val riwayatList = listOf(
            RiwayatModel("Pupuk NPK 50 kg", "2 sak · 18 Sep 2026", "Rp1.450.000", R.drawable.ic_keranjang, R.color.sikuda_tint_oranye, R.color.sikuda_accent, R.color.sikuda_teks),
            RiwayatModel("Herbisida Roundup", "4 liter · 2 Sep 2026", "Rp420.000", R.drawable.ic_keranjang, R.color.sikuda_tint_oranye, R.color.sikuda_accent, R.color.sikuda_teks),
            RiwayatModel("Pupuk Urea 50 kg", "2 sak · 14 Agu 2026", "Rp760.000", R.drawable.ic_keranjang, R.color.sikuda_tint_oranye, R.color.sikuda_accent, R.color.sikuda_teks),
            RiwayatModel("Egrek + gagang", "1 unit · 3 Jul 2026", "Rp350.000", R.drawable.ic_keranjang, R.color.sikuda_tint_oranye, R.color.sikuda_accent, R.color.sikuda_teks),
            RiwayatModel("Pupuk KCl 50 kg", "3 sak · 10 Jun 2026", "Rp1.750.000", R.drawable.ic_keranjang, R.color.sikuda_tint_oranye, R.color.sikuda_accent, R.color.sikuda_teks)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabPembelianBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ringkasan: jumlah transaksi dihitung dari jumlah data di RecyclerView
        binding.textJumlahTransaksi.text = "${riwayatList.size} kali"

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
