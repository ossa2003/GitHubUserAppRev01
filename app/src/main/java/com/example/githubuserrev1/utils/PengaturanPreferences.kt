package com.example.githubuserrev1.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PengaturanPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val TEMA_KEY = booleanPreferencesKey("theme_setting")

    fun mendapatkanThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[TEMA_KEY] ?: false
        }
    }

    suspend fun menyimpanThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[TEMA_KEY] = isDarkModeActive
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: PengaturanPreferences? = null

        fun mendapatkanInstance(dataStore: DataStore<Preferences>): PengaturanPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = PengaturanPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}