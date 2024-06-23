package com.example.githubuserrev1.kumpulan_viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubuserrev1.kumpulan_utils.PengaturanPreferences
import kotlinx.coroutines.launch

// Mendeklarasikan kelas PengaturanViewModel yang mewarisi dari ViewModel
class PengaturanViewModel(private val pref: PengaturanPreferences) : ViewModel() {

    // Fungsi untuk mendapatkan pengaturan tema dari Preferences dan mengubahnya menjadi LiveData
    fun getThemeSettings(): LiveData<Boolean> {
        return pref.mendapatkanThemeSetting().asLiveData()
    }

    // Fungsi untuk menyimpan pengaturan tema
    fun saveThemeSetting(isDarkModeActive: Boolean) {
        // Meluncurkan coroutine di viewModelScope
        viewModelScope.launch {
            pref.menyimpanThemeSetting(isDarkModeActive)
        }
    }
}