package com.example.anh_chocolatos.beranda

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.anh_chocolatos.R
import com.example.anh_chocolatos.databinding.FragmentBerandaBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import android.content.Intent
import com.example.anh_chocolatos.anggota.DataAnggotaActivity

class BerandaFragment : Fragment() {

    private var _binding: FragmentBerandaBinding? = null
    private val binding get() = _binding!!

    lateinit var barChart: BarChart
    lateinit var barDataSet1: BarDataSet
    lateinit var barDataSet2: BarDataSet
    // 12 bulan lengkap (Januari - Desember)
    val months = arrayOf("Jan", "Feb", "Mar", "Apr", "Mei", "Jun", "Jul", "Agu", "Sep", "Okt", "Nov", "Des")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBerandaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Toolbar di Fragment memakai (requireActivity() as AppCompatActivity)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
        }

        // Ambil username dari SharedPreferences
        val sharedPref = requireContext().getSharedPreferences("user_pref", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", "Pengguna")
        binding.textNama.text = username

        // Tutorial: barChart = findViewById(R.id.idBarChart)
        barChart = binding.chartArusKas
        setupBarChart()
        loadBarChartData()
        binding.cardAnggotaAktif.setOnClickListener {
            startActivity(Intent(requireContext(), DataAnggotaActivity::class.java))
        }
    }

    private fun setupBarChart() {
        with(barChart) {
            description.isEnabled = false
            setDragEnabled(true)
            setVisibleXRangeMaximum(6f)   // tutorial: 3f → 6f

            xAxis.apply {
                valueFormatter = IndexAxisValueFormatter(months)
                // setCenterAxisLabels(true) dihapus: membuat label kosong
                // bila dipakai bersama IndexAxisValueFormatter
                position = XAxis.XAxisPosition.BOTTOM
                granularity = 1f
                isGranularityEnabled = true
                axisMinimum = -0.5f                   // tutorial: 0f
                axisMaximum = months.size - 0.5f      // tengah kelompok di 0, 1, 2, ...
                setLabelCount(months.size, false)     // tampilkan semua 12 bulan

                // TAMBAHAN (tampilan Figma)
                setDrawGridLines(false)
                setDrawAxisLine(false)
                textColor = ContextCompat.getColor(requireContext(), R.color.sikuda_abu)
                typeface = ResourcesCompat.getFont(requireContext(), R.font.poppins_regular)
            }

            // TAMBAHAN (tampilan Figma)
            legend.isEnabled = false                 // legenda sudah ada di header kartu
            axisRight.isEnabled = false
            axisLeft.axisMinimum = 0f
            axisLeft.setDrawLabels(false)
            axisLeft.setDrawAxisLine(false)
            axisLeft.gridColor = ContextCompat.getColor(requireContext(), R.color.sikuda_garis)

            animateY(1000)
        }
    }

    private fun loadBarChartData() {
        // Tutorial: "C++" → "Masuk", warna purple_200 → sikuda_kas_masuk
        barDataSet1 = BarDataSet(getBarChartDataForSet1(), "Masuk").apply {
            color = ContextCompat.getColor(requireContext(), R.color.sikuda_kas_masuk)
            setDrawValues(false)   // TAMBAHAN: tanpa angka di atas batang
        }
        // Tutorial: "Java" → "Keluar", warna teal_200 → sikuda_kas_keluar
        barDataSet2 = BarDataSet(getBarChartDataForSet2(), "Keluar").apply {
            color = ContextCompat.getColor(requireContext(), R.color.sikuda_kas_keluar)
            setDrawValues(false)   // TAMBAHAN
        }

        val data = BarData(barDataSet1, barDataSet2).apply {
            barWidth = 0.15f
        }

        barChart.apply {
            this.data = data
            groupBars(-0.5f, 0.5f, 0.1f)   // tutorial: groupBars(0f, 0.5f, 0.1f)
            invalidate()
        }
    }

    // Data dummy Kas Masuk (juta rupiah), Jan - Des — tutorial: data dummy C++
    private fun getBarChartDataForSet1(): ArrayList<BarEntry> {
        return arrayListOf(
            BarEntry(1f, 95f),
            BarEntry(2f, 102f),
            BarEntry(3f, 108f),
            BarEntry(4f, 118f),
            BarEntry(5f, 126f),
            BarEntry(6f, 112f),
            BarEntry(7f, 134f),
            BarEntry(8f, 121f),
            BarEntry(9f, 140.9f),
            BarEntry(10f, 130f),
            BarEntry(11f, 125f),
            BarEntry(12f, 150f)
        )
    }

    // Data dummy Kas Keluar (juta rupiah), Jan - Des — tutorial: data dummy Java
    private fun getBarChartDataForSet2(): ArrayList<BarEntry> {
        return arrayListOf(
            BarEntry(1f, 70f),
            BarEntry(2f, 72f),
            BarEntry(3f, 75f),
            BarEntry(4f, 80f),
            BarEntry(5f, 74f),
            BarEntry(6f, 79f),
            BarEntry(7f, 66f),
            BarEntry(8f, 88f),
            BarEntry(9f, 83.7f),
            BarEntry(10f, 78f),
            BarEntry(11f, 81f),
            BarEntry(12f, 90f)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}