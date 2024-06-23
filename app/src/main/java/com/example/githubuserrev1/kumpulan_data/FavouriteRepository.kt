package com.example.githubuserrev1.kumpulan_data

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubuserrev1.kumpulan_data.kumpulan_data_local.entity.Favourite
import com.example.githubuserrev1.kumpulan_data.kumpulan_data_local.room.FavouriteDao
import com.example.githubuserrev1.kumpulan_data.kumpulan_data_local.room.FavouriteRoomDB
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavouriteRepository(application: Application) {

    // Deklarasi variabel untuk DAO Favourite
    private val favoriteDao: FavouriteDao

    // Deklarasi ExecutorService untuk menjalankan tugas secara asynchronous
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    // Blok inisialisasi untuk mendapatkan instance dari database dan DAO
    init {
        val db = FavouriteRoomDB.mendapatkanInstance(application)
        favoriteDao = db.favouriteDao()
    }

    // Fungsi untuk mencari semua data Favourite dalam bentuk LiveData
    fun cariSemua(): LiveData<List<Favourite>> {
        return favoriteDao.cariSemua()
    }

    // Fungsi untuk mencari data Favourite berdasarkan username dalam bentuk LiveData
    fun cariByID(username: String?): LiveData<List<Favourite>>  {
        return favoriteDao.cariByID(username)
    }

    // Fungsi untuk menambahkan data Favourite ke database
    fun buat(favorite: Favourite) {
        executorService.execute {
            favoriteDao.buat(favorite)
        }
    }

    // Fungsi untuk menghapus data Favourite dari database berdasarkan id
    fun hapus(id: Long?) {
        executorService.execute { favoriteDao.hapus(id) }
    }
}