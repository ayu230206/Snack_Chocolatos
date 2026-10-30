package com.example.anh_chocolatos.anggota

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.anh_chocolatos.R
import com.example.anh_chocolatos.databinding.ActivityDataAnggotaBinding

class DataAnggotaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDataAnggotaBinding

    lateinit var anggotaRV: RecyclerView
    lateinit var anggotaRVAdapter: AnggotaRVAdapter
    lateinit var anggotaList: ArrayList<AnggotaRVModal>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDataAnggotaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Toolbar + tombol back (materi P5)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Data Anggota"
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back_dark)
        }

        // Tutorial: courseRV = findViewById(R.id.idRVCourses) → di sini ViewBinding
        anggotaRV = binding.idRVAnggota

        anggotaList = ArrayList()
        anggotaRVAdapter = AnggotaRVAdapter(anggotaList)
        anggotaRV.adapter = anggotaRVAdapter


        anggotaList.add(AnggotaRVModal("H. Hasan Basri", "1406 0112 7801 0021", "Aktif"))
        anggotaList.add(AnggotaRVModal("Siti Aminah", "1406 0245 8203 0007", "Aktif"))
        anggotaList.add(AnggotaRVModal("Budi Santoso", "1406 0318 7905 0112", "Aktif"))
        anggotaList.add(AnggotaRVModal("Rahmat Hidayat", "1406 0421 8507 0034", "Pasif"))
        anggotaList.add(AnggotaRVModal("Dewi Lestari", "1406 0530 9002 0056", "Aktif"))
        anggotaList.add(AnggotaRVModal("Ahmad Fauzi", "1406 0611 7710 0078", "Keluar"))
        anggotaList.add(AnggotaRVModal("Nurhayati", "1406 0702 8811 0090", "Aktif"))
        anggotaList.add(AnggotaRVModal("Joko Prasetyo", "1406 0815 8312 0103", "Pasif"))
        anggotaList.add(AnggotaRVModal("Rina Marlina", "1406 0923 9104 0125", "Aktif"))
        anggotaList.add(AnggotaRVModal("Syahrul Ramadhan", "1406 1007 8706 0147", "Aktif"))
        anggotaRVAdapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchItem: MenuItem = menu.findItem(R.id.actionSearch)
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.queryHint = "Cari nama atau NIK"   // TAMBAHAN

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(msg: String): Boolean {
                filter(msg)
                return false
            }
        })
        return true
    }

    // Tombol back di Toolbar (materi P5)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun filter(text: String) {
        val filteredlist: ArrayList<AnggotaRVModal> = ArrayList()

        for (item in anggotaList) {
            // Tutorial: courseName.toLowerCase().contains(text.toLowerCase())
            if (item.namaAnggota.lowercase().contains(text.lowercase()) ||
                item.nikAnggota.contains(text)   // TAMBAHAN: cari lewat NIK
            ) {
                filteredlist.add(item)
            }
        }

        if (filteredlist.isEmpty()) {
            Toast.makeText(this, "Data tidak ditemukan..", Toast.LENGTH_SHORT).show()
        } else {
            anggotaRVAdapter.filterList(filteredlist)
        }
    }
}