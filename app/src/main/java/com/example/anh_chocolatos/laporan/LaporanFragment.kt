package com.example.anh_chocolatos.laporan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import com.example.anh_chocolatos.R
import com.example.anh_chocolatos.databinding.FragmentLaporanBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale
import kotlin.math.roundToInt

class LaporanFragment : Fragment() {

    // Binding untuk mengakses komponen di fragment_laporan.xml
    private var _binding: FragmentLaporanBinding? = null
    private val binding get() = _binding!!

    // ================= DATA DUMMY (dalam juta rupiah) =================

    // Data per bulan tahun 2026 (Januari - September)
    private val namaBulan = listOf(
        "Januari", "Februari", "Maret", "April", "Mei",
        "Juni", "Juli", "Agustus", "September"
    )
    private val labaPerBulan = floatArrayOf(47.3f, 52.4f, 60.9f, 54.2f, 58.3f, 66.3f, 70.0f, 75.4f, 57.2f)
    private val pengeluaranPerBulan = floatArrayOf(108.4f, 112.6f, 115.0f, 109.8f, 111.2f, 113.9f, 110.5f, 112.9f, 83.7f)

    // Rincian per jenis bulan September (sesuai Figma), jadi acuan bulan lainnya
    private val rincianSeptember = listOf(
        mapOf("icon" to R.drawable.ic_dompet, "title" to "Transaksi Kas", "jumlah" to 42, "nilai" to 12.4f),
        mapOf("icon" to R.drawable.ic_daun, "title" to "Penjualan TBS", "jumlah" to 312, "nilai" to 188.6f),
        mapOf("icon" to R.drawable.ic_tag, "title" to "Pembelian Barang", "jumlah" to 57, "nilai" to 21.3f),
        mapOf("icon" to R.drawable.ic_koin, "title" to "Simpanan", "jumlah" to 96, "nilai" to 9.55f),
        mapOf("icon" to R.drawable.ic_anggota, "title" to "Penyaluran Dana", "jumlah" to 25, "nilai" to 12.5f)
    )

    // Rincian untuk rentang 15 hari (sesuai Figma), jadi acuan rentang lainnya
    private val rincianRentang15Hari = listOf(
        mapOf("icon" to R.drawable.ic_daun, "title" to "Penjualan TBS", "jumlah" to 148, "nilai" to 62.1f),
        mapOf("icon" to R.drawable.ic_koin, "title" to "Simpanan", "jumlah" to 41, "nilai" to 4.2f),
        mapOf("icon" to R.drawable.ic_anggota, "title" to "Penyaluran Dana", "jumlah" to 12, "nilai" to 30.5f)
    )
    private val pemasukanRentang15Hari = 71.4f
    private val pengeluaranRentang15Hari = 39.8f

    // Data yang sedang tampil di ListView (berganti sesuai tab / periode)
    private var dataListWithDesc: List<Map<String, Any>> = emptyList()

    // ================= STATE HALAMAN =================
    private var modeAktif = MODE_BULAN
    private var indeksBulan = 8 // September
    private var tanggalDari: LocalDate = LocalDate.of(2026, 9, 1)
    private var tanggalSampai: LocalDate = LocalDate.of(2026, 9, 15)

    companion object {
        private const val MODE_BULAN = "bulan"
        private const val MODE_TAHUN = "tahun"
        private const val MODE_RENTANG = "rentang"
    }

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

        /** Toolbar */
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
        }

        // Aksi saat item ListView diklik
        binding.listViewItems.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = dataListWithDesc[position]
            val title = selectedItem["title"]
            val nominal = selectedItem["nominal"]
            Toast.makeText(requireContext(), "Kamu memilih: $title ($nominal)", Toast.LENGTH_SHORT).show()
        }

        // Tab periode
        binding.tabPerBulan.setOnClickListener { tampilkanPerBulan() }
        binding.tabPerTahun.setOnClickListener { tampilkanPerTahun() }
        binding.tabRentang.setOnClickListener { tampilkanRentang() }

        // Panah ganti bulan / tahun
        binding.btnPeriodeSebelum.setOnClickListener { gantiPeriode(-1) }
        binding.btnPeriodeSesudah.setOnClickListener { gantiPeriode(1) }

        // Kotak tanggal Dari / Sampai -> buka kalender
        binding.boxTanggalDari.setOnClickListener { bukaPemilihTanggal() }
        binding.boxTanggalSampai.setOnClickListener { bukaPemilihTanggal() }

        // Tombol Export Laporan
        binding.btnExportLaporan.setOnClickListener {
            Toast.makeText(requireContext(), "${binding.btnExportLaporan.text} diproses", Toast.LENGTH_SHORT).show()
        }

        setupGrafikTahunan()
        tampilkanPerBulan()
    }

    // ================= TAB PER BULAN =================
    private fun tampilkanPerBulan() {
        modeAktif = MODE_BULAN
        setTabAktif(binding.tabPerBulan)
        aturTampilan(pemilihPeriode = true, rentang = false, grafik = false, listView = true)

        val pengeluaran = pengeluaranPerBulan[indeksBulan]
        val laba = labaPerBulan[indeksBulan]
        val pemasukan = laba + pengeluaran

        binding.textPeriode.text = "${namaBulan[indeksBulan]} 2026"
        tampilkanRingkasan(pemasukan, pengeluaran, laba)
        binding.textJudulRincian.text = "Rincian per Jenis"

        // Bulan lain dihitung dari perbandingan pemasukannya dengan September
        val pemasukanSeptember = labaPerBulan[8] + pengeluaranPerBulan[8]
        tampilkanListView(buatRincian(rincianSeptember, pemasukan / pemasukanSeptember))

        binding.btnExportLaporan.text = "Export Laporan"
    }

    // ================= TAB PER TAHUN =================
    private fun tampilkanPerTahun() {
        modeAktif = MODE_TAHUN
        setTabAktif(binding.tabPerTahun)
        aturTampilan(pemilihPeriode = true, rentang = false, grafik = true, listView = false)

        val laba = labaPerBulan.sum()
        val pengeluaran = pengeluaranPerBulan.sum()
        binding.textPeriode.text = "Tahun 2026"
        tampilkanRingkasan(laba + pengeluaran, pengeluaran, laba)

        binding.chartLabaTahunan.animateY(1000)
        binding.btnExportLaporan.text = "Export Laporan Tahunan"
    }

    // ================= TAB RENTANG =================
    private fun tampilkanRentang() {
        modeAktif = MODE_RENTANG
        setTabAktif(binding.tabRentang)
        aturTampilan(pemilihPeriode = false, rentang = true, grafik = false, listView = true)

        val jumlahHari = ChronoUnit.DAYS.between(tanggalDari, tanggalSampai).toInt() + 1
        val faktor = jumlahHari / 15f

        binding.textTanggalDari.text = formatTanggal(tanggalDari)
        binding.textTanggalSampai.text = formatTanggal(tanggalSampai)

        val pemasukan = pemasukanRentang15Hari * faktor
        val pengeluaran = pengeluaranRentang15Hari * faktor
        tampilkanRingkasan(pemasukan, pengeluaran, pemasukan - pengeluaran)

        binding.textJudulRincian.text = "Rincian ${formatRentang(tanggalDari, tanggalSampai)}"
        tampilkanListView(buatRincian(rincianRentang15Hari, faktor))

        binding.btnExportLaporan.text = "Export Laporan $jumlahHari Hari"
    }

    // Kalender rentang tanggal (MaterialDatePicker)
    private fun bukaPemilihTanggal() {
        val picker = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("Pilih Rentang Tanggal")
            .setSelection(Pair(keMillis(tanggalDari), keMillis(tanggalSampai)))
            .build()

        picker.addOnPositiveButtonClickListener { pilihan ->
            tanggalDari = keTanggal(pilihan.first)
            tanggalSampai = keTanggal(pilihan.second)
            tampilkanRentang()
        }
        picker.show(childFragmentManager, "PEMILIH_RENTANG")
    }

    // ================= PANAH GANTI PERIODE =================
    private fun gantiPeriode(arah: Int) {
        if (modeAktif == MODE_BULAN) {
            val indeksBaru = indeksBulan + arah
            if (indeksBaru in namaBulan.indices) {
                indeksBulan = indeksBaru
                tampilkanPerBulan()
            } else {
                Toast.makeText(requireContext(), "Data bulan tersebut belum tersedia", Toast.LENGTH_SHORT).show()
            }
        } else if (modeAktif == MODE_TAHUN) {
            Toast.makeText(requireContext(), "Data tahun ${2026 + arah} belum tersedia", Toast.LENGTH_SHORT).show()
        }
    }

    // ================= LISTVIEW (SimpleAdapter) =================
    private fun tampilkanListView(data: List<Map<String, Any>>) {
        dataListWithDesc = data

        /* SimpleAdapter sebagai penghubung dataListWithDesc dengan layout item_laporan */
        val adapter = SimpleAdapter(
            requireContext(),
            dataListWithDesc,
            R.layout.item_laporan,
            arrayOf("icon", "title", "desc", "nominal"),
            intArrayOf(R.id.imgIkon, R.id.textJudul, R.id.textDesc, R.id.textNominal)
        )

        // Hubungkan listViewItems dengan adapter
        binding.listViewItems.adapter = adapter
    }

    // Ubah data acuan menjadi Map yang siap ditampilkan (jumlah & nominal dikali faktor)
    private fun buatRincian(acuan: List<Map<String, Any>>, faktor: Float): List<Map<String, Any>> {
        return acuan.map { item ->
            val jumlah = ((item["jumlah"] as Int) * faktor).roundToInt()
            val nilai = (item["nilai"] as Float) * faktor
            mapOf(
                "icon" to item["icon"]!!,
                "title" to item["title"]!!,
                "desc" to "$jumlah transaksi",
                "nominal" to formatRupiah(nilai)
            )
        }
    }

    // ================= GRAFIK PER TAHUN (MPAndroidChart) =================
    private fun setupGrafikTahunan() {
        val barChart = binding.chartLabaTahunan
        val warnaBiasa = ContextCompat.getColor(requireContext(), R.color.sikuda_hijau_grafik)
        val warnaSorot = ContextCompat.getColor(requireContext(), R.color.sikuda_primary)
        val warnaGaris = ContextCompat.getColor(requireContext(), R.color.sikuda_garis)
        val warnaLabel = ContextCompat.getColor(requireContext(), R.color.sikuda_abu_muda)

        val entries = labaPerBulan.mapIndexed { i, nilai -> BarEntry(i.toFloat(), nilai) }
        val dataSet = BarDataSet(entries, "Laba Bersih")
        // Bulan terakhir (September) disorot hijau tua, sisanya hijau muda
        dataSet.colors = entries.indices.map { if (it == entries.lastIndex) warnaSorot else warnaBiasa }
        dataSet.setDrawValues(false)
        dataSet.isHighlightEnabled = false

        val data = BarData(dataSet)
        data.barWidth = 0.6f
        barChart.data = data

        barChart.description.isEnabled = false
        barChart.legend.isEnabled = false
        barChart.axisRight.isEnabled = false
        barChart.setTouchEnabled(false)

        barChart.axisLeft.apply {
            axisMinimum = 0f
            setDrawLabels(false)
            setDrawAxisLine(false)
            gridColor = warnaGaris
        }

        barChart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            granularity = 1f
            setDrawGridLines(false)
            setDrawAxisLine(false)
            valueFormatter = IndexAxisValueFormatter(namaBulan.map { it.take(1) }) // J F M A ...
            textColor = warnaLabel
            textSize = 10f
            typeface = ResourcesCompat.getFont(requireContext(), R.font.poppins_regular)
        }
        barChart.invalidate()

        val rataRata = labaPerBulan.average().roundToInt()
        val bulanTertinggi = namaBulan[labaPerBulan.indices.maxBy { labaPerBulan[it] }]
        binding.textKeteranganGrafik.text = "Rata-rata Rp$rataRata jt / bulan · tertinggi $bulanTertinggi"
    }

    // ================= BANTUAN TAMPILAN =================
    private fun setTabAktif(tabAktif: TextView) {
        val semuaTab = listOf(binding.tabPerBulan, binding.tabPerTahun, binding.tabRentang)
        for (tab in semuaTab) {
            if (tab == tabAktif) {
                tab.setBackgroundResource(R.drawable.bg_tab_aktif)
                tab.setTextColor(ContextCompat.getColor(requireContext(), R.color.sikuda_putih))
                tab.typeface = ResourcesCompat.getFont(requireContext(), R.font.poppins_semibold)
            } else {
                tab.background = null
                tab.setTextColor(ContextCompat.getColor(requireContext(), R.color.sikuda_teks_2))
                tab.typeface = ResourcesCompat.getFont(requireContext(), R.font.poppins_medium)
            }
        }
    }

    private fun aturTampilan(pemilihPeriode: Boolean, rentang: Boolean, grafik: Boolean, listView: Boolean) {
        binding.cardPemilihPeriode.visibility = if (pemilihPeriode) View.VISIBLE else View.GONE
        binding.cardRentang.visibility = if (rentang) View.VISIBLE else View.GONE
        binding.cardGrafikTahunan.visibility = if (grafik) View.VISIBLE else View.GONE
        binding.textJudulRincian.visibility = if (listView) View.VISIBLE else View.GONE
        binding.cardRincian.visibility = if (listView) View.VISIBLE else View.GONE
    }

    private fun tampilkanRingkasan(pemasukan: Float, pengeluaran: Float, laba: Float) {
        binding.textPemasukan.text = formatRupiah(pemasukan)
        binding.textPengeluaran.text = formatRupiah(pengeluaran)
        binding.textLabaBersih.text = formatRupiah(laba)
    }

    // 140.9 -> "Rp140,9 jt", 9.55 -> "Rp9,55 jt", 1520 -> "Rp1,52 M"
    private fun formatRupiah(juta: Float): String {
        val simbol = DecimalFormatSymbols(Locale.forLanguageTag("id-ID"))
        return when {
            juta >= 1000f -> "Rp" + DecimalFormat("#,##0.##", simbol).format(juta / 1000f) + " M"
            juta < 10f -> "Rp" + DecimalFormat("0.##", simbol).format(juta) + " jt"
            else -> "Rp" + DecimalFormat("#,##0.#", simbol).format(juta) + " jt"
        }
    }

    private val formatTanggalPendek = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.forLanguageTag("id-ID"))

    // 2026-09-01 -> "1 Sep 2026"
    private fun formatTanggal(tanggal: LocalDate): String = tanggal.format(formatTanggalPendek)

    // "1 – 15 Sep 2026" atau "17 Agu – 15 Sep 2026"
    private fun formatRentang(dari: LocalDate, sampai: LocalDate): String {
        val awal = when {
            dari.year != sampai.year -> formatTanggal(dari)
            dari.month != sampai.month -> dari.format(DateTimeFormatter.ofPattern("d MMM", Locale.forLanguageTag("id-ID")))
            else -> dari.dayOfMonth.toString()
        }
        return "$awal – ${formatTanggal(sampai)}"
    }

    // Konversi tanggal <-> milidetik (MaterialDatePicker memakai UTC)
    private fun keMillis(tanggal: LocalDate): Long =
        tanggal.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()

    private fun keTanggal(millis: Long): LocalDate =
        Instant.ofEpochMilli(millis).atZone(ZoneOffset.UTC).toLocalDate()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
