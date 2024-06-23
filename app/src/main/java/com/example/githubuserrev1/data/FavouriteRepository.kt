package com.example.githubuserrev1.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubuserrev1.data.local.entity.Favourite
import com.example.githubuserrev1.data.local.room.FavouriteDao
import com.example.githubuserrev1.data.local.room.FavouriteRoomDB
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavouriteRepository(application: Application) {

    private val favoriteDao: FavouriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavouriteRoomDB.mendapatkanInstance(application)
        favoriteDao = db.favouriteDao()
    }

    fun cariSemua(): LiveData<List<Favourite>> {
        return favoriteDao.cariSemua()
    }

    fun cariByID(username: String?): LiveData<List<Favourite>>  {
        return favoriteDao.cariByID(username)
    }

    fun buat(favorite: Favourite) {
        executorService.execute {
            favoriteDao.buat(favorite)
        }
    }

    fun hapus(id: Long?) {
        executorService.execute { favoriteDao.hapus(id) }
    }
}