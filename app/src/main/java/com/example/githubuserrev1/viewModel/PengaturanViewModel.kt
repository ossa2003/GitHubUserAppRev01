package com.example.githubuserrev1.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubuserrev1.utils.PengaturanPreferences
import kotlinx.coroutines.launch

class PengaturanViewModel(private val pref: PengaturanPreferences) : ViewModel() {

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.mendapatkanThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.menyimpanThemeSetting(isDarkModeActive)
        }
    }
}