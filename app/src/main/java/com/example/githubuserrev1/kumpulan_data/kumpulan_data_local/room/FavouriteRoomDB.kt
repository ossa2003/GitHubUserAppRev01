package com.example.githubuserrev1.kumpulan_data.kumpulan_data_local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubuserrev1.kumpulan_data.kumpulan_data_local.entity.Favourite

// Menandai kelas ini sebagai Room Database dan mendefinisikan entitas yang terkait serta versi database
@Database(entities = [Favourite::class], version = 2, exportSchema = false)
abstract class FavouriteRoomDB : RoomDatabase() {

    // Mendefinisikan metode abstrak untuk mendapatkan instance dari FavouriteDao
    abstract fun favouriteDao(): FavouriteDao

    // Companion object untuk mendukung singleton pattern
    companion object {
        // Menandai instance variabel sebagai volatile untuk memastikan visibilitasnya antar thread
        @Volatile
        private var instance: FavouriteRoomDB? = null

        // Mendapatkan instance dari FavouriteRoomDB
        fun mendapatkanInstance(context: Context): FavouriteRoomDB =
            // Menggunakan instance yang ada jika sudah tersedia, jika tidak, sinkronisasi untuk membuat instance baru
            instance ?: synchronized(this) {
                // Jika instance masih null, bangun database baru
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    FavouriteRoomDB::class.java, "favourites.db"
                ).build().also { instance = it }
            }
    }
}
