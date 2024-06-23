package com.example.githubuserrev1.kumpulan_viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

// Mendeklarasikan kelas ViewModelFactory yang bersifat private constructor dan mewarisi dari ViewModelProvider.NewInstanceFactory
class ViewModelFactory private constructor(private val myAplikasi: Application) :
    ViewModelProvider.NewInstanceFactory() {

    // Menimpa (override) fungsi create untuk membuat instance ViewModel
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Memeriksa apakah modelClass adalah subclass dari FavouriteViewModel
        if (modelClass.isAssignableFrom(FavouriteViewModel::class.java)) {
            // Mengembalikan instance FavouriteViewModel dengan myAplikasi sebagai parameter
            return FavouriteViewModel(myAplikasi) as T
        }
        // Memeriksa apakah modelClass adalah subclass dari DetilUserViewModel
        else if (modelClass.isAssignableFrom(DetilUserViewModel::class.java)) {
            // Mengembalikan instance DetilUserViewModel dengan myAplikasi sebagai parameter
            return DetilUserViewModel(myAplikasi) as T
        }
        // Melempar pengecualian jika modelClass tidak dikenal
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }

    // Companion object untuk mengimplementasikan pola singleton
    companion object {
        // Menandai instance variabel sebagai volatile untuk memastikan visibilitasnya antar thread
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        // Fungsi untuk mendapatkan instance dari ViewModelFactory
        @JvmStatic
        fun mendapatkanInstance(application: Application): ViewModelFactory {
            // Memeriksa apakah instance belum ada
            if (INSTANCE == null) {
                // Mensinkronisasi blok untuk membuat instance baru jika belum ada
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application)
                }
            }
            // Mengembalikan instance ViewModelFactory
            return INSTANCE as ViewModelFactory
        }
    }
}