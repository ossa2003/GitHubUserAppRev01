package com.example.githubuserrev1.kumpulan_viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.githubuserrev1.kumpulan_data.FavouriteRepository
import com.example.githubuserrev1.kumpulan_data.kumpulan_data_local.entity.Favourite

// Mendeklarasikan kelas FavouriteViewModel yang mewarisi dari ViewModel
class FavouriteViewModel(application: Application) : ViewModel() {

    // Mendeklarasikan instance FavouriteRepository untuk mengakses data favorit
    private val favoriteRepository: FavouriteRepository = FavouriteRepository(application)

    // Fungsi untuk mencari semua data favorit
    fun cariSemua() = favoriteRepository.cariSemua()

    // Fungsi untuk menambahkan data favorit ke repository
    fun buat(favorite: Favourite) {
        favoriteRepository.buat(favorite)
    }

    // Fungsi untuk mencari data favorit berdasarkan username
    fun cariByID(username: String?) {
        favoriteRepository.cariByID(username)
    }

    // Fungsi untuk menghapus data favorit dari repository berdasarkan id
    fun hapus(id: Long?) {
        favoriteRepository.hapus(id)
    }
}
