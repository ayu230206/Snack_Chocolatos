package com.example.anh_chocolatos.anggota.detail

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.anh_chocolatos.databinding.ItemRiwayatBinding

// Adapter RecyclerView (pola sama dengan ProductAdapter di materi)
class RiwayatAdapter(
    private val riwayatList: List<RiwayatModel>,
    private val onItemClick: (RiwayatModel) -> Unit
) : RecyclerView.Adapter<RiwayatAdapter.RiwayatViewHolder>() {

    // ViewHolder memakai ViewBinding item_riwayat.xml
    inner class RiwayatViewHolder(val binding: ItemRiwayatBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RiwayatViewHolder {
        val binding = ItemRiwayatBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return RiwayatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RiwayatViewHolder, position: Int) {
        val item = riwayatList[position]
        val context = holder.itemView.context

        with(holder.binding) {
            tvJudul.text = item.judul
            tvKeterangan.text = item.keterangan
            tvNominal.text = item.nominal
            tvNominal.setTextColor(ContextCompat.getColor(context, item.warnaNominal))

            // Ikon dimuat dengan Glide
            Glide.with(context)
                .load(item.ikon)
                .into(imgIkon)
            imgIkon.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, item.warnaLatar))
            ImageViewCompat.setImageTintList(imgIkon, ColorStateList.valueOf(ContextCompat.getColor(context, item.warnaIkon)))

            // Garis pemisah tidak ditampilkan di item pertama
            viewGaris.visibility = if (position == 0) View.GONE else View.VISIBLE

            // Klik item dikirim ke Fragment lewat lambda onItemClick
            root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun getItemCount(): Int = riwayatList.size
}
