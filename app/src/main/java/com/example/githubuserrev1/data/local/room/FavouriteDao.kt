package com.example.githubuserrev1.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubuserrev1.data.local.entity.Favourite

@Dao
interface FavouriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun buat(user: Favourite)

    @Query("SELECT * FROM favourites ORDER BY id")
    fun cariSemua(): LiveData<List<Favourite>>

    @Query("SELECT * FROM favourites WHERE favourites.username = :username ")
    fun cariByID(username: String?): LiveData<List<Favourite>>

    @Query("DELETE FROM favourites WHERE favourites.id = :id")
    fun hapus(id: Long?)
}