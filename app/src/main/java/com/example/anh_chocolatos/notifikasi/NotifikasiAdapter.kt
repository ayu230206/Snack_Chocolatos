package com.example.anh_chocolatos.notifikasi

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.bumptech.glide.Glide
import com.example.anh_chocolatos.databinding.ItemNotifikasiBinding
import com.google.android.material.snackbar.Snackbar

// Adapter penghubung data (NotifikasiModel) dengan tampilan per item (item_notifikasi.xml)
class NotifikasiAdapter(
    context: Context,
    private val notifikasiList: List<NotifikasiModel>
) : ArrayAdapter<NotifikasiModel>(context, 0, notifikasiList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemNotifikasiBinding = ItemNotifikasiBinding.inflate(LayoutInflater.from(context), parent, false)
        val view = binding.root

        val data = notifikasiList[position]

        // Judul grup hanya muncul di item pertama tiap grup (Hari ini, Kemarin, ...)
        val grupSebelumnya = if (position > 0) notifikasiList[position - 1].grup else null
        binding.textGrup.text = data.grup
        binding.textGrup.visibility = if (data.grup != grupSebelumnya) View.VISIBLE else View.GONE

        // Muat ikon dengan Glide
        Glide.with(context)
            .load(data.ikon)
            .into(binding.imgIkon)

        // Warna kotak ikon dan warna ikon sesuai jenis notifikasi
        binding.imgIkon.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(context, data.warnaLatar))
        ImageViewCompat.setImageTintList(
            binding.imgIkon,
            ColorStateList.valueOf(ContextCompat.getColor(context, data.warnaIkon))
        )

        binding.textJudul.text = data.judul
        binding.textPesan.text = data.pesan
        binding.textWaktu.text = data.waktu

        // Belum dibaca -> garis hijau dan titik merah, sudah dibaca -> polos
        val tebalGaris = (1 * context.resources.displayMetrics.density).toInt()
        binding.cardNotifikasi.strokeWidth = if (data.sudahDibaca) 0 else tebalGaris
        binding.viewBelumDibaca.visibility = if (data.sudahDibaca) View.GONE else View.VISIBLE

        // OnClick diterapkan di Adapter (sesuai materi)
        binding.cardNotifikasi.setOnClickListener {
            data.sudahDibaca = true
            notifyDataSetChanged()

            Snackbar.make(
                parent,
                "${data.judul}: ${data.pesan}",
                Snackbar.LENGTH_SHORT
            ).show()
        }

        return view
    }
}
