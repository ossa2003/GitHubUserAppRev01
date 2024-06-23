package com.example.githubuserrev1.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.githubuserrev1.data.FavouriteRepository
import com.example.githubuserrev1.data.local.entity.Favourite

class FavouriteViewModel(application: Application) : ViewModel() {

    private val favoriteRepository: FavouriteRepository = FavouriteRepository(application)

    fun cariSemua() = favoriteRepository.cariSemua()

    fun buat(favorite: Favourite) {
        favoriteRepository.buat(favorite)
    }

    fun cariByID(username: String?) {
        favoriteRepository.cariByID(username)
    }

    fun hapus(id: Long?) {
        favoriteRepository.hapus(id)
    }
}