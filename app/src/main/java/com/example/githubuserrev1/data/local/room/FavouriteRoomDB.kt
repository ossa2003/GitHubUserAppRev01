package com.example.githubuserrev1.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubuserrev1.data.local.entity.Favourite
import kotlinx.coroutines.CoroutineScope

@Database(entities = [Favourite::class], version = 2, exportSchema = false)
abstract class FavouriteRoomDB : RoomDatabase() {

    abstract fun favouriteDao(): FavouriteDao

    companion object {
        @Volatile
        private var instance: FavouriteRoomDB? = null

        fun mendapatkanInstance(context: Context): FavouriteRoomDB =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    FavouriteRoomDB::class.java, "favourites.db"
                ).build()
            }
    }
}