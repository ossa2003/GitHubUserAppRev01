package com.example.githubuserrev1.kumpulan_data.kumpulan_data_local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubuserrev1.kumpulan_data.kumpulan_data_local.entity.Favourite

// Menandai bahwa ini adalah interface DAO (Data Access Object) untuk Room
@Dao
interface FavouriteDao {

    // Mendefinisikan operasi insert dengan strategi konflik IGNORE
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun buat(user: Favourite)
    // Fungsi ini akan menyisipkan objek Favourite ke dalam database.
    // Jika ada konflik (misalnya data sudah ada), operasi akan diabaikan.

    // Mendefinisikan query untuk mendapatkan semua data dari tabel favourites, diurutkan berdasarkan id
    @Query("SELECT * FROM favourites ORDER BY id")
    fun cariSemua(): LiveData<List<Favourite>>
    // Fungsi ini akan mengembalikan LiveData yang berisi daftar semua objek Favourite dalam database,
    // diurutkan berdasarkan kolom id.

    // Mendefinisikan query untuk mencari data di tabel favourites berdasarkan username
    @Query("SELECT * FROM favourites WHERE favourites.username = :username ")
    fun cariByID(username: String?): LiveData<List<Favourite>>
    // Fungsi ini akan mengembalikan LiveData yang berisi daftar objek Favourite yang memiliki username tertentu.

    // Mendefinisikan query untuk menghapus data dari tabel favourites berdasarkan id
    @Query("DELETE FROM favourites WHERE favourites.id = :id")
    fun hapus(id: Long?)
    // Fungsi ini akan menghapus objek Favourite dari database yang memiliki id tertentu.
}
