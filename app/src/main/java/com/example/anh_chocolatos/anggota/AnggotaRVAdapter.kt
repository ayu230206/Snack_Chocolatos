package com.example.anh_chocolatos.anggota

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.anh_chocolatos.R

// Tutorial: class CourseRVAdapter(private var courseList: ArrayList<CourseRVModal>)
class AnggotaRVAdapter(private var anggotaList: ArrayList<AnggotaRVModal>) :
    RecyclerView.Adapter<AnggotaRVAdapter.AnggotaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnggotaViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.anggota_rv_item, parent, false)
        return AnggotaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AnggotaViewHolder, position: Int) {
        val anggota = anggotaList[position]
        holder.namaTV.text = anggota.namaAnggota

        // TAMBAHAN: NIK, inisial, dan warna badge status
        holder.nikTV.text = "NIK ${anggota.nikAnggota}"
        holder.inisialTV.text = anggota.namaAnggota
            .split(" ")
            .filter { it.isNotEmpty() }
            .take(2)
            .joinToString("") { it.first().uppercase() }

        holder.statusTV.text = anggota.statusAnggota
        val context = holder.itemView.context
        when (anggota.statusAnggota) {
            "Aktif" -> {
                holder.statusTV.setBackgroundResource(R.drawable.bg_chip_hijau)
                holder.statusTV.setTextColor(ContextCompat.getColor(context, R.color.sikuda_kas_masuk))
            }
            "Pasif" -> {
                holder.statusTV.setBackgroundResource(R.drawable.bg_ikon_oranye)
                holder.statusTV.setTextColor(ContextCompat.getColor(context, R.color.sikuda_accent))
            }
            else -> {
                holder.statusTV.setBackgroundResource(R.drawable.bg_kotak_keluar)
                holder.statusTV.setTextColor(ContextCompat.getColor(context, R.color.sikuda_kas_keluar))
            }
        }
    }

    override fun getItemCount(): Int {
        return anggotaList.size
    }

    // Sama seperti tutorial: ganti isi daftar dengan hasil filter
    fun filterList(filterlist: ArrayList<AnggotaRVModal>) {
        anggotaList = filterlist
        notifyDataSetChanged()
    }

    // Sama seperti tutorial: ViewHolder memakai findViewById
    class AnggotaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val inisialTV: TextView = itemView.findViewById(R.id.idTVInisial)
        val namaTV: TextView = itemView.findViewById(R.id.idTVNama)
        val nikTV: TextView = itemView.findViewById(R.id.idTVNik)
        val statusTV: TextView = itemView.findViewById(R.id.idTVStatus)
    }
}