package com.example.githubuserrev1.kumpulan_viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.example.githubuserrev1.kumpulan_utils.PengaturanPreferences

// Mendeklarasikan kelas PengaturanViewModelFactory yang mewarisi dari NewInstanceFactory
class PengaturanViewModelFactory(private val pref: PengaturanPreferences) : NewInstanceFactory() {

    // Menimpa (override) fungsi create untuk membuat instance ViewModel
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Memeriksa apakah modelClass adalah subclass dari PengaturanViewModel
        if (modelClass.isAssignableFrom(PengaturanViewModel::class.java)) {
            // Mengembalikan instance PengaturanViewModel dengan pref sebagai parameter
            return PengaturanViewModel(pref) as T
        }
        // Melempar pengecualian jika modelClass tidak dikenal
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}