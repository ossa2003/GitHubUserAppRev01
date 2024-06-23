package com.example.githubuserrev1.kumpulan_data.kebutuhan_api

import com.example.githubuserrev1.BuildConfig
import com.example.githubuserrev1.user_model.ListUser
import com.example.githubuserrev1.user_model.ResponUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // Companion object untuk menyimpan konstanta API dari BuildConfig
    companion object{
        // Konstanta untuk menyimpan nilai API dari BuildConfig
        private const val APIbuatBuildConfig = BuildConfig.API
    }

    // Mendefinisikan endpoint untuk mendapatkan daftar pengguna yang diikuti oleh username tertentu
    @GET("users/{username}/following")
    @Headers("Authorization: token $APIbuatBuildConfig", "User-Agent: request")
    fun mendapatkanListFollowing(@Path("username") username: String): Call<List<ListUser>>

    // Mendefinisikan endpoint untuk mendapatkan detail pengguna berdasarkan username
    @GET("users/{username}")
    @Headers("Authorization: token $APIbuatBuildConfig", "User-Agent: request")
    fun mendapatkanDetailUser(@Path("username") username: String): Call<ListUser>

    // Mendefinisikan endpoint untuk mencari pengguna berdasarkan query
    @GET("search/users")
    @Headers("Authorization: token $APIbuatBuildConfig", "User-Agent: request")
    fun mendapatkanUsersDariSearch(@Query("q") username: String): Call<ResponUser>

    // Mendefinisikan endpoint untuk mendapatkan daftar pengikut pengguna berdasarkan username
    @GET("users/{username}/followers")
    @Headers("Authorization: token $APIbuatBuildConfig", "User-Agent: request")
    fun mendapatkanListFollowers(@Path("username") username: String): Call<List<ListUser>>

}