package com.example.githubuserrev1.kumpulan_utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Mendeklarasikan kelas PengaturanPreferences dengan konstruktor privat yang menerima parameter dataStore bertipe DataStore<Preferences>
class PengaturanPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    // Mendefinisikan kunci preferensi untuk pengaturan tema
    private val TEMA_KEY = booleanPreferencesKey("theme_setting")

    // Mendefinisikan fungsi untuk mendapatkan pengaturan tema dalam bentuk Flow<Boolean>
    fun mendapatkanThemeSetting(): Flow<Boolean> {
        // Mengembalikan Flow yang mengamati data dalam dataStore dan mengembalikan nilai pengaturan tema atau false jika belum disetel
        return dataStore.data.map { preferences ->
            preferences[TEMA_KEY] ?: false
        }
    }

    // Mendefinisikan fungsi suspend untuk menyimpan pengaturan tema
    suspend fun menyimpanThemeSetting(isDarkModeActive: Boolean) {
        // Mengedit data dalam dataStore untuk menyetel nilai pengaturan tema
        dataStore.edit { preferences ->
            preferences[TEMA_KEY] = isDarkModeActive
        }
    }

    // Companion object untuk mendukung singleton pattern
    companion object {
        // Menandai instance variabel sebagai volatile untuk memastikan visibilitasnya antar thread
        @Volatile
        private var INSTANCE: PengaturanPreferences? = null

        // Mendapatkan instance dari PengaturanPreferences
        fun mendapatkanInstance(dataStore: DataStore<Preferences>): PengaturanPreferences {
            // Menggunakan instance yang ada jika sudah tersedia, jika tidak, sinkronisasi untuk membuat instance baru
            return INSTANCE ?: synchronized(this) {
                val instance = PengaturanPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
