package com.example.githubuserrev1.data.api

import com.example.githubuserrev1.BuildConfig
import com.example.githubuserrev1.model.ListUser
import com.example.githubuserrev1.model.ResponUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    companion object{
        private const val APIbuatBuildConfig = BuildConfig.API
    }

    @GET("users/{username}/following")
    @Headers("Authorization: token $APIbuatBuildConfig", "UserResponse-Agent: request")
    fun mendapatkanListFollowing(@Path("username") username: String): Call<List<ListUser>>

    @GET("users/{username}")
    @Headers("Authorization: token $APIbuatBuildConfig", "UserResponse-Agent: request")
    fun mendapatkanDetailUser(@Path("username") username: String): Call<ListUser>

    @GET("search/users")
    @Headers("Authorization: token $APIbuatBuildConfig", "UserResponse-Agent: request")
    fun mendapatkanUsersDariSearch(@Query("q") username: String): Call<ResponUser>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $APIbuatBuildConfig", "UserResponse-Agent: request")
    fun mendapatkanListFollowers(@Path("username") username: String): Call<List<ListUser>>

}