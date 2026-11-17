package com.example.anh_chocolatos.anggota.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

// Adapter pengatur Fragment mana yang ditampilkan untuk setiap tab (seperti TenthTabsAdapter)
class DetailAnggotaTabsAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    // Jumlah total tab yang ada
    override fun getItemCount(): Int = 3

    // Menentukan Fragment mana yang akan ditampilkan berdasarkan posisi tab
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TabSimpananFragment()
            1 -> TabPenjualanFragment()
            2 -> TabPembelianFragment()
            else -> throw IllegalStateException("Posisi tidak valid")
        }
    }
}
